package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.PlayerDao;
import dao.TeamDao;
import entity.Player;
import entity.PlayerSalary;
import entity.Team;


public class Menu {

	private PlayerDao playerDao = new PlayerDao();
	private TeamDao teamDao = new TeamDao();
	private Scanner scanner = new Scanner(System.in);
	private int current_year = 2019;
	private List<String> options = Arrays.asList(
			"\nTEAM OPTIONS\n-------------",
			"1) Show All Teams", 
			"2) Show Team Roster",
			"3) Create Team",
			"4) Delete Team",
			"5) Show Cap Space",
			"\nPLAYER OPTIONS\n--------------",
			"6) Sign Player",
			"7) Show Player Profile",
			"8) Update Player Information",
			"9) Remove a Player From Team",
			"\nTEAM BUILDING\n-------------",
			"10) Show Position Group",
			"11) Get Starters"
			);
	private List<String> offensive_positions = Arrays.asList(
			"QB", "RB", "WR", "LT", "LG", "C", "RG", "RT", "TE");
	private List<String> all_positions = Arrays.asList(
			"QB", "RB", "WR", "LT", "LG", "C", "RG", "RT", "TE",
			"DE", "DT", "NT", "OLB", "MLB", "CB", "SS", "FS");
	private List<String> defensive_positions = Arrays.asList(
			"DE", "DT", "NT", "OLB", "MLB", "CB", "SS", "FS");
			
	
	public void start() {
		String selection = "";
		do {
			printMenu();
			selection = scanner.nextLine();
			
			try {
				if (selection.equals("1")) {
					displayTeams();
				} else if (selection.equals("2")) {
					displayTeamRoster();
				} else if (selection.equals("3")) {
					createTeam();
				} else if (selection.equals("4")) {
					deleteTeam();
				} else if (selection.equals("5")) {
					showCapSpace();
				} else if (selection.equals("6")) {
					signPlayer();
				} else if (selection.equals("7")) {
					getPlayer();
				} else if (selection.equals("8")) {
					updatePlayer();
				} else if (selection.equals("9")) {
					cutPlayer();
				} else if (selection.equals("10")) {
					showPositionGroup();
				} else if (selection.equals("11")) {
					getStarters();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("");
			System.out.println("Press enter to continue...");
			scanner.nextLine();
			
		} while (!selection.equals("-1"));
	}
	
	private void printMenu() {
		System.out.println("Select an option :\n----------------");
		for (int i = 0; i < options.size(); i++) {
			System.out.println(options.get(i));
		}
	}
	
	/*
	 * Team related functions
	 */
	
	private void displayTeams() throws SQLException {
		List<Team> teams = teamDao.getTeams();
		for (Team team : teams) {
			System.out.println(team.getId() + ") " + team.getTeam_name());
		}
	}
	
	private void createTeam() throws SQLException {
		System.out.print("What team would you like to create? ");
		String team_name = scanner.nextLine();
		System.out.print("What season will this team describe? ");
		int season = Integer.parseInt(scanner.nextLine());
		System.out.print("Projected Salary cap? ");
		int salary_cap = Integer.parseInt(scanner.nextLine());
		System.out.print("How much dead cap in this year? ");
		int deadspace = Integer.parseInt(scanner.nextLine());
		
		int cap_space = salary_cap - deadspace;
		
		teamDao.createTeam(team_name, salary_cap, cap_space, deadspace, season);
		
	}
	
	private void deleteTeam() throws SQLException {
		System.out.println("Enter the ID of the team you wish to delete: ");
		int team_id = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Warning! This will delete all players and salaries on this roster.");
		System.out.print("Continue deleting team? (y/n)");
		String answer = scanner.nextLine();
		
		if (answer.equals("y") || answer.equals("yes")) {
			teamDao.deleteTeamById(team_id);
		} else if (answer.equals("n") || answer.equals("no")) {
			System.out.println("Cancelled - no changes made.");
		} else {
			System.out.println("Invalid input.");
		}
	}
	
	private void displayTeamRoster() throws SQLException {
		System.out.print("What's the team id? ");
		int teamId = Integer.parseInt(scanner.nextLine());
		Team team = teamDao.getTeamById(teamId);
		
		for (Player player : team.getRoster()) {
			System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - "
					+ player.getPosition() + player.getDepth() + " (ID: " + player.getId() + ")");
		}
		System.out.println("Roster size: " + team.getRoster().size());
	}
	
	private void showCapSpace() throws SQLException {
		System.out.print("Enter teamId: ");
		int teamId = Integer.parseInt(scanner.nextLine());
		
		Team team = teamDao.getTeamById(teamId);
		
		System.out.println(team.getTeam_name() + " cap space: " + team.getCap_space() 
			+ " remaining, and " + team.getDeadspace() + " in " + team.getSeason() + " deadspace");
	}
	
	/*
	 * Player related functions
	 */
	
	private void signPlayer() throws SQLException {
		System.out.print("First name? ");
		String first = scanner.nextLine();
		System.out.print("Last name? ");
		String last = scanner.nextLine();
		System.out.print("Position? ");
		String position = scanner.nextLine();
		System.out.print("Where is the player on the depth chart? (Enter 1 if starting): ");
		int depth = Integer.parseInt(scanner.nextLine());
		System.out.println("Please Select a Team By Id");
		for (Team team : teamDao.getTeams()) {
			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
		}
		System.out.print("Team Id? ");
		int team_id = Integer.parseInt(scanner.nextLine());
		
		// before signing the player, I need to check to make sure I have enough cap space
		
		playerDao.signPlayer(first, last, position, depth, team_id);
		
		signContract(playerDao.getPlayer(first, last));
	}
	
	private void signContract(Player player) throws SQLException {
		String option = "Sign";
		System.out.print("How many years is the contract for? ");
		int contract_length = Integer.parseInt(scanner.nextLine());
		
		for (int i = 0; i < contract_length; i++) {
			System.out.print("Salary of year " + (i + 1) + " (ex: 10000000): ");
			int salary = Integer.parseInt(scanner.nextLine());
			System.out.print("What calendar year does this apply to? (YYYY): ");
			int year = Integer.parseInt(scanner.nextLine());
			System.out.print("How much dead cap is there for cutting the player in year " + year + "? ");
			int deadspace = Integer.parseInt(scanner.nextLine());
			
			playerDao.signContract(player.getId(), salary, year, deadspace);
			
			if (year == current_year) {
				
				// also make sure this works since I updated getcontract details to take a player, not first/last
				Team updated_team = calculateCapImpact(teamDao.getTeamById(player.getTeamId()), playerDao.getContractDetails(player).get(0), option);
				teamDao.updateCapSpace(updated_team);
			}
		}
	}
	
	private void cutPlayer() throws SQLException {
		String option = "Cut";
		System.out.print("Who do you want to cut? (full name) ");
		String first = scanner.next();
		String last = scanner.next();
		
		Player player = playerDao.getPlayer(first, last);
		
		PlayerSalary salary = playerDao.getContractDetails(player).get(0);
		
		// I need to make sure I can afford to cut the player here.
		
		System.out.println("Cutting this player will result in dead cap of " + salary.getDeadspace() + " and savings of " + (salary.getSalary() - salary.getDeadspace()));
		System.out.print("Continue? (y/n) ");
		String answer = scanner.next();
		
		if (answer.equals("y") || answer.equals("yes")) {
			
			playerDao.deleteSalaryByPlayerId(player.getId());
			playerDao.cutPlayerById(player.getId());
			
			Team team = calculateCapImpact(teamDao.getTeamById(player.getTeamId()), salary, option); 
			
			teamDao.updateCapSpace(team);
			
		} else if (answer.equals("n") || answer.equals("no")) {
			System.out.println("Cancelled - no changes made.");
		} else {
			System.out.println("Invalid input.");
		}
	}
	
	private Team calculateCapImpact(Team team, PlayerSalary salary, String option) throws SQLException {
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
	
	private void getPlayer() throws SQLException {
		System.out.print("Which player do you want information on? (full name) ");
		
		String first = scanner.next();
		String last = scanner.next();
		scanner.nextLine();
		Player player = playerDao.getPlayer(first, last);
		List<PlayerSalary> salaries = playerDao.getContractDetails(player);
		PlayerSalary contract = salaries.get(0);
		System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - " + 
				player.getPosition() + player.getDepth());
		System.out.println("\t" + contract.getYear() + " Contract: " + contract.getSalary());
		System.out.println("\t" + contract.getYear() + " Dead Cap: " + contract.getDeadspace());
	}
	
	
	private void updatePlayer() throws SQLException {
		System.out.println("Which player would you like to update? ");
		System.out.print("First name: ");
		String first = scanner.nextLine();
		System.out.print("Last name: ");
		String last = scanner.nextLine();
		
		Player player = playerDao.getPlayer(first, last);
		
		System.out.print("Would you like to update? 1) info 2) salary?");
		String answer = scanner.nextLine();
		
		if (answer.equals("1") || answer.equals("info")) {
			
			
			System.out.println("Current name: " + player.getFirst_name() + " " + player.getLast_name());
			System.out.print("Enter new first name: ");
			String new_first = scanner.nextLine();
			System.out.print("Enter new last name: ");
			String new_last = scanner.nextLine();
			System.out.println("Current Position and Depth: " + player.getPosition() + player.getDepth());
			System.out.print("Enter new position: ");
			String new_position = scanner.nextLine();
			System.out.println("Enter Depth at position: ");
			int new_depth = Integer.parseInt(scanner.nextLine());
			
			player.setFirst_name(new_first);
			player.setLast_name(new_last);
			player.setPosition(new_position);
			player.setDepth(new_depth);
			
			playerDao.updatePlayer(player);
			
			System.out.println("Update complete!");
			
		} else if (answer.equals("2") || answer.equals("salary")) {
			// get salary object
			PlayerSalary salary = playerDao.getContractDetails(player).get(0);
			System.out.println("Current contract for " + player.getFirst_name() + " " + player.getLast_name());
			System.out.println(salary.getYear() + " Salary: " + salary.getSalary() + " Dead cap: " + salary.getDeadspace());
			System.out.print("Enter new salary: ");
			int new_salary = Integer.parseInt(scanner.nextLine());
			System.out.print("Enter updated dead cap: ");
			int new_deadspace = Integer.parseInt(scanner.nextLine());
			
			salary.setSalary(new_salary);
			salary.setDeadspace(new_deadspace);
			
			playerDao.updatePlayerSalary(salary);
			
		} else {
			System.out.println("Invalid option.");
		}
	}
	
	private void showPositionGroup() throws SQLException {
		
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
		if (!isValidPosition(user_position, all_positions)) {
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
	
	private void getStarters() throws SQLException {
		
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
			displayStarters(selected_team, selection);
		} else {
			System.out.println("Invalid selection.");
		}
		
	}
	
	private void displayStarters(Team team, String selection) {
		
		// Display all players at a position, in order.
		List<Player> roster = new ArrayList<Player>();
		roster = team.getRoster();
		
		// This might be bad design - I made a copy of the roster, and I'm removing players from the roster as I go
		// My goal was to only have to deal with each player only 1 time, as I found that to avoid the nested loops I'd have to start over with my entity + db design.
		if (selection.toLowerCase().equals("offense")) {
			for (String position : offensive_positions) {
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
			for (String position : defensive_positions) {
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
	
	private boolean isValidPosition(String user_position, List<String> positions) {
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




//menu hierarchy

// Show All Teams
	// CRUD Teams


// Select Team by name
	// CRUD Player (edit the roster)


// all features

// Player CRUD (and keep deadspace!)
// Team CRUD (salarycap / deadspace, etc)
// 
// View Team Roster
// View All players on a team at a position group (OL, DL, etc)
// view all players on a team at a position (LG, NT, etc)

// 

/*
 * Design errors
 */
//Problem: Each player has 1 team_id associated with it. 
		// Solution: this program only works for player lifetime contracts, and team 2019 season. 
		// Fix: re-do teams db. Each team has a sub DB of each season. 