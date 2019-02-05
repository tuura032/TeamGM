package application;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.PlayerDao;
import options.*;

public class Menu {

	// Initialize objects, static variables, and lists
	private static Scanner scanner = new Scanner(System.in);
	private PlayerDao playerDao = new PlayerDao();
	private MenuOptions menuOptions;
	public final static int CURRENT_YEAR = 2019;
	private final List<String> OPTIONS = Arrays.asList(
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
			

	// Start Program
	public void start() {
		
		// Repeatedly present user with a menu of options
		String selection = "";
		do {
			printMenu();
			selection = scanner.nextLine();
			
			try {
				if (selection.equals("1")) {
					menuOptions = new DisplayTeams();
				} else if (selection.equals("2")) {
					menuOptions = new DisplayTeamRoster();
				} else if (selection.equals("3")) {
					menuOptions = new CreateTeam();
				} else if (selection.equals("4")) {
					menuOptions = new DeleteTeam();
				} else if (selection.equals("5")) {
					menuOptions = new ShowCapSpace();
				} else if (selection.equals("6")) {
					menuOptions = new ShowPositionGroup();
				} else if (selection.equals("7")) {
					menuOptions = new GetStarters();
				} else if (selection.equals("8")) {
					menuOptions = new SignPlayer();
				} else if (selection.equals("9")) {
					menuOptions = new GetPlayer();
				} else if (selection.equals("10")) {
					menuOptions = new UpdatePlayer();
				} else if (selection.equals("11")) {
					menuOptions = new CutPlayer();
				}
				
				menuOptions.execute();
				
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
		for (int i = 0, size = OPTIONS.size(); i < size; i++) {
			System.out.println(OPTIONS.get(i));
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
}