package com.java;

public class Office {
	private boolean working;
	private boolean status;
	private Patient patient;

	public Office() {
		this.working = true;
		this.status = true;
	}

	public synchronized void setPatient(Patient patient) {
		this.patient = patient;
		if (patient != null) {
			this.status = false;
		} else {
			this.status = true;
		}
	}

	public synchronized Patient getPatient() {
		return patient;
	}

	public void closeOffice() {
		this.working = false;
	}

	public synchronized boolean isWorking() {
		return working;
	}

	public synchronized boolean isFree() {
		return status;
	}

	public synchronized void setStatus(boolean status) {
		this.status = status;
	}

	public synchronized void knockKnock(Patient patient) {
		this.status = false;
		patient.setOffice(this);
		this.patient = patient;
	}

}
