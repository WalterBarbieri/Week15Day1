package esercizio2;

import java.time.LocalDate;

public class Student {
	String name;
	String lastname;
	String gender;
	LocalDate birthdate;
	double avg;

	public Student(String name, String lastname, String gender, LocalDate birthdate, double avg, int min_vote,
			int max_vote) {
		super();
		this.setName(name);
		this.setLastname(lastname);
		this.setGender(gender);
		this.setBirthdate(birthdate);
		this.setAvg(avg);
		this.setMin_vote(min_vote);
		this.setMax_vote(max_vote);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public int getMin_vote() {
		return min_vote;
	}

	public void setMin_vote(int min_vote) {
		this.min_vote = min_vote;
	}

	public int getMax_vote() {
		return max_vote;
	}

	public void setMax_vote(int max_vote) {
		this.max_vote = max_vote;
	}

	int min_vote;
	int max_vote;
}
