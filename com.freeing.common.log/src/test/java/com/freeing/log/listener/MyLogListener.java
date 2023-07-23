package com.freeing.log.listener;

import com.freeing.common.log.domain.OperationLog;
import com.freeing.common.log.listener.BaseLogListener;
import org.springframework.stereotype.Component;


/**
 * @author yanggy
 */
@Component
public class MyLogListener extends BaseLogListener {
    @Override
    protected void apply(OperationLog operationLog) {
        System.out.println(operationLog);
    }
}
