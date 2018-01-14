package com.java;

public class Chair {

	private String name;
	private Patient patient;

	public Chair(String name) {
		this.name = name;
	}

	public synchronized boolean isFree() {
		if (patient == null) {
			return true;
		} else {
			return false;
		}

	}

	public synchronized void setPatient(Patient patient) {
/*		System.out.println("Я стул " + name + ", на меня сел " + patient.getPatientName());*/
		this.patient = patient;
	}
	
	public String getName() {
		return name;
	}

	public synchronized Patient getPatient() {
		return patient;
	}

	private synchronized void standUp() {
		this.patient = null;
	}

	public synchronized Patient callPatient() {
		Patient patient = this.patient;
		standUp();
		return patient;
	}
}
