package com.freeing.batch;

import com.freeing.batch.entity.JobExecutionContext;
import com.freeing.batch.entity.JobResult;

public abstract class AbstractJob {

    public JobResult executeJob(JobExecutionContext jobExecutionContext) {
        return null;
    }
}
