package ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminLogin
{


	@Id
	private String uid;
	private String pwd;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
