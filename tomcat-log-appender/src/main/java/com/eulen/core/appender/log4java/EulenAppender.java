
package com.eulen.core.appender.log4java;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.MDC;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.WarnStatus;
import ch.qos.logback.core.util.Loader;

public class EulenAppender extends OutputStreamAppender<ILoggingEvent> {

	protected ConsoleTarget target = ConsoleTarget.SystemOut;
	protected boolean withJansi = false;

	private final static String AnsiConsole_CLASS_NAME = "org.fusesource.jansi.AnsiConsole";
	private final static String wrapSystemOut_METHOD_NAME = "wrapSystemOut";
	private final static String wrapSystemErr_METHOD_NAME = "wrapSystemErr";
	private final static Class<?>[] ARGUMENT_TYPES = { PrintStream.class };

	/**
	 * Sets the value of the <b>Target</b> option. Recognized values are
	 * "System.out" and "System.err". Any other value will be ignored.
	 */
	public void setTarget(String value) {
		ConsoleTarget t = ConsoleTarget.findByName(value.trim());
		if (t == null) {
			targetWarn(value);
		} else {
			target = t;
		}
	}

	/**
	 * Returns the current value of the <b>target</b> property. The default value of
	 * the option is "System.out".
	 * <p>
	 * See also {@link #setTarget}.
	 */
	public String getTarget() {
		return target.getName();
	}

	private void targetWarn(String val) {
		Status status = new WarnStatus("[" + val + "] should be one of " + Arrays.toString(ConsoleTarget.values()),
				this);
		status.add(new WarnStatus("Using previously set target, System.out by default.", this));
		addStatus(status);
	}

	@Override
	public void start() {
		OutputStream targetStream = target.getStream();
		// enable jansi only if withJansi set to true
		if (withJansi) {
			targetStream = wrapWithJansi(targetStream);
		}
		setOutputStream(targetStream);
		super.start();
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		MDC.put("X-Correlation-Id", "TEST");
		super.append(eventObject);
	}

	private OutputStream wrapWithJansi(OutputStream targetStream) {
		try {
			addInfo("Enabling JANSI AnsiPrintStream for the console.");
			ClassLoader classLoader = Loader.getClassLoaderOfObject(context);
			Class<?> classObj = classLoader.loadClass(AnsiConsole_CLASS_NAME);
			String methodName = target == ConsoleTarget.SystemOut ? wrapSystemOut_METHOD_NAME
					: wrapSystemErr_METHOD_NAME;
			Method method = classObj.getMethod(methodName, ARGUMENT_TYPES);
			return (OutputStream) method.invoke(null, new PrintStream(targetStream));
		} catch (Exception e) {
			addWarn("Failed to create AnsiPrintStream. Falling back on the default stream.", e);
		}
		return targetStream;
	}

	/**
	 * @return whether to use JANSI or not.
	 */
	public boolean isWithJansi() {
		return withJansi;
	}

	/**
	 * If true, this appender will output to a stream provided by the JANSI library.
	 *
	 * @param withJansi whether to use JANSI or not.
	 * @since 1.0.5
	 */
	public void setWithJansi(boolean withJansi) {
		this.withJansi = withJansi;
	}

}