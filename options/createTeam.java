package options;

import java.sql.SQLException;

public class CreateTeam implements MenuOptions {

	@Override
	public void execute() throws SQLException {
		
		// Get user Input
		System.out.print("What team would you like to create? ");
		String teamName = scanner.nextLine();
		System.out.print("What season will this team describe? ");
		int season = Integer.parseInt(scanner.nextLine());
		System.out.print("Projected Salary cap? ");
		int salaryCap = Integer.parseInt(scanner.nextLine());
		System.out.print("How much dead cap in this year? ");
		int deadspace = Integer.parseInt(scanner.nextLine());
		int capSpace = salaryCap - deadspace;

		// Send Input to DB
		teamDao.createTeam(teamName, salaryCap, capSpace, deadspace, season);
	}
}
