package options;

import java.sql.SQLException;

import entity.Team;

public class SignPlayer implements MenuOptions {
	
	@Override
	public void execute() throws SQLException {
		
		// Get Player information from user
		System.out.print("First name? ");
		String first = scanner.nextLine();
		System.out.print("Last name? ");
		String last = scanner.nextLine();
		System.out.print("Position? ");
		String position = scanner.nextLine();
		System.out.print("Where is the player on the depth chart? (Enter 1 if starting): ");
		int depth = Integer.parseInt(scanner.nextLine());
		System.out.println("Please Select a Team By Id");
		
		// Display All Teams to Assist User
		for (Team team : teamDao.getTeams()) {
			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
		}
		System.out.print("Team Id? ");
		int team_id = Integer.parseInt(scanner.nextLine());
		
		// before signing the player, ensure enough cap space
		
		// Create a new player
		playerDao.signPlayer(first, last, position, depth, team_id);
		
		// Create a new player salary
		helper.signContract(playerDao.getPlayer(first, last));
	}

}
