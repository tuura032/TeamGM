package options;

import java.sql.SQLException;

import entity.Team;

public class ShowCapSpace implements MenuOptions {

	
	@Override
	public void execute() throws SQLException {
		
		// Get team by id
		System.out.print("Enter teamId: ");
		int teamId = Integer.parseInt(scanner.nextLine());
		Team team = teamDao.getTeamById(teamId);
		
		// Display Team Finances
		System.out.println(team.getTeam_name() + " cap space: " + team.getCap_space() 
			+ " remaining, and " + team.getDeadspace() + " in " + team.getSeason() + " deadspace");
	}
	
}
