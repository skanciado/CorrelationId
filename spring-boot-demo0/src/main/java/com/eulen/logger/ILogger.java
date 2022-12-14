package com.eulen.logger;

public interface ILogger {

    void info(String message);

    void error(String message);

    void debug(String message);

    void warn(String message); 
    
}
