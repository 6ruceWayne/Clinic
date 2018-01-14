package com.java;

import java.util.Random;
import java.util.TimerTask;

public class PatientGenerator extends TimerTask {
	private Clinic clinic;
	private int count;
	private Random random;

	public PatientGenerator(Clinic clinic) {
		random = new Random();
		this.clinic = clinic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (clinic.isWorking()) {
			count++;
			Patient patient = new Patient(Integer.toString(count), random.nextInt(20) + 10, clinic);
			patient.start();
		} else {
			this.cancel();
			
			System.err.println("Вырубил таймер");
		}
	}
}
