package options;

import java.sql.SQLException;
import java.util.Scanner;

import dao.PlayerDao;
import dao.TeamDao;
import options.Helpers;

public interface MenuOptions {

	TeamDao teamDao = new TeamDao();
	Scanner scanner = new Scanner(System.in);
	PlayerDao playerDao = new PlayerDao();
	Helpers helper = new Helpers();
	
	public void execute() throws SQLException;
}
