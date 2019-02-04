package options;

import java.sql.SQLException;

import entity.Player;
import entity.PlayerSalary;

public class UpdatePlayer implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		
		// get Player information or player salary information
		System.out.println("Which player would you like to update? ");
		System.out.print("First name: ");
		String first = scanner.nextLine();
		System.out.print("Last name: ");
		String last = scanner.nextLine();
		
		Player player = playerDao.getPlayer(first, last);
		
		System.out.print("Would you like to update? 1) info 2) salary?");
		String answer = scanner.nextLine();
		
		
		// update player or salary
		if (answer.equals("1") || answer.equals("info")) {
			updatePlayer(player);
		} else if (answer.equals("2") || answer.equals("salary"))  {
			updateSalary(player);
		} else {
			System.out.println("Invalid option.");
		}
	}
	
	private void updatePlayer(Player player) throws SQLException {
		// update player
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
		
		System.out.println("Player updated!");
	}
	
	private void updateSalary(Player player) throws SQLException {
		
		// update salary
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
		
		System.out.println("Salary Updated!");
	}

}
