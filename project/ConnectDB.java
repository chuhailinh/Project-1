package project;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ConnectDB {
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/project1";
	private static String USER_NAME = "root";
	private static String PASSWORD = "1112";

	public static Connection connection;

	public static void KetNoiDL(){
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Loi CSDL");
		}
	}
	
	public static void DongCSDL(){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Loi CSDL 2");
		}
	}
	
	public static void changeData(String nameSP, String HSD) throws SQLException{
		ConnectDB.KetNoiDL();
		String sql = "delete from sp where name = " + "\"" + nameSP + "\"" + " and HSD = " + "\"" + HSD + "\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		ppst.executeUpdate();
	}
	
	public static void changeData(String nameSP, int SL, String HSD) throws SQLException{
		ConnectDB.KetNoiDL();
		String sql = "update sp set amount = " + SL + " where name = " + "\"" + nameSP + "\"" + " and HSD = " + "\"" + HSD +"\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		ppst.executeUpdate();
	}
	
	public static void delUser(String username) throws SQLException{
		ConnectDB.KetNoiDL();
		String sql = "delete from account where name = " + "\"" + username + "\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		ppst.executeUpdate();
	}
	
	//đang sửa
	public static void addSP(String name, int price, int sl, String date) throws SQLException{
		ConnectDB.KetNoiDL();
//		String sql = "select name,amount from sp";
		PreparedStatement ppst = null;
//		ppst = ConnectDB.connection.prepareStatement(sql);
//		ResultSet rs = ppst.executeQuery();
//		while(rs.next()){
//			String namecheck = rs.getString("name");
//			int soluong = rs.getInt("amount");
//			if(namecheck.equals(name)){
//				soluong = soluong + sl;
//				changeData(name, soluong);
//				return;
//			}
//		}
		String sql1 = "insert into sp values (" + "\"" + name + "\"" + "," + price + "," + sl + "," + "\"" + date + "\"" + ")";
		ppst = ConnectDB.connection.prepareStatement(sql1);
		ppst.executeUpdate();
	}	
	
	public static int getSL(String nameSP, String HSD) throws SQLException{
		ConnectDB.KetNoiDL();
		String sql = "select amount from sp where name = " + "\"" + nameSP + "\"" + " and HSD = " + "\"" + HSD + "\"";
		PreparedStatement ppst = null;
		ppst = ConnectDB.connection.prepareStatement(sql);
		ResultSet rs = ppst.executeQuery();
		int sl = 0;
		while(rs.next()){
			sl = rs.getInt("amount");
		}
		return sl;
	}
	
	//đang sửa
	public static ArrayList<SP> getSP() throws SQLException{
		ArrayList<SP> arrSP = new ArrayList<>();
		ConnectDB.KetNoiDL();
		PreparedStatement ppst = null;
		ResultSet rs = null;
		String sql = "select * from sp where now() < sp.HSD";
		ppst = ConnectDB.connection.prepareStatement(sql);
		rs = ppst.executeQuery();
		while(rs.next()){
			String name = rs.getString("name");
			int price = rs.getInt("price");
			int SL = rs.getInt("amount");
			Date HSD = rs.getDate("HSD");
			SP sp = new SP(name, price, SL, HSD);
			arrSP.add(sp);
		}
		return arrSP;
	}
	
	public static ArrayList<SP> getSPAdmin() throws SQLException{
		ArrayList<SP> arrSP = new ArrayList<>();
		ConnectDB.KetNoiDL();
		PreparedStatement ppst = null;
		ResultSet rs = null;
		String sql = "select * from sp";
		ppst = ConnectDB.connection.prepareStatement(sql);
		rs = ppst.executeQuery();
		while(rs.next()){
			String name = rs.getString("name");
			int price = rs.getInt("price");
			int SL = rs.getInt("amount");
			Date HSD = rs.getDate("HSD");
			SP sp = new SP(name, price, SL, HSD);
			arrSP.add(sp);
		}
		return arrSP;
	}
	
	public static ArrayList<User> getUser() throws SQLException{
		ArrayList<User> arrUser = new ArrayList<>();
		ConnectDB.KetNoiDL();
		String sql = "select * from account where role = " + "\"" + "client" + "\"";
		PreparedStatement ppst = null;
		ppst = ConnectDB.connection.prepareStatement(sql);
		ResultSet rs = ppst.executeQuery();
		while(rs.next()){
			String name = rs.getString("name");
			String password = rs.getString("password");
			String phone = rs.getString("phone");
			String address = rs.getString("address");
			User user = new User(name, password, phone, address);
			arrUser.add(user);
		}
		return arrUser;
	}
	
	public static void delSP(String nameSP) throws SQLException{
		ConnectDB.KetNoiDL();
		String sql = "delete from sp where name = " + "\"" + nameSP + "\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		int i = ppst.executeUpdate();
	}
	
	public static int addAccount(User user) throws IOException, SQLException{
		int check = 0;
		String name = user.getName();
		String password = user.getPassword();
		String phone = user.getPhone();
		String address = user.getAddress();
		String role = "client";
		ConnectDB.KetNoiDL();
		String sql1 = "select name from account";
		PreparedStatement ppst1 = null;
		ppst1 = ConnectDB.connection.prepareStatement(sql1);
		ResultSet rs = ppst1.executeQuery();
		while(rs.next()){
			String namecheck = rs.getString("name");
			if(namecheck.equals(name)){
				return check;
			}
		}
		String sql = "insert into account values (" + "\""+ name + "\"" + "," +
		            "\"" + password + "\"" + "," + "\"" + phone + "\"" + "," + "\"" + address + "\"" + "," + "\"" + role + "\"" + ")" ;
	    PreparedStatement ppst = null;
		ppst = ConnectDB.connection.prepareStatement(sql);
		check = ppst.executeUpdate();	
		return check;
	}
	
	public static String checkAccount(String name, String password) throws IOException{
		ConnectDB.KetNoiDL();
		PreparedStatement ppst = null;
		String sql = "select * from account";
		try {
			ppst = ConnectDB.connection.prepareStatement(sql);
			ResultSet rs = ppst.executeQuery();
			while(rs.next()){
				String username = rs.getString("name");
				String passwd = rs.getString("password");
				String role = rs.getString("role");
				if(username.equals(name) && passwd.equals(password)){
					if(role.equals("client")){
						return "client";
					}
					if(role.equals("admin")){
						return "admin";
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failed";
	}
	
	public static void insertBillTable(String nameOfUser, String nameSP, String HSD, int SL, String dayBuySP) throws SQLException{
		ConnectDB.KetNoiDL();
		PreparedStatement ppst = null;
		String sql1 = "select * from bill";
		ppst = ConnectDB.connection.prepareStatement(sql1);
		ResultSet rs = ppst.executeQuery();
		int check = 0;
		int soluong = 0;
		while(rs.next()){
			String nou = rs.getString("nameofuser");
			String namesp = rs.getString("namesp");
			String hsd = rs.getString("HSD");
			String daybuysp = rs.getString("daybuysp");
			int sl = rs.getInt("SL");
			if(nameOfUser.equals(nou) && nameSP.equals(namesp) && hsd.equals(HSD) && daybuysp.equals(dayBuySP)){
				check = 1;
				soluong = sl + SL;
				break;
			}
		}
		if(check == 0){
			String sql = "insert into bill values (" + "\"" + nameOfUser + "\", " + "\"" + nameSP + "\", " + "\"" + HSD + "\", "
				      + SL + ", \"" + dayBuySP + "\"" + ")";
			ppst = ConnectDB.connection.prepareStatement(sql);
			ppst.executeUpdate();
		}
		if(check == 1){
			String sqlString = "update bill set SL = " + soluong + " where namesp = " + "\"" + nameSP + "\" and nameofuser = "
					 + "\"" + nameOfUser + "\" and HSD = " + "\"" + HSD + "\" and daybuysp = " + "\"" +dayBuySP + "\""; 
			ppst = ConnectDB.connection.prepareStatement(sqlString);
			ppst.executeUpdate();
		}
	}
	
	public static ArrayList<Bill> getStatistical(String date) throws SQLException, ParseException{
		String datestart = date + "-01";
		String dateend = date + "-31";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(datestart);
		Date date2 = sdf.parse(dateend);
		datestart = sdf.format(date1);
		dateend = sdf.format(date2);
		ArrayList<Bill> arrBill = new ArrayList<>();
		ConnectDB.KetNoiDL();
		String sql = "select * from bill where daybuysp < " + "\"" + dateend + "\" and daybuysp > " + "\"" + datestart + "\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		ResultSet rs = ppst.executeQuery();
		while(rs.next()){
			String nameofuser = rs.getString("nameofuser");
			String namesp = rs.getString("namesp");
			String HSD = rs.getString("HSD");
			Date hsd = sdf.parse(HSD);
			int SL = rs.getInt("SL");
			String date3 = rs.getString("daybuysp");
			int price = getPrice(namesp, HSD);
			Bill bill = new Bill(namesp, price, SL, hsd, nameofuser);
			arrBill.add(bill);
		}
		return arrBill;
	}
	
	public static int getPrice(String nameSP, String HSD) throws SQLException{
		int price = 0;
		ConnectDB.KetNoiDL();
		String sql = "select price from sp where name = " + "\"" + nameSP + "\" and HSD = " + "\"" + HSD + "\"";
		PreparedStatement ppst = ConnectDB.connection.prepareStatement(sql);
		ResultSet rs = ppst.executeQuery();
		while(rs.next()){
			price = rs.getInt("price");
		}
		return price;
	}
}

