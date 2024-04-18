package model;

import main.Logable;

public class Employee extends Person implements Logable{
	int employeeId;
	final int USER = 123;
	final String PASSWORD = "test";
	
	public boolean login (int user, String password) {
		if (user == USER && password.equals(PASSWORD)) {
			return true;
		}else {
			return false;
		}
	}
}
