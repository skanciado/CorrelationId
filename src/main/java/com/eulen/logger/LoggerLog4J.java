 // Class for logging with log4j implementation ILogger interface
package com.eulen.logger;
 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;

import com.eulen.correlationid.CorrelationIdStore;

import lombok.Builder;
@Builder
public class LoggerLog4J implements ILogger {
    
    private Logger logger  ;
    public LoggerLog4J(Logger logger) {
        this.logger = logger;
    }
    @Builder(builderMethodName = "getLogger")
    public static LoggerLog4J  getLogger(Class<?> clazz) { 
        LoggerLog4J log =  new LoggerLog4J(LogManager.getLogger(clazz));
        return log; 
    }
    @Override
    public void info(String message) {
        logger.info(new StringMapMessage()
        .with("message", message)
        .with("correlationid", CorrelationIdStore.getCurrentId()));
    }
    
    @Override
    public void error(String message) {
        logger.error(message);
    }
    
    @Override
    public void debug(String message) {
        logger.debug(message);
    }
    
    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
