package ecommerce.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Transactions
{
	@Id
	@GeneratedValue
	private String tid;
	private String ttype,address,tdate;
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private double tamt;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTtype() {
		return ttype;
	}
	public void setTtype(String ttype) {
		this.ttype = ttype;
	}
	public double getTamt() {
		return tamt;
	}
	public void setTamt(double tamt) {
		this.tamt = tamt;
	}

}
