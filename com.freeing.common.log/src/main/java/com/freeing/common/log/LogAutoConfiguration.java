package com.freeing.common.log;

import com.freeing.common.log.config.AuditLogProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author yanggy
 */
@EnableConfigurationProperties(AuditLogProperties.class)
public class LogAutoConfiguration {

}
