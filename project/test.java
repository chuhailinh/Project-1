package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
	public static void main(String[] args) throws SQLException{
		ConnectDB.KetNoiDL();
		String dateend = "2018-12-03";
		String datestart = "2018-12-01";
		String sqlString = "select * from bill where daybuysp < " + "\"" + dateend + "\" and daybuysp > " + "\"" + datestart + "\"";
		String sqlString2 = "select * from bill where daybuysp = " + "\"" + dateend + "\"" ;
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sqlString2);
		ResultSet rs = ppst.executeQuery();
		while(rs.next()){
			String nameString = rs.getString("daybuysp");
			System.out.println(nameString);
		}
	}
}
