package beans;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = -1355807851822025659L;
	private String EmployeeID;
	private String FirstName;
	private String LastName;
	private String Role;

	private String Password;

	public Employee(String emp, String fn, String ln, String r, String pwd) {
		this.EmployeeID = emp;
		this.FirstName = fn;
		this.LastName = ln;
		this.Role = r;
		this.Password = pwd;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

	public String getFisrtName() {
		return FirstName;
	}

	public void setFisrtName(String fisrtName) {
		FirstName = fisrtName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "Hello" + this.FirstName;
	}
}