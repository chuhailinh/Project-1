package project;

import java.util.Date;

public class Bill extends SP {
	private String nameOfUser;
	public Bill(String nameSP, int priceSP, int SL, Date HSD, String nameOfUser){
		super(nameSP, priceSP, SL, HSD);
		this.nameOfUser = nameOfUser;
	}
	public String getNameOfUser() {
		return nameOfUser;
	}
	public void setNameOfUser(String nameOfUser) {
		this.nameOfUser = nameOfUser;
	}
}
