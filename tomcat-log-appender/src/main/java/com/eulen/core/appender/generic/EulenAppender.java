package com.eulen.core.appender.generic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class EulenAppender extends AppenderBase<ILoggingEvent> {

	private ConcurrentMap<String, ILoggingEvent> eventMap = new ConcurrentHashMap<String, ILoggingEvent>();

	@Override
	protected void append(ILoggingEvent event) {
		eventMap.put("" + System.currentTimeMillis(), event);
	}

	public Map<String, ILoggingEvent> getEventMap() {
		return eventMap;
	}
}