package options;

import java.sql.SQLException;
import java.util.List;

import entity.Team;

public class GetStarters implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		// help user find team
		System.out.println("Please Select a Team By Id");
		List<Team> teams = teamDao.getTeams();
		for (Team team : teams) {
			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
		}
		System.out.print("Team Id? ");
		int teamId = Integer.parseInt(scanner.nextLine());
		
		System.out.println("View Offense or Defense? ");
		String selection = scanner.nextLine();
	
		// Select team from list of teams
		Team selected_team = null;
		for (Team team : teams) {
			if (team.getId() == teamId) {
				selected_team = team;
				break;
			}
		}
		
		if (selection.toLowerCase().equals("offense") || selection.toLowerCase().equals("defense")) {
			helper.displayStarters(selected_team, selection);
		} else {
			System.out.println("Invalid selection.");
		}
		
	}

	
}
