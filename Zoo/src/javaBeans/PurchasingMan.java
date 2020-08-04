package javaBeans;

import java.util.Collection;

/**
 * class that hold parameters of purchasing obj
 * @author user
 *
 */

public class PurchasingMan {
	private long id;
	private String  purch_name;
	private String password;
	private String email;
	




	/**
	 * @param id
	 *  		  the id of the worker in the database, this id is a prime key
	 *            in table
	 * @param  worker_name
	 * 			 the name of the worker 
	 * @param password
	 * 			 the password for logging in
	 * @param email
	 * 			the email of the worker
	 * 
	 */
	
	public PurchasingMan() {
	
	}
	
	public PurchasingMan(long id, String purch_name, String password, String email) {
		super();
		this.id = id;
		this.purch_name = purch_name;
		this.password = password;
		this.email = email;
	}
	
	public PurchasingMan( String purch_name, String password, String email) {
		super();
		this.purch_name = purch_name;
		this.password = password;
		this.email = email;
	}
	








	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPurch_name() {
		return purch_name;
	}



	public void setPurch_name(String purch_name) {
		this.purch_name = purch_name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	

	
	@Override
	public String toString() {
		return "Purchasing [id=" + id + ", purch_name=" + purch_name + ", password=" + password + ", email=" + email
				+ "]";
	}
	
}
