package options;

import java.sql.SQLException;

import entity.Player;
import entity.PlayerSalary;
import entity.Team;

public class CutPlayer implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		
		// switch for calculating cap impact
		String option = "Cut";
		
		// Get Player to be cut and playersalary
		System.out.print("Who do you want to cut? (full name) ");
		String first = scanner.next();
		String last = scanner.next();
		
		Player player = playerDao.getPlayer(first, last);
		PlayerSalary salary = playerDao.getContractDetails(player).get(0);
		Team team = teamDao.getTeamById(player.getId());
		
		// Make sure user can afford to cut the player
		if (team.getCap_space() < salary.getDeadspace()) {
			System.out.println("Not enough cap space to cut this player.");
			System.out.println("Cap Space: " + team.getCap_space() + "\tDeadspace created: " + salary.getDeadspace());
		} else {
			// Ensure user wants to cut player
			System.out.println("Cutting this player will result in dead cap of " + salary.getDeadspace() + " and savings of " + (salary.getSalary() - salary.getDeadspace()));
			System.out.print("Continue? (y/n) ");
			String answer = scanner.next();
			if (answer.equals("y") || answer.equals("yes")) {
				playerDao.deleteSalaryByPlayerId(player.getId());
				playerDao.cutPlayerById(player.getId());
				
				// Update Team Financials
				Team updated_team = helper.calculateCapImpact(teamDao.getTeamById(player.getTeamId()), salary, option); 
				teamDao.updateCapSpace(updated_team);
				
			} else if (answer.equals("n") || answer.equals("no")) {
				System.out.println("Cancelled - no changes made.");
			} else {
				System.out.println("Invalid input.");
			}
		}
		
	}

	
}
