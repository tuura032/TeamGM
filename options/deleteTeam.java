package options;

import java.sql.SQLException;

public class deleteTeam implements MenuOptions {
	
	@Override
	public void execute() throws SQLException {
		
		// Get ID of team to delete
		System.out.println("Enter the ID of the team you wish to delete: ");
		int team_id = Integer.parseInt(scanner.nextLine());
		System.out.println("Warning! This will delete all players and salaries on this roster.");
		System.out.print("Continue deleting team? (y/n) ");
		String answer = scanner.nextLine();
		
		// delete team (and associated play and salaries) if input is valid
		if (answer.equals("y") || answer.equals("yes")) {
			teamDao.deleteTeamById(team_id);
		} else if (answer.equals("n") || answer.equals("no")) {
			System.out.println("Cancelled - no changes made.");
		} else {
			System.out.println("Invalid input.");
		}
	}
}
