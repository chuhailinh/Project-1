package project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SP {
	private String nameSP;
	private int priceSP;
	private int SL;
	private Date HSD;
	public String getNameSP() {
		return nameSP;
	}
	public void setNameSP(String nameSP) {
		this.nameSP = nameSP;
	}
	public int getPriceSP() {
		return priceSP;
	}
	public void setPriceSP(int priceSP) {
		this.priceSP = priceSP;
	}
	public int getSL() {
		return SL;
	}
	public String getHSD(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(HSD);
		return date;
	}
	public void setSL(int sL) {
		SL = sL;
	}
	public SP(String nameSP, int priceSP, int SL, Date HSD){
		this.nameSP = nameSP;
		this.priceSP = priceSP;
		this.SL = SL;
		this.HSD = HSD;
	}
}
