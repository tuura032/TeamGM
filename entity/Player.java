package entity;

public class Player {

	private int id;
	private String first_name;
	private String last_name;
	private String position;
	private int depth; 
	private int teamId;
	// Add PlayerSalary


	public Player(int id, String first_name, String last_name, String position, int depth, int teamId) {
		this.setId(id);
		this.setFirst_name(first_name);
		this.setLast_name(last_name);
		this.setPosition(position);
		this.setDepth(depth);
		this.setTeamId(teamId);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getTeamId() {
		return teamId;
	}


	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
}
