package entity;


public class PlayerSalary {

	private int id;
	private int salary;
	private int year;
	private int deadspace;
	
	public PlayerSalary(int id, int salary, int year, int deadspace) {
		this.setId(id);
		this.setSalary(salary);
		this.setYear(year);
		this.setDeadspace(deadspace);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDeadspace() {
		return deadspace;
	}

	public void setDeadspace(int deadspace) {
		this.deadspace = deadspace;
	}
}
