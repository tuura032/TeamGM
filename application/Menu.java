package application;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.PlayerDao;
import options.*;

public class Menu {

	private static Scanner scanner = new Scanner(System.in);
	private PlayerDao playerDao = new PlayerDao();

	public final static int CURRENT_YEAR = 2019;
	public List<String> options = Arrays.asList(
			"\nTEAM OPTIONS\n-------------",
			"1) Show All Teams", 
			"2) Show Team Roster",
			"3) Create Team",
			"4) Delete Team",
			"5) Show Cap Space",
			"6) Show Position Group",
			"7) Show Starters",
			"\nPLAYER OPTIONS\n--------------",
			"8) Sign Player",
			"9) Show Player Profile",
			"10) Update Player Information",
			"11) Cut a Player"
			);
	
	// Positions for US Football
	public final static List<String> OFFENSIVE_POSITIONS = Arrays.asList(
			"QB", "RB", "WR", "LT", "LG", "C", "RG", "RT", "TE"); 
	public final static List<String> ALL_POSITIONS = Arrays.asList(
			"QB", "RB", "WR", "LT", "LG", "C", "RG", "RT", "TE",
			"DE", "DT", "NT", "OLB", "MLB", "CB", "SS", "FS");
	public final static List<String> DEFENSIVE_POSITIONS = Arrays.asList(
			"DE", "DT", "NT", "OLB", "MLB", "CB", "SS", "FS");
			
	
	public void start() {
		
		String selection = "";
		do {
			printMenu();
			selection = scanner.nextLine();
			
			try {
				if (selection.equals("1")) {
					//displayTeams();
					MenuOptions menuOptions = new DisplayTeams();
					menuOptions.execute();
				} else if (selection.equals("2")) {
					//displayTeamRoster();
					MenuOptions menuOptions = new DisplayTeamRoster();
					menuOptions.execute();
				} else if (selection.equals("3")) {
					//createTeam();
					MenuOptions menuOptions = new CreateTeam();
					menuOptions.execute();
				} else if (selection.equals("4")) {
					//deleteTeam();
					MenuOptions menuOptions = new DeleteTeam();
					menuOptions.execute();
				} else if (selection.equals("5")) {
					//showCapSpace();
					MenuOptions menuOptions = new ShowCapSpace();
					menuOptions.execute();
				} else if (selection.equals("6")) {
					//showPositionGroup();
					MenuOptions menuOptions = new ShowPositionGroup();
					menuOptions.execute();
				} else if (selection.equals("7")) {
					//getStarters();
					MenuOptions menuOptions = new GetStarters();
					menuOptions.execute();
				} else if (selection.equals("8")) {
					//signPlayer();
					MenuOptions menuOptions = new SignPlayer();
					menuOptions.execute();
				} else if (selection.equals("9")) {
					//getPlayer();
					MenuOptions menuOptions = new GetPlayer();
					menuOptions.execute();
				} else if (selection.equals("10")) {
					//updatePlayer();
					MenuOptions menuOptions = new UpdatePlayer();
					menuOptions.execute();
				} else if (selection.equals("11")) {
					//cutPlayer();
					MenuOptions menuOptions = new CutPlayer();
					menuOptions.execute();
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
		
		// Display list of options
		System.out.println("Select an option :\n----------------");
		for (int i = 0, size = options.size(); i < size; i++) {
			System.out.println(options.get(i));
		}
	}
	
	public static void printWelcome() {
		System.out.println("--------\nWELCOME!\n--------");
		System.out.println("Program Starting...\n\tImplementing Current Year...2019\n"
				+ "\tImplementing roster type....Football");
		System.out.println();
		System.out.println("");
		System.out.println("Press enter to Begin.");
		scanner.nextLine();
	}
	
	/*
	 * Team related functions
	 */
	
//	private void displayTeams() throws SQLException {
//		
//		// instantiate and display all teams
//		List<Team> teams = teamDao.getTeams();
//		for (Team team : teams) {
//			System.out.println(team.getId() + ") " + team.getTeam_name());
//		}
//	}
	
//	private void createTeam() throws SQLException {
//		
//		// Get user Input
//		System.out.print("What team would you like to create? ");
//		String teamName = scanner.nextLine();
//		System.out.print("What season will this team describe? ");
//		int season = Integer.parseInt(scanner.nextLine());
//		System.out.print("Projected Salary cap? ");
//		int salaryCap = Integer.parseInt(scanner.nextLine());
//		System.out.print("How much dead cap in this year? ");
//		int deadspace = Integer.parseInt(scanner.nextLine());
//		int capSpace = salaryCap - deadspace;
//
//		// Send Input to DB
//		teamDao.createTeam(teamName, salaryCap, capSpace, deadspace, season);
//		
//	}
	
//	private void deleteTeam() throws SQLException {
//		
//		// Get ID of team to delete
//		System.out.println("Enter the ID of the team you wish to delete: ");
//		int team_id = Integer.parseInt(scanner.nextLine());
//		System.out.println("Warning! This will delete all players and salaries on this roster.");
//		System.out.print("Continue deleting team? (y/n) ");
//		String answer = scanner.nextLine();
//		
//		// delete team (and associated play and salaries) if input is valid
//		if (answer.equals("y") || answer.equals("yes")) {
//			teamDao.deleteTeamById(team_id);
//		} else if (answer.equals("n") || answer.equals("no")) {
//			System.out.println("Cancelled - no changes made.");
//		} else {
//			System.out.println("Invalid input.");
//		}
//	}
	
//	private void displayTeamRoster() throws SQLException {
//		
//		// Get team by id
//		System.out.print("What's the team id? ");
//		int teamId = Integer.parseInt(scanner.nextLine());
//		Team team = teamDao.getTeamById(teamId);
//		
//		// Display each player on team and roster size
//		for (Player player : team.getRoster()) {
//			System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - "
//					+ player.getPosition() + player.getDepth() + " (ID: " + player.getId() + ")");
//		}
//		System.out.println("Roster size: " + team.getRoster().size());
//	}
	
//	private void showCapSpace() throws SQLException {
//		
//		// Get team by id
//		System.out.print("Enter teamId: ");
//		int teamId = Integer.parseInt(scanner.nextLine());
//		Team team = teamDao.getTeamById(teamId);
//		
//		// Display Team Finances
//		System.out.println(team.getTeam_name() + " cap space: " + team.getCap_space() 
//			+ " remaining, and " + team.getDeadspace() + " in " + team.getSeason() + " deadspace");
//	}
	
	/*
	 * Player related functions
	 */
	
//	private void signPlayer() throws SQLException {
//		
//		// Get Player information from user
//		System.out.print("First name? ");
//		String first = scanner.nextLine();
//		System.out.print("Last name? ");
//		String last = scanner.nextLine();
//		System.out.print("Position? ");
//		String position = scanner.nextLine();
//		System.out.print("Where is the player on the depth chart? (Enter 1 if starting): ");
//		int depth = Integer.parseInt(scanner.nextLine());
//		System.out.println("Please Select a Team By Id");
//		
//		// Display All Teams to Assist User
//		for (Team team : teamDao.getTeams()) {
//			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
//		}
//		System.out.print("Team Id? ");
//		int team_id = Integer.parseInt(scanner.nextLine());
//		
//		// before signing the player, ensure enough cap space
//		
//		// Create a new player
//		playerDao.signPlayer(first, last, position, depth, team_id);
//		
//		// Create a new player salary
//		signContract(playerDao.getPlayer(first, last));
//	}
	
//	private void signContract(Player player) throws SQLException {
//		
//		// Switch for calculating cap impact
//		String option = "Sign";
//		
//		// Get User Input for each contract year
//		System.out.print("How many years is the contract for? ");
//		int contract_length = Integer.parseInt(scanner.nextLine());
//		
//		for (int i = 0; i < contract_length; i++) {
//			System.out.print("Salary of year " + (i + 1) + " (ex: 10000000): ");
//			int salary = Integer.parseInt(scanner.nextLine());
//			System.out.print("What calendar year does this apply to? (YYYY): ");
//			int year = Integer.parseInt(scanner.nextLine());
//			System.out.print("How much dead cap is there for cutting the player in year " + year + "? ");
//			int deadspace = Integer.parseInt(scanner.nextLine());
//			
//			// Create new player salary
//			playerDao.signContract(player.getId(), salary, year, deadspace);
//			
//			// Update Available Cap Space
//			if (year == CURRENT_YEAR) {
//				Team updated_team = calculateCapImpact(teamDao.getTeamById(player.getTeamId()), playerDao.getContractDetails(player).get(0), option);
//				teamDao.updateCapSpace(updated_team);
//			}
//		}
//	}
	
//	private void cutPlayer() throws SQLException {
//		
//		// switch for calculating cap impact
//		String option = "Cut";
//		
//		// Get Player to be cut and playersalary
//		System.out.print("Who do you want to cut? (full name) ");
//		String first = scanner.next();
//		String last = scanner.next();
//		
//		// try catch block here?
//		Player player = playerDao.getPlayer(first, last);
//		PlayerSalary salary = playerDao.getContractDetails(player).get(0);
//		Team team = teamDao.getTeamById(player.getId());
//		
//		// Make sure user can afford to cut the player
//		if (team.getCap_space() < salary.getDeadspace()) {
//			System.out.println("Not enough cap space to cut this player.");
//			System.out.println("Cap Space: " + team.getCap_space() + "\tDeadspace created: " + salary.getDeadspace());
//		} else {
//			// Ensure user wants to cut player
//			System.out.println("Cutting this player will result in dead cap of " + salary.getDeadspace() + " and savings of " + (salary.getSalary() - salary.getDeadspace()));
//			System.out.print("Continue? (y/n) ");
//			String answer = scanner.next();
//			if (answer.equals("y") || answer.equals("yes")) {
//				playerDao.deleteSalaryByPlayerId(player.getId());
//				playerDao.cutPlayerById(player.getId());
//				
//				// Update Team Financials
//				Team updated_team = calculateCapImpact(teamDao.getTeamById(player.getTeamId()), salary, option); 
//				teamDao.updateCapSpace(updated_team);
//				
//			} else if (answer.equals("n") || answer.equals("no")) {
//				System.out.println("Cancelled - no changes made.");
//			} else {
//				System.out.println("Invalid input.");
//			}
//		}
//	}
	
	
	
//	private void getPlayer() throws SQLException {
//		
//		// Displays information on a player
//		System.out.print("Which player do you want information on? (full name) ");
//
//		String first = scanner.next();
//		String last = scanner.next();
//		scanner.nextLine();
//		Player player = playerDao.getPlayer(first, last);
//		List<PlayerSalary> salaries = playerDao.getContractDetails(player);
//		PlayerSalary contract = salaries.get(0);
//		System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - " + 
//				player.getPosition() + player.getDepth() + " - Id: " + player.getId());
//		System.out.println("\t" + contract.getYear() + " Contract: " + contract.getSalary());
//		System.out.println("\t" + contract.getYear() + " Dead Cap: " + contract.getDeadspace());
//	}
	
	
//	private void updatePlayer() throws SQLException {
//		
//		// Update Player information or player salary information
//		System.out.println("Which player would you like to update? ");
//		System.out.print("First name: ");
//		String first = scanner.nextLine();
//		System.out.print("Last name: ");
//		String last = scanner.nextLine();
//		
//		Player player = playerDao.getPlayer(first, last);
//		
//		System.out.print("Would you like to update? 1) info 2) salary?");
//		String answer = scanner.nextLine();
//		
//		
//		if (answer.equals("1") || answer.equals("info")) {
//			
//			// update player
//			System.out.println("Current name: " + player.getFirst_name() + " " + player.getLast_name());
//			System.out.print("Enter new first name: ");
//			String new_first = scanner.nextLine();
//			System.out.print("Enter new last name: ");
//			String new_last = scanner.nextLine();
//			System.out.println("Current Position and Depth: " + player.getPosition() + player.getDepth());
//			System.out.print("Enter new position: ");
//			String new_position = scanner.nextLine();
//			System.out.println("Enter Depth at position: ");
//			int new_depth = Integer.parseInt(scanner.nextLine());
//			
//			player.setFirst_name(new_first);
//			player.setLast_name(new_last);
//			player.setPosition(new_position);
//			player.setDepth(new_depth);
//			
//			playerDao.updatePlayer(player);
//			
//			System.out.println("Update complete!");
//			
//		} else if (answer.equals("2") || answer.equals("salary")) {
//		
//			// update salary
//			PlayerSalary salary = playerDao.getContractDetails(player).get(0);
//			System.out.println("Current contract for " + player.getFirst_name() + " " + player.getLast_name());
//			System.out.println(salary.getYear() + " Salary: " + salary.getSalary() + " Dead cap: " + salary.getDeadspace());
//			System.out.print("Enter new salary: ");
//			int new_salary = Integer.parseInt(scanner.nextLine());
//			System.out.print("Enter updated dead cap: ");
//			int new_deadspace = Integer.parseInt(scanner.nextLine());
//			
//			salary.setSalary(new_salary);
//			salary.setDeadspace(new_deadspace);
//			
//			playerDao.updatePlayerSalary(salary);
//			
//		} else {
//			System.out.println("Invalid option.");
//		}
//	}
	
//	private void showPositionGroup() throws SQLException {
//		
//		// Help user find team ID
//		System.out.println("Please Select a Team By Id");
//		List<Team> teams = teamDao.getTeams();
//		for (Team team : teams) {
//			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
//		}
//		System.out.print("Team Id? ");
//		int teamId = Integer.parseInt(scanner.nextLine());
//		
//		// Get position group
//		System.out.print("Which position would you like to view? (ex: LG, OLB) ");
//		String user_position = scanner.nextLine();
//		
//		// If valid, select the team and display the position
//		if (!isValidPosition(user_position, ALL_POSITIONS)) {
//			System.out.println("invalid input.");
//		} else {
//			// Select team from list of teams
//			Team selected_team = null;
//			for (Team team : teams) {
//				if (team.getId() == teamId) {
//					selected_team = team;
//					break;
//				}
//			}
//			// Display All Players at a position
//			for (Player player : selected_team.getRoster()) {
//				if (player.getPosition().equals(user_position)) {
//					System.out.println(player.getFirst_name() + " " + player.getLast_name() + " " + player.getPosition() + player.getDepth());
//				}
//			}
//		}	
//	}
	
//	private void getStarters() throws SQLException {
//		
//		// help user find team
//		System.out.println("Please Select a Team By Id");
//		List<Team> teams = teamDao.getTeams();
//		for (Team team : teams) {
//			System.out.println("\tTeam Name: " + team.getTeam_name() + " - Team ID: " + team.getId());
//		}
//		System.out.print("Team Id? ");
//		int teamId = Integer.parseInt(scanner.nextLine());
//		
//		System.out.println("View Offense or Defense? ");
//		String selection = scanner.nextLine();
//	
//		// Select team from list of teams
//		Team selected_team = null;
//		for (Team team : teams) {
//			if (team.getId() == teamId) {
//				selected_team = team;
//				break;
//			}
//		}
//		
//		if (selection.toLowerCase().equals("offense") || selection.toLowerCase().equals("defense")) {
//			displayStarters(selected_team, selection);
//		} else {
//			System.out.println("Invalid selection.");
//		}
//		
//	}
	
//	private void displayStarters(Team team, String selection) {
//		
//		// Display all players at a position, in order.
//		List<Player> roster = new ArrayList<Player>();
//		roster = team.getRoster();
//		
//		// Iterate through roster, displaying only offensive or defensive starters
//		if (selection.toLowerCase().equals("offense")) {
//			for (String position : OFFENSIVE_POSITIONS) {
//				for (int i = 0; i < roster.size(); i++) {
//					if (roster.get(i).getPosition().equals(position) && roster.get(i).getDepth() == 1) {
//						System.out.println(roster.get(i).getFirst_name() + " " + roster.get(i).getLast_name() + " " + roster.get(i).getPosition() + roster.get(i).getDepth());
//						roster.remove(i);
//						i--;
//					} else if (roster.get(i).getDepth() != 1) {
//						roster.remove(i);
//						i--;
//					}
//				}
//			}
//		} else {
//			for (String position : DEFENSIVE_POSITIONS) {
//				for (int i = 0; i < roster.size(); i++) {
//					if (roster.get(i).getPosition().equals(position) && roster.get(i).getDepth() == 1) {
//						System.out.println(roster.get(i).getFirst_name() + " " + roster.get(i).getLast_name() + " " + roster.get(i).getPosition() + roster.get(i).getDepth());
//						roster.remove(i);
//						i--;
//					} else if (roster.get(i).getDepth() != 1) {
//						roster.remove(i);
//						i--;
//					}
//				}
//			}
//		}
//		
//	}
//	
//	private boolean isValidPosition(String user_position, List<String> positions) {
//		
//		// Checks to confirm position group is valid
//		boolean check = false;
//		for (String position : positions) {
//			if (position.equals(user_position)) {
//				check = true;
//				break;
//			}
//		}
//		return check;
//	}
}