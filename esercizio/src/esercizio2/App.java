package esercizio2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

	static Connection conn = null;

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/Week15Day1Esercizio2?useSLL=false";
		String username = "postgres";
		String password = "1234";

		try {
			System.out.println("Connecting to db " + url);

			conn = DriverManager.getConnection(url, username, password);

			System.out.println("Connected!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// INSERT STUDENTS

//		Student student1 = new Student("Pino", "Silvestre", "M", LocalDate.of(2000, 6, 19), 7.5, 6, 9);
//		Student student2 = new Student("Pino", "Marino", "M", LocalDate.of(2005, 9, 1), 7, 6, 8);
//		Student student3 = new Student("Abete", "Montano", "F", LocalDate.of(2003, 2, 26), 8, 7, 9);
//		Student student4 = new Student("Castagno", "Spinoso", "F", LocalDate.of(2002, 11, 8), 6, 4, 8);
//		Student student5 = new Student("Nocciolo", "Silvestre", "M", LocalDate.of(2001, 4, 12), 6.5, 6, 7);
//		insertStudent(student1);
//		insertStudent(student2);
//		insertStudent(student3);
//		insertStudent(student4);
//		insertStudent(student5);

		// UPDATE STUDENT

//		HashMap<String, Object> studentUpdated = new HashMap<>();
//		studentUpdated.put("name", "Limone");
//		studentUpdated.put("lastname", "Selvatico");
//		studentUpdated.put("max_vote", 10);
//		updateStudent(1, studentUpdated);

		// DELETE STUDENT
//		deleteStudent(5);

		// SELECT: GET BEST

		Student theBest = getBest();
		System.out.println("The best student is: " + theBest.getName() + " " + theBest.getLastname()
				+ " with an average of: " + theBest.getAvg());

		// SELECT: STUDENTS BETWEEN

		List<Student> studentInRange = getVoteRange(6, 9);
		System.out.println("Students in the selected range: ");
		for (Student student : studentInRange) {
			System.out.println("Name: " + student.getName());
			System.out.println("Lastname: " + student.getLastname());
			System.out.println("Gender: " + student.getGender());
			System.out.println("Birthdate: " + student.getBirthdate());
			System.out.println("Avg: " + student.getAvg());
			System.out.println("Min Vote: " + student.getMin_vote());
			System.out.println("Max Vote: " + student.getMax_vote());
		}

	}

	public static void insertStudent(Student s) {
		String sqlInsert = "INSERT INTO public.school_students (name, lastname, gender, birthdate, avg, min_vote, max_vote) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement t = conn.prepareStatement(sqlInsert);

			t.setString(1, s.getName());
			t.setString(2, s.getLastname());
			t.setString(3, s.getGender());
			t.setDate(4, Date.valueOf(s.getBirthdate()));
			t.setDouble(5, s.getAvg());
			t.setInt(6, s.getMin_vote());
			t.setInt(7, s.getMax_vote());
			t.execute();

			System.out.println("Student inserted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateStudent(int id, HashMap<String, Object> s) {
		String sqlUpdate = "UPDATE public.school_students SET ";
		for (String key : s.keySet()) {
			sqlUpdate += key + "=?, ";
		}
		sqlUpdate = sqlUpdate.substring(0, sqlUpdate.length() - 2);
		sqlUpdate += " WHERE id=?";
		try {
			PreparedStatement t = conn.prepareStatement(sqlUpdate);
			int index = 1;
			for (String key : s.keySet()) {
				Object value = s.get(key);
				if (value instanceof String) {
					t.setString(index, (String) value);
				} else if (value instanceof LocalDate) {
					t.setDate(index, Date.valueOf((LocalDate) value));
				} else if (value instanceof Double) {
					t.setDouble(index, (Double) value);
				} else if (value instanceof Integer) {
					t.setInt(index, (Integer) value);
				}
				index++;
			}

			t.setInt(index, id);

			int updated = t.executeUpdate();
			if (updated > 0) {
				System.out.println("Studente modificato");
			} else {

				System.out.println("Nessuno studente trovato con l'id corrispondente");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteStudent(int id) {
		String sqlDelete = "DELETE FROM public.school_students WHERE id = ?";
		try {
			PreparedStatement s = conn.prepareStatement(sqlDelete);
			s.setInt(1, id);
			int deleted = s.executeUpdate();

			if (deleted > 0) {
				System.out.println("Studente rimosso");
			} else {
				System.out.println("Nessuno studente trovato con l'id corrispondente");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Student getBest() {
		String sqlSelect = "SELECT * FROM public.school_students ORDER BY avg DESC LIMIT 1";
		try {
			Statement s = conn.createStatement();
			ResultSet bestStudent = s.executeQuery(sqlSelect);
			if (bestStudent.next()) {
				String name = bestStudent.getString("name");
				String lastname = bestStudent.getString("lastname");
				String gender = bestStudent.getString("gender");
				LocalDate birthdate = bestStudent.getDate("birthdate").toLocalDate();
				double avg = bestStudent.getDouble("avg");
				int min_vote = bestStudent.getInt("min_vote");
				int max_vote = bestStudent.getInt("max_vote");

				return new Student(name, lastname, gender, birthdate, avg, min_vote, max_vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<Student> getVoteRange(int min, int max) {
		String sqlSelect = "SELECT * FROM public.school_students WHERE min_vote >= " + min + " AND max_vote <= " + max;
		List<Student> students = new ArrayList<>();
		try {
			Statement s = conn.createStatement();
			ResultSet resultSet = s.executeQuery(sqlSelect);

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String lastname = resultSet.getString("lastname");
				String gender = resultSet.getString("gender");
				LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
				double avg = resultSet.getDouble("avg");
				int min_vote = resultSet.getInt("min_vote");
				int max_vote = resultSet.getInt("max_vote");

				Student student = new Student(name, lastname, gender, birthdate, avg, min_vote, max_vote);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
}
