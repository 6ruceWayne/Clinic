package com.java;

import java.util.List;
import java.util.Timer;

import com.java.Clinic.GeneratorPatients;

public class City {
	private Clinic clinic;
	private Timer generator;

	public City(Clinic clinic) {
		this.clinic = clinic;
		generator = new Timer();
		generator.schedule(new PatientGenerator(clinic), 3000, 3000);
	}

	public void start(List<Doctor> doctors) {
		clinic.init(doctors);
	}

}
