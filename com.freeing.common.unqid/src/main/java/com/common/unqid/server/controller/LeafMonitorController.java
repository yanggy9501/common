package com.common.unqid.server.controller;

import com.common.unqid.leafcore.IdManager;
import com.common.unqid.leafcore.segment.model.SegmentBuffer;
import com.common.unqid.leafcore.segment.model.entity.LeafAlloc;
import com.common.unqid.server.module.SegmentBufferView;
import com.common.unqid.server.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控 Controller 接口
 */
@RestController
@RequestMapping("/monitor")
public class LeafMonitorController {
    private final Logger logger = LoggerFactory.getLogger(LeafMonitorController.class);

    @Autowired
    private SegmentService segmentService;

    @RequestMapping(value = "/cache")
    public Map<String, SegmentBufferView> getCache() {
        Map<String, SegmentBufferView> data = new HashMap<>();
        if (segmentService == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        IdManager idManager = IdManager.getIdManager();
        Map<String, SegmentBuffer> cache = idManager.getSegmentContextMap();
        for (Map.Entry<String, SegmentBuffer> entry : cache.entrySet()) {
            SegmentBufferView sv = new SegmentBufferView();

            SegmentBuffer buffer = entry.getValue();

            sv.setInitOk(buffer.isInitOk());
            sv.setKey(buffer.getKey());
            sv.setThreadRunning(buffer.getThreadRunning().get());
            sv.setCurrentPosition(buffer.getCurrentPosition());

            sv.setMax0(buffer.getSegments()[0].getMaxId());
            sv.setValue0(buffer.getSegments()[0].getValue().get());
            sv.setStep0(buffer.getSegments()[0].getStep());
            sv.setReady0(buffer.getSegments()[0].isReady());

            sv.setMax1(buffer.getSegments()[1].getMaxId());
            sv.setValue1(buffer.getSegments()[1].getValue().get());
            sv.setStep1(buffer.getSegments()[1].getStep());
            sv.setReady1(buffer.getSegments()[1].isReady());

            data.put(entry.getKey(), sv);

        }
        logger.info("Cache info {}", data);
        return data;
    }

    @RequestMapping(value = "/db")
    public List<LeafAlloc> getDb() {
        if (segmentService == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        IdManager idManager = IdManager.getIdManager();
        List<LeafAlloc> items = idManager.getAllLeafAllocs();
        logger.info("DB info {}", items);
        return items;
    }
}
