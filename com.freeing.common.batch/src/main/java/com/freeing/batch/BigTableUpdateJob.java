package com.freeing.batch;

import com.freeing.batch.entity.JobConfig;
import com.freeing.batch.entity.JobExecutionContext;
import com.freeing.batch.entity.JobResult;
import com.freeing.batch.entity.RunningParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BigTableUpdateJob extends AbstractJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(BigTableUpdateJob.class);

    private static final String THREAD_HANDLER_COUNT = "theadTotalCount";
    private static final String BROKEN_COUNT = "brokenCount";
    private static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ThreadPoolExecutor threadPoolExecutor;

    public BigTableUpdateJob(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public JobResult executeJob(JobExecutionContext jobExecutionContext) {
        String jobParameter = jobExecutionContext.getJobParameter();
        // 校验参数

        JobConfig jobConfig = new JobConfig();
        int threadSize = jobConfig.getThreadSize();
        long total = 0L;
        try {
            long startNanos = jobConfig.getStartTime().toEpochSecond(ZoneOffset.UTC);
            long endNanos = jobConfig.getEndTime().toEpochSecond(ZoneOffset.UTC);
            long times = (startNanos - endNanos) / threadSize;

            ArrayList<Handler> list = new ArrayList<>(threadSize);
            LocalDateTime startTime = jobConfig.getStartTime();
            LocalDateTime endTime = jobConfig.getEndTime();
            for (int i = 0; i < threadSize; i++) {
                Map<String, Object> monitorMap = new HashMap<>();

                endTime = startTime.plusNanos(times);
                Handler handler = new Handler(jobConfig.getTableName(), startTime, endTime, jobConfig.getIntervalTime(), jobConfig.getThreshold(), jobConfig.getMinInterval(), jobConfig.getIntervalDivisorFactor(), monitorMap);
                list.add(handler);

                startTime = endTime.plusNanos(0);
            }

            List<Future<Long>> futures = threadPoolExecutor.invokeAll(list);

            for (Future<Long> future : futures) {
                total += future.get();
            }

        } catch (Exception e) {

        }
        return null;
    }


    private void doHandler(LocalDateTime startTime, LocalDateTime endTime, int intervalTime, RunningParams runningParams, Map<String, Object> monitorMap) {
        long count;
        while (startTime.isBefore(endTime)) {
            LocalDateTime currentStartTime = startTime.plusNanos(intervalTime);
            if (currentStartTime.isAfter(endTime)) {
                currentStartTime = endTime;
            }

            count = 0L;
            List<Object> list = new ArrayList<>(); // 查询结果
            if (list.size() <= runningParams.getThreshold()) {
                // 直接执行
                LOGGER.info("{} 正常处理数据 startTime:{}, endTime{}, intervalTime:{}, querySize:{}, threadTotalCount:{}", Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime), intervalTime, list.size(), monitorMap.get(THREAD_HANDLER_COUNT));
                // startHandler();
                count += list.size();
            }
            // 判断是否需要缩小间隔时间: 处理数据量大于给定阈值，并且间隔时间不可切分则分月处理
            else if (intervalTime <= runningParams.getMinInterval()) {
                String uuid = UUID.randomUUID().toString();
                LOGGER.info("{}-{} 准备分页处理数据 startTime:{}, endTime{}, intervalTime:{}, querySize:{}, threadTotalCount:{}", uuid, Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime), intervalTime, list.size(), monitorMap.get(THREAD_HANDLER_COUNT));
                // count += pageHandler(uuid, );
                LOGGER.info("{}-{} 分页处理数据结束 startTime:{}, endTime{}, intervalTime:{}, querySize:{}, threadTotalCount:{}", uuid, Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime), intervalTime, list.size(), monitorMap.get(THREAD_HANDLER_COUNT));
            }
            // 需要缩小时间间隔处理
            else {
                Long brokenCount = (Long) monitorMap.get(BROKEN_COUNT);
                brokenCount++;
                monitorMap.put(BROKEN_COUNT, brokenCount);

                LOGGER.info("{} 分裂处理数据 startTime:{}, endTime{}, newIntervalTime:{}, brokenCount:{}, querySize:{}, threadTotalCount:{}", Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime), intervalTime / runningParams.getIntervalDivisorFactor(), brokenCount, list.size(), monitorMap.get(THREAD_HANDLER_COUNT));
                doHandler(startTime, endTime, intervalTime / runningParams.getIntervalDivisorFactor(), runningParams, monitorMap);
            }

            Long threadTotalCount = (Long) monitorMap.get(THREAD_HANDLER_COUNT);

            threadTotalCount += count;
            monitorMap.put(THREAD_HANDLER_COUNT, threadTotalCount);
            startTime = currentStartTime;

        }
    }

    class Handler implements Callable<Long> {
        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private RunningParams runningParams;
        
        private int intervalTime;

        private Map<String, Object> monitorMap;


        public Handler(String tableName, LocalDateTime startTime, LocalDateTime endTime, int intervalTime, int threshold,
            int minInterval, int intervalDivisorFactor, Map<String, Object> monitorMap) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.intervalTime = intervalTime;
            this.monitorMap = monitorMap;
            this.runningParams = new RunningParams(tableName, minInterval, threshold, intervalDivisorFactor);

            monitorMap.put(THREAD_HANDLER_COUNT, 0L);
            monitorMap.put(BROKEN_COUNT, 0L);
        }

        @Override
        public Long call() throws Exception {
            try {
                LOGGER.info("{} 开始执行 === 开始时间: {}, 结束时间: {}", Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime));
                doHandler(startTime, endTime, intervalTime, runningParams, monitorMap);
                LOGGER.info("{} 开始执行 === 开始时间: {}, 结束时间: {}", Thread.currentThread().getName(), df_yyyy_MM_ddHHmmss(startTime), df_yyyy_MM_ddHHmmss(endTime));
            } catch (Exception ex) {
                LOGGER.error("BigTableUpdateJob Handler occurred error", ex);
            }
            return (Long) monitorMap.get(THREAD_HANDLER_COUNT);
        }
    }




    private static String df_yyyy_MM_ddHHmmss(LocalDateTime dateTime) {
        return dateTime.format(yyyyMMddHHmmss);
    }
}
