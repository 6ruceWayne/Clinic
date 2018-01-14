package com.java;

public class Doctor extends Thread {
	private String name;
	private int norm;
	private int count;
	/* private boolean status; */
	private Clinic clinic;
	private Office office;

	public Doctor(String name, int norm, Clinic clinic) {
		this.name = name;
		this.norm = norm;
		this.clinic = clinic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Доктор " + name + " пришел на работу и ждет пациентов. Сегодня он собирается вылечить "
				+ norm + " пациентов");

		while (norm > count) {
			if (!office.isFree()) {
				startCure(office.getPatient());
				if (norm > count) {
					System.out.println("Доктор " + name + " пошел проверять наличие пациентов");
					/* Patient patient = clinic.callPatient(); */
					while (clinic.invitePatient(office) && norm > count) {
						System.out
								.println("Доктор " + name + " вызвал пациента " + office.getPatient().getPatientName());
						startCure(office.getPatient());
						/* office.setPatient(null); */
					}
					if (norm > count) {
						System.out.println("Доктор " + name
								+ " не обнаружил ожидающих приема пациентов и вернулся в кабинет ждать");
						/* office.setStatus(true); */
						office.setPatient(null);
						office.setStatus(true);
					}
				}
			}
		}
		this.clinic.closeOffice(office);
		System.out.println("Доктор " + name + " закончил свою работу и ушел");

	}

	public void setOffice(Office chamber) {
		this.office = chamber;
	}

	private boolean checkPatient() {
		if (clinic.hasPatient()) {
			return true;
		}
		return false;
	}

	public synchronized void startCure(Patient patient) {
		System.out.println("Доктор " + name + " начал лечить пациента " + patient.getPatientName());
		try {
			this.wait(patient.getHard() * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patient.isCurred();

		count++;
		System.out.println("Доктор " + name + " вылечил " + count + " пациентов. Осталось еще " + (norm - count));
	}

}
