package com.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Clinic {
	private boolean working;
	private List<Office> offices;
	private List<Chair> chairs;
	/* private Timer generatorPatients; */

	public Clinic(int chairs, List<Office> offices) {
		this.offices = offices;
		this.working = true;
		this.chairs = new ArrayList<Chair>();
		for (int i = 0; i < chairs; i++) {
			this.chairs.add(new Chair(Integer.toString(i)));
		}
	}

	public void init(List<Doctor> doctors) {
		Doctor doctor;
		for (int i = 0; i < doctors.size(); i++) {
			doctor = doctors.get(i);
			doctor.setOffice(offices.get(i));
			/* offices.get(i).setDoctor(doctor); */
			doctor.start();
		}
		/*
		 * generatorPatients = new Timer(); generatorPatients.schedule(new
		 * GeneratorPatients(this), 3000, 3000);
		 */
	}

	public boolean isWorking() {
		return working;
	}

	public synchronized void closeOffice(Office officeDoctors) {
		officeDoctors.closeOffice();
		boolean somebodyWork = false;
		for (Office office : offices) {
			if (office.isWorking()) {
				somebodyWork = true;
			}
		}
		if (!somebodyWork) {
			/* generatorPatients.cancel(); */
			kickOutPatients();
			this.working = false;
			System.out.println("Больница закрылась");

		}
	}

	private void kickOutPatients() {
		for (Chair chair : chairs) {
			if (chair.getPatient() != null) {
				System.out.println("Выгнали пациента номер " + chair.getPatient().getPatientName());
				chair.getPatient().goHome();
				chair.setPatient(null);
			}
		}
	}

	public synchronized boolean goAnyWhere(Patient patient) {
		for (Office office : offices) {
			if (office.isFree() && office.isWorking()) {
				office.knockKnock(patient);
				return true;
			}
		}
		return false;
	}

	public synchronized boolean hasPatient() {
		for (Chair chair : chairs) {
			if (!chair.isFree()) {
				return true;
			}
		}
		return false;
	}

	public synchronized Office findFreeOffice() {
		for (Office office : offices) {
			if (office.isFree() && office.isWorking()) {
				return office;
			}
		}
		return null;
	}

	public synchronized boolean findFreeChair(Patient patient) {
		for (int i = 0; i < chairs.size(); i++) {
			if (chairs.get(i).isFree()) {
				chairs.get(i).setPatient(patient);
				patient.setChair(chairs.get(i));
				return true;
			}
		}
		return false;
	}

	public synchronized boolean invitePatient(Office office) {
		for (Chair chair : chairs) {
			if (!chair.isFree()) {
				office.setPatient(chair.getPatient());
				chair.getPatient().setOffice(office);
				chair.setPatient(null);
				moveQueue();
				return true;
			}
		}
		return false;
	}

	public synchronized Patient callPatient() {
		/*
		 * for (Chair chair : chairs) { if (!chair.isFree()) { chairs.poll(); return
		 * chair.callPatient(); } }
		 */
		/* printPatient(); */
		Patient patient = chairs.get(0).callPatient();
		/* System.out.println(patient.getPatientName()); */
		moveQueue();
		return patient;
	}

	private synchronized void moveQueue() {
		chairs.get(0).setPatient(null);
		for (int i = 0; i < chairs.size(); i++) {
			if (i + 1 < chairs.size()) {
				chairs.get(i).setPatient(chairs.get(i + 1).getPatient());
			} else {
				chairs.get(i).setPatient(null);
			}
		}
	}

	public synchronized void moveQueue(Patient patient) {
		for (int i = 0; i < chairs.size(); i++) {
			Patient compare = chairs.get(i).getPatient();
			if (compare != null) {
				if (compare.getPatientName().equals(patient.getPatientName())) {
					chairs.get(i).setPatient(null);
					for (int j = i; j < chairs.size(); j++) {
						if (j + 1 < chairs.size()) {
							chairs.get(j).setPatient(chairs.get(j + 1).getPatient());
						} else {
							chairs.get(j).setPatient(null);
						}
					}
				}
			}
		}
	}

	public class GeneratorPatients extends TimerTask {
		private Clinic clinic;
		private int count;
		private Random random;

		public GeneratorPatients(Clinic clinic) {
			random = new Random();
			this.clinic = clinic;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (working) {
				count++;
				Patient patient = new Patient(Integer.toString(count), random.nextInt(20) + 10, clinic);
				patient.start();
			}
		}

	}
}
