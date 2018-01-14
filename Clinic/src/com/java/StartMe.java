package com.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartMe {
	public static void main(String[] args) {
		int count = 3;
		List<Office> chambers = makeChambers(count);
		Clinic clinic = new Clinic(3, chambers);
		List<Doctor> doctors = makeDoctors(count, clinic);
		City city = new City(clinic);
		city.start(doctors);
	}

	private static List<Office> makeChambers(int count) {
		List<Office> chambers = new ArrayList<Office>();
		for (int i = 0; i < count; i++) {
			chambers.add(new Office());
		}
		return chambers;
	}

	private static List<Doctor> makeDoctors(int count, Clinic clinic) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		Random rnd = new Random();
		for (int i = 1; i < count + 1; i++) {
			doctors.add(new Doctor(Integer.toString(i), rnd.nextInt(3) + 2, clinic));
		}
		return doctors;
	}
}
