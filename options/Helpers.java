package options;

import java.sql.SQLException;
import dao.PlayerDao;
import dao.TeamDao;
import entity.Team;
import entity.Player;
import entity.PlayerSalary;
import application.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Helpers {

	PlayerDao playerDao = new PlayerDao();
	Scanner scanner = new Scanner(System.in);
	TeamDao teamDao = new TeamDao();
	
	public void signContract(Player player) throws SQLException {
		
		// Switch for calculating cap impact
		String option = "Sign";
		
		// Get User Input for each contract year
		System.out.print("How many years is the contract for? ");
		int contract_length = Integer.parseInt(scanner.nextLine());
		
		for (int i = 0; i < contract_length; i++) {
			System.out.print("Salary of year " + (i + 1) + " (ex: 10000000): ");
			int salary = Integer.parseInt(scanner.nextLine());
			System.out.print("What calendar year does this apply to? (YYYY): ");
			int year = Integer.parseInt(scanner.nextLine());
			System.out.print("How much dead cap is there for cutting the player in year " + year + "? ");
			int deadspace = Integer.parseInt(scanner.nextLine());
			
			// Create new player salary
			playerDao.signContract(player.getId(), salary, year, deadspace);
			
			// Update Available Cap Space
			if (year == Menu.CURRENT_YEAR) {
				Team updated_team = calculateCapImpact(teamDao.getTeamById(player.getTeamId()), playerDao.getContractDetails(player).get(0), option);
				teamDao.updateCapSpace(updated_team);
			}
		}
	}
	
	
	public Team calculateCapImpact(Team team, PlayerSalary salary, String option) throws SQLException {
		
		// Calculate Finances for cutting and signing players
		if (option.equals("Cut")) {
			int cap_space = team.getCap_space();
			int updated_deadspace = team.getDeadspace() + salary.getDeadspace();
			int savings = (salary.getSalary() - salary.getDeadspace());
			int updated_cap_space = ((cap_space + savings));
			team.setCap_space(updated_cap_space);
			team.setDeadspace(updated_deadspace);
		} else if (option.equals("Sign")) {
			int updated_cap_space = team.getCap_space() - salary.getSalary();
			team.setCap_space(updated_cap_space);
		}
		return team;
	}
	
	public void displayStarters(Team team, String selection) {
		
		// Display all players at a position, in order.
		List<Player> roster = new ArrayList<Player>();
		roster = team.getRoster();
		
		// Iterate through roster, displaying only offensive or defensive starters
		if (selection.toLowerCase().equals("offense")) {
			for (String position : Menu.OFFENSIVE_POSITIONS) {
				for (int i = 0; i < roster.size(); i++) {
					if (roster.get(i).getPosition().equals(position) && roster.get(i).getDepth() == 1) {
						System.out.println(roster.get(i).getFirst_name() + " " + roster.get(i).getLast_name() + " " + roster.get(i).getPosition() + roster.get(i).getDepth());
						roster.remove(i);
						i--;
					} else if (roster.get(i).getDepth() != 1) {
						roster.remove(i);
						i--;
					}
				}
			}
		} else {
			for (String position : Menu.DEFENSIVE_POSITIONS) {
				for (int i = 0; i < roster.size(); i++) {
					if (roster.get(i).getPosition().equals(position) && roster.get(i).getDepth() == 1) {
						System.out.println(roster.get(i).getFirst_name() + " " + roster.get(i).getLast_name() + " " + roster.get(i).getPosition() + roster.get(i).getDepth());
						roster.remove(i);
						i--;
					} else if (roster.get(i).getDepth() != 1) {
						roster.remove(i);
						i--;
					}
				}
			}
		}
		
	}
	
	public boolean isValidPosition(String user_position, List<String> positions) {
		
		// Checks to confirm position group is valid
		boolean check = false;
		for (String position : positions) {
			if (position.equals(user_position)) {
				check = true;
				break;
			}
		}
		return check;
	}
}
