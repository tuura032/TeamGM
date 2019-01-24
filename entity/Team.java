package entity;

import java.util.List;

public class Team {

	private int id;
	private String team_name;
	private int salary_cap;
	private int cap_space;
	private int deadspace;
	private int season;
	private List<Player> roster;
	
	public Team(int id, String team_name, int salary_cap, int cap_space, int deadspace, int season, List<Player> roster) {
		this.setId(id);
		this.setTeam_name(team_name);
		this.setSalary_cap(salary_cap);
		this.setCap_space(cap_space);
		this.setDeadspace(deadspace);
		this.setSeason(season);
		this.setRoster(roster);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public int getSalary_cap() {
		return salary_cap;
	}

	public void setSalary_cap(int salary_cap) {
		this.salary_cap = salary_cap;
	}

	public int getCap_space() {
		return cap_space;
	}

	public void setCap_space(int cap_space) {
		this.cap_space = cap_space;
	}

	public int getDeadspace() {
		return deadspace;
	}

	public void setDeadspace(int deadspace) {
		this.deadspace = deadspace;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public List<Player> getRoster() {
		return roster;
	}

	public void setRoster(List<Player> roster) {
		this.roster = roster;
	}
}
