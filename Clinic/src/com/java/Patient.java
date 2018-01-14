package com.java;

import java.util.Date;
import java.util.Random;

public class Patient extends Thread {

	private String name;
	private int hard;
	private int waiting;
	private Chair chair;
	private Clinic clinic;
	private Office office;

	public Patient(String name, int hard, Clinic clinic) {
		this.name = name;
		this.hard = hard;
		this.waiting = new Random().nextInt(20) + 10;
		this.clinic = clinic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Пациент номер " + name + " пришел в клинику");
		while (!this.isInterrupted()) {
			if (this.office == null) {
				if (clinic.hasPatient()) {
					tryToSit();
				} else {
					if (clinic.goAnyWhere(this)) {
						System.out.println("Пациент " + name + " зашел в офис");
					} else {
						System.out.println("Пациент " + name + " не нашел свободных офисов и пошел искать сидения");
						tryToSit();
					}
				}
			}
		}
	}

	private void tryToSit() {
		if (clinic.findFreeChair(this)) {
			this.chair.setPatient(this);
			System.out.println("Пациент " + name + " сел ждать на стул");

			startWaiting();
		} else {
			System.out.println("Пациент " + name + " на нашел возможностей лечится и вышел в окно");
			this.interrupt();
		}
	}

	private void startWaiting() {
		Date start = new Date();
		Date current = new Date();
		while (current.getTime() - start.getTime() < this.waiting * 1000) {
			current = new Date();
		}
		if (office == null) {
			System.out.println("Пациент " + name + " задолбался ждать и ушел");
			this.chair.setPatient(null);
			this.clinic.moveQueue(this);
			this.interrupt();
		}
	}
	
	public synchronized void setOffice(Office office) {
		this.office = office;
	}
	
	public synchronized void setChair(Chair chair) {
		this.chair = chair;
	}

	public synchronized void goHome() {
		System.out.println("Пациента " + name + " выгнали из больницы");
		this.interrupt();
	}

	public synchronized void isCurred() {
		this.interrupt();
	}

	public String getPatientName() {
		return name;
	}

	public synchronized int getHard() {
		return hard;
	}
}
