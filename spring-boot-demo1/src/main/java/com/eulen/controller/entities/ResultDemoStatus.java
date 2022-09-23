package com.eulen.controller.entities;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.MDC;

import com.eulen.core.filter.EulenRequestIdLocalFilter;;

public class ResultDemoStatus {
	public enum Status {
		SUCCES, FAIL
	};

	public ResultDemoStatus() {
		states = new ArrayList<String>();
		this.addState();
	}

	public Status status;
	public String message;
	public List<String> states;

	public Status calculateStatus() {

		// Si no hay stados es OK
		if (states != null && states.size() > 0) {
			// Se valida si todos los correlationIds son iguales
			String last = states.get(0);
			for (int cont = 0; cont < states.size(); cont++) {
				String sts = states.get(cont);
				if (!last.equals(sts)) {
					status = Status.FAIL;
					message = String.format("%s , lineas de %s a %s", sts, cont - 1, cont);
					return Status.FAIL;
				}
			}
		}
		status = Status.SUCCES;
		return status;

	}

	public void merge(ResultDemoStatus res) {
		if (states == null)
			states = new ArrayList<String>();
		states.addAll(res.states);
		this.calculateStatus();
	}

	public void addState() {
		if (states == null)
			states = new ArrayList<String>();
		String mdc = MDC.get(EulenRequestIdLocalFilter.CORRELATION_ID_FIELD_NAME);
		if (mdc != null)
			states.add(mdc);
		this.calculateStatus();
	}

	public void addState(String status) {
		if (status != null)
			return;
		if (states == null)
			states = new ArrayList<String>();
		states.add(status);
		this.calculateStatus();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

}