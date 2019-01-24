package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Player;
import entity.PlayerSalary;


public class PlayerDao {

	private Connection connection;
	private final String GET_MEMBERS_BY_TEAM_ID_QUERY = "SELECT * FROM players WHERE team_id = ?";
	private final String SIGN_PLAYER_BY_TEAM_ID = "INSERT INTO players(first_name, last_name, position, depth, team_id) "
			+ "values(?, ?, ?, ?, ?)";
	private final String GET_MEMBERS_BY_TEAM_NAME = "SELECT p.id, p.first_name, p.last_name, p.position, p.depth, p.team_id FROM players p "
			+ "INNER JOIN teams t ON p.team_id = t.id "
			+ "WHERE t.team_name = ?";
	private final String SELECT_PLAYER_BY_NAME = "SELECT * FROM players WHERE first_name = ? and last_name = ? limit 1";
	private final String CREATE_NEW_PLAYER_SALARY = "INSERT INTO salary(player_id, salary, year, deadspace) values(?,?,?,?)";
	private final String GET_PLAYER_SALARY = "SELECT * FROM salary WHERE player_id = ? ORDER BY year";
	private final String CUT_PLAYER_BY_ID = "DELETE FROM players where id = ?";
	private final String DELETE_SALARY_BY_PLAYER_ID = "DELETE FROM salary where player_id = ?";
	private final String UPDATE_PLAYER = "UPDATE players SET first_name = ?, last_name = ?, position = ?, depth = ? WHERE id = ?";
	private final String UPDATE_PLAYER_SALARY = "UPDATE salary SET salary = ?, deadspace = ? WHERE id = ? and year = ?";
	
	public PlayerDao() {
		connection = DBConnection.getConnection();
	}
	
	public List<Player> getPlayersByTeamId(int teamId) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(GET_MEMBERS_BY_TEAM_ID_QUERY);
		ps.setInt(1, teamId);
		ResultSet rs = ps.executeQuery();
		List<Player> roster = new ArrayList<Player>();
		
		while (rs.next()) {
			roster.add(new Player(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
		}
		
		return roster;
	}
	
	
	// Might b eable to delete this one
	public List<Player> getPlayersByTeamName(String team_name) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(GET_MEMBERS_BY_TEAM_NAME);
		ps.setString(1, team_name);
		ResultSet rs = ps.executeQuery();
		List<Player> roster = new ArrayList<Player>();
		
		while (rs.next()) {
			roster.add(new Player(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
		}
		
		return roster;
	}
	
	public void signPlayer(String first, String last, String position, int depth, int team_id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SIGN_PLAYER_BY_TEAM_ID);
		ps.setString(1, first);
		ps.setString(2, last);
		ps.setString(3, position);
		ps.setInt(4, depth);
		ps.setInt(5, team_id);
		ps.executeUpdate();
	}

	public Player getPlayer(String first, String last) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SELECT_PLAYER_BY_NAME);
		ps.setString(1, first);
		ps.setString(2, last);
		ResultSet rs = ps.executeQuery();

		Player player = null;
		while (rs.next()) {
			player = new Player(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
		}
		
		return player;
	}

	public void signContract(int id, int salary, int year, int deadspace) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CREATE_NEW_PLAYER_SALARY);
		ps.setInt(1, id);
		ps.setInt(2, salary);
		ps.setInt(3, year);
		ps.setInt(4, deadspace);
		ps.executeUpdate();
		
	}
	
	public void cutPlayerById(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CUT_PLAYER_BY_ID);
		ps.setInt(1, id);
		ps.executeUpdate();
		
	}
	
	public void deleteSalaryByPlayerId(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(DELETE_SALARY_BY_PLAYER_ID);
		ps.setInt(1, id);
		ps.executeUpdate();
	}

	// Note: This only gets the first year of the salary!!!!! 
	public List<PlayerSalary> getContractDetails(Player player) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(GET_PLAYER_SALARY);
		ps.setInt(1, player.getId());
		ResultSet rs = ps.executeQuery();
		List<PlayerSalary> salaries = new ArrayList<PlayerSalary>();
		
		//PlayerSalary salary = null;
		while (rs.next()) {
			salaries.add(new PlayerSalary(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
			break;
		}
		
		return salaries;
	}

	/*
	 * update player methods
	 */
	
	public void updatePlayer(Player player) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_PLAYER);
		ps.setString(1, player.getFirst_name());
		ps.setString(2, player.getLast_name());
		ps.setString(3, player.getPosition());
		ps.setInt(4, player.getDepth());
		ps.setInt(5, player.getId());
		ps.executeUpdate();
		
	}

	public void updatePlayerSalary(PlayerSalary salary) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_PLAYER_SALARY);
		ps.setInt(1, salary.getSalary());
		ps.setInt(2, salary.getDeadspace());
		ps.setInt(3, salary.getId());
		ps.setInt(4, salary.getYear());
		ps.executeUpdate();
	
	}
	
}
