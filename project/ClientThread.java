package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectableChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.border.TitledBorder;

public class ClientThread extends Thread{
	private Socket socketOfServer;
	private DataInputStream is;
	private DataOutputStream os;
	private boolean active;
	
	public ClientThread(Socket socketOfServer){
		this.socketOfServer = socketOfServer;
	}
	
	public void run(){
		String title = null;
		String reply = null;
		try {
			is = new DataInputStream(socketOfServer.getInputStream());
			os = new DataOutputStream(socketOfServer.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(!active){	
			try {
				title = is.readUTF();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(title.equals("login")){
				try {
					String name = is.readUTF();
					String password = is.readUTF();
					reply = ConnectDB.checkAccount(name, password);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(!reply.equals("failed")){
					try {
						active = true;
						sendMSG(reply);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						sendMSG(reply);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(title.equals("createaccount")){
				try {
					String name = is.readUTF();
					String password = is.readUTF();
					String phone = is.readUTF();
					String address = is.readUTF();
					User user = new User(name, password, phone, address);
					int success = ConnectDB.addAccount(user);
					if(success != 0){
						sendMSG("signupsuccess");
					}
					if(success == 0) {
						sendMSG("isexisted");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(reply.equals("client")){
			while(active){
				try {
					title = getMSG();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ArrayList<SP> arrSP = new ArrayList<>();
				if(title.equals("showsp")){
					try {
						arrSP = ConnectDB.getSP();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					for (SP sp : arrSP) {
						try {
							sendMSG("sp");
							sendSP(sp);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						sendMSG("done");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("charged")){
					String title1 = null;
					String nameOfUser = null;
					try {
						nameOfUser = getMSG();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Date date1 = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String DATE = sdf.format(date1);
					while(true){
						try {
							title1 = getMSG();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(title1.equals("done")){
							try {
								sendMSG("done");
								break;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						else {
							try {
								String nameSP = getMSG();
								String sl = getMSG();
								String hsd = getMSG();
								sdf = new SimpleDateFormat("dd/MM/yyyy");
								Date date = sdf.parse(hsd);
								sdf = new SimpleDateFormat("yyyy-MM-dd");
								String HSD = sdf.format(date);
								int soluong = Integer.valueOf(sl);
								ConnectDB.insertBillTable(nameOfUser, nameSP, HSD, soluong, DATE);
								int SL = ConnectDB.getSL(nameSP, HSD);
								if(SL == soluong){
									ConnectDB.changeData(nameSP, HSD);
								}else {
									SL = SL - soluong;
									ConnectDB.changeData(nameSP, SL, HSD);
								}
							} catch (IOException | SQLException | ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
				if(title.equals("close")){
					active = false;
					try {
						close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(reply.equals("admin")){
			while(active){
				try {
					title = getMSG();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ArrayList<User> arrUser = new ArrayList<>();
				ArrayList<SP> arrSP = new ArrayList<>();
				if(title.equals("showspAdmin")){
					try {
						arrSP = ConnectDB.getSPAdmin();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					for (SP sp : arrSP) {
						try {
							sendMSG("sp");
							sendSP(sp);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						sendMSG("done");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("showuser")){
					try {
						arrUser = ConnectDB.getUser();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					for (User user : arrUser) {
						try {
							sendMSG("user");
							sendUser(user);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						sendMSG("done");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("deluser")){
					try {
						String username = getMSG();
						ConnectDB.delUser(username);
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("addsp")){
					try {
						String nameSP = getMSG();
						String priceSP = getMSG();
						String SL = getMSG();
						String HSD = getMSG();
						int price = Integer.valueOf(priceSP);
						int sl = Integer.valueOf(SL);
						ConnectDB.addSP(nameSP, price, sl, HSD);
						sendMSG("done");
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					};
				}
				if(title.equals("delsp")){
					String nameSP;
					try {
						nameSP = getMSG();
						ConnectDB.delSP(nameSP);
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("close")){
					active = false;
					try {
						close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(title.equals("statistical")){
					String date = null;
					try {
						String month = getMSG();
						String year = getMSG();
						date = year + "-" + month;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						ArrayList<Bill> arrBill = ConnectDB.getStatistical(date);
						Bill bill = null;
						for(int i = 0; i < arrBill.size(); i++){
							bill = arrBill.get(i);
							String nameofuser = bill.getNameOfUser();
							String namesp = bill.getNameSP();
							int SL = bill.getSL();
							int price = bill.getPriceSP();
							String HSD = bill.getHSD();
							sendMSG("continue");
							sendMSG(nameofuser);
							sendMSG(namesp);
							sendMSG(HSD);
							sendMSG(Integer.toString(SL));
							sendMSG(Integer.toString(price));
						}
						sendMSG("done");
					} catch (SQLException | ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void close() throws IOException{
		is.close();
		os.close();
		socketOfServer.close();
	}
	
	public void sendSP(SP sp) throws IOException{
		sendMSG(sp.getNameSP());
		sendMSG(Integer.toString(sp.getPriceSP()));
		sendMSG(Integer.toString(sp.getSL()));
		sendMSG(sp.getHSD());
	}
	
	public void sendUser(User user) throws IOException{
		sendMSG(user.getName());
		sendMSG(user.getPhone());
		sendMSG(user.getAddress());
	}
	
	public void sendMSG(String data) throws IOException{
		os.writeUTF(data);
		os.flush();
	}
	
	public String getMSG() throws IOException{
		String name = is.readUTF();
		return name;
	}

}
