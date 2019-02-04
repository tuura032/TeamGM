package options;

import java.sql.SQLException;

import entity.Player;
import entity.Team;

public class DisplayTeamRoster implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		// Get team by id
		System.out.print("What's the team id? ");
		int teamId = Integer.parseInt(scanner.nextLine());
		Team team = teamDao.getTeamById(teamId);
		
		// Display each player on team and roster size
		for (Player player : team.getRoster()) {
			System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - "
					+ player.getPosition() + player.getDepth() + " (ID: " + player.getId() + ")");
		}
		System.out.println("Roster size: " + team.getRoster().size());
	}
	
}
