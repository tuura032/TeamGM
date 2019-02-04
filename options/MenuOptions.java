package options;

import java.sql.SQLException;
import java.util.Scanner;

import dao.TeamDao;

public interface MenuOptions {

	TeamDao teamDao = new TeamDao();
	Scanner scanner = new Scanner(System.in);
	
	public void execute() throws SQLException;
}
