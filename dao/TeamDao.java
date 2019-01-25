package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Player;
import entity.Team;

public class TeamDao {

	private Connection connection;
	private PlayerDao playerDao;

	private final String CREATE_TEAM = "INSERT INTO teams(team_name, salary_cap, cap_space, deadspace, season)"
			+ "values(?, ?, ?, ?, ?)";
	private final String GET_TEAMS_QUERY = "SELECT * from teams";
	private final String GET_TEAM_BY_TEAM_ID = "SELECT * FROM teams where id = ?";
	private final String UPDATE_CAP_SPACE = "UPDATE teams SET cap_space = ?, deadspace = ? WHERE id = ?";
	private final String DELETE_TEAM_BY_TEAM_ID = "DELETE FROM teams where id = ?";
	private final String DELETE_ROSTER_BY_TEAM_ID = "DELETE FROM players where team_id = ?";
	private final String DELETE_SALARIES_BY_PLAYER_ID = "DELETE FROM salary where player_id = ?";
	
	
	public TeamDao() {
		connection = DBConnection.getConnection();
		playerDao = new PlayerDao();
	}
	
	/*
	 * Get Team Methods
	 */
	
	public Team getTeamById(int teamId) throws SQLException {
		
		// Get A Team
		PreparedStatement ps = connection.prepareStatement(GET_TEAM_BY_TEAM_ID);
		ps.setInt(1, teamId);
		ResultSet rs = ps.executeQuery();
		Team team = null;
		
		// Create and Return Team Instance
		while (rs.next()) {
			team = objectifyTeam(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
		}
		return team;
	}
	
	public List<Team> getTeams() throws SQLException {
		
		// Get all teams
		ResultSet rs = connection.prepareStatement(GET_TEAMS_QUERY).executeQuery();
		List<Team> teams = new ArrayList<Team>();
		
		// Return a list of all teams
		while (rs.next()) {
			teams.add(objectifyTeam(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
		}
		return teams;
	}
	
	
	private Team objectifyTeam(int id, String team_name, int salary_cap, int cap_space, int deadspace, int season) throws SQLException {
		// Returns a Team entity
		return new Team(id, team_name, salary_cap, cap_space, deadspace, season, playerDao.getPlayersByTeamId(id));
	}

	/*
	 * Create Team Method
	 */
	
	public void createTeam(String team_name, int salary_cap, int cap_space, int deadspace, int season) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CREATE_TEAM);
		ps.setString(1, team_name);
		ps.setInt(2, salary_cap);
		ps.setInt(3, cap_space);
		ps.setInt(4, deadspace);
		ps.setInt(5, season);
		ps.executeUpdate();
	}

	/*
	 * Delete Team methods
	 */
	
	public void deleteTeamById(int team_id) throws SQLException {
		
		// Delete Salaries, Players, and team associated with teamId.
		deleteSalariesByTeamId(team_id);
		deleteRosterByTeamId(team_id);
		PreparedStatement ps = connection.prepareStatement(DELETE_TEAM_BY_TEAM_ID);
		ps.setInt(1, team_id);
		ps.executeUpdate();
	}

	private void deleteSalariesByTeamId(int team_id) throws SQLException {
		// get a roster of players on some team
		List<Player> roster = playerDao.getPlayersByTeamId(team_id);
		
		// for each player in the roster, get their id, and delete all salaries with that player_id
		for (Player player : roster) {
			PreparedStatement ps = connection.prepareStatement(DELETE_SALARIES_BY_PLAYER_ID);
			ps.setInt(1, player.getId());
			ps.executeUpdate();
		}
		// consider updating cap_space and deadspace?
	}

	private void deleteRosterByTeamId(int team_id) throws SQLException {
		
		// Deletes Players associated with a teamId if salaries are deleted
		PreparedStatement ps = connection.prepareStatement(DELETE_ROSTER_BY_TEAM_ID);
		ps.setInt(1, team_id);
		ps.executeUpdate();
	}
	
	
	/*
	 * Update Team Method
	 */
	
	public void updateCapSpace(Team team) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_CAP_SPACE);
		ps.setInt(1, team.getCap_space());
		ps.setInt(2, team.getDeadspace());
		ps.setInt(3, team.getId());
		ps.executeUpdate();
	}
}
