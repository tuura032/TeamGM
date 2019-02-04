package options;

import java.sql.SQLException;
import java.util.List;

import application.Menu;
import entity.Player;
import entity.Team;

public class ShowPositionGroup implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		// Help user find team ID
		System.out.println("Please Select a Team By Id");
		List<Team> teams = teamDao.getTeams();
		for (Team team : teams) {
			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
		}
		System.out.print("Team Id? ");
		int teamId = Integer.parseInt(scanner.nextLine());
		
		// Get position group
		System.out.print("Which position would you like to view? (ex: LG, OLB) ");
		String user_position = scanner.nextLine();
		
		// If valid, select the team and display the position
		if (!helper.isValidPosition(user_position, Menu.ALL_POSITIONS)) {
			System.out.println("invalid input.");
		} else {
			// Select team from list of teams
			Team selected_team = null;
			for (Team team : teams) {
				if (team.getId() == teamId) {
					selected_team = team;
					break;
				}
			}
			// Display All Players at a position
			for (Player player : selected_team.getRoster()) {
				if (player.getPosition().equals(user_position)) {
					System.out.println(player.getFirst_name() + " " + player.getLast_name() + " " + player.getPosition() + player.getDepth());
				}
			}
		}	
		
	}

}
