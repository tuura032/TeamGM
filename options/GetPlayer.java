package options;

import java.sql.SQLException;
import java.util.List;

import entity.Player;
import entity.PlayerSalary;

public class GetPlayer implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		
		// Displays information on a player
		System.out.print("Which player do you want information on? (full name) ");

		String first = scanner.next();
		String last = scanner.next();
		scanner.nextLine();
		Player player = playerDao.getPlayer(first, last);
		List<PlayerSalary> salaries = playerDao.getContractDetails(player);
		PlayerSalary contract = salaries.get(0);
		System.out.println(player.getFirst_name() + " " + player.getLast_name() + " - " + 
				player.getPosition() + player.getDepth() + " - Id: " + player.getId());
		System.out.println("\t" + contract.getYear() + " Contract: " + contract.getSalary());
		System.out.println("\t" + contract.getYear() + " Dead Cap: " + contract.getDeadspace());
	}

}
