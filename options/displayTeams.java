package options;

import java.sql.SQLException;
import java.util.List;

import entity.Team;

public class DisplayTeams implements MenuOptions {
	
	@Override
	public void execute() throws SQLException {
		
		// instantiate and display all teams
		List<Team> teams = teamDao.getTeams();
		for (Team team : teams) {
			System.out.println(team.getId() + ") " + team.getTeam_name());
		}
	}
}
