package javaBeans;


/**
 * class that hold parameters of workers obj
 * @author user
 *
 */

public class Workers {
	private long id;
	private String  worker_name;
	private String password;
	private String email;
	

	/**
	 * @param id
	 *  		  the id of the worker in the database, this id is a prime key in table
	 * @param  worker_name
	 * 			 the name of the worker 
	 * @param password
	 * 			 the password for logging in
	 * @param email
	 * 			the email of the worker
	 * 
	 */

	public Workers() {

	}
	
	public Workers(long id,String  worker_name,String password,String email) {
		this.id = id;
		this.worker_name= worker_name;
		this.password = password;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Workers [id=" + id + ", worker_name=" + worker_name + ", password=" + password + ", email=" + email
				+ "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWorker_name() {
		return worker_name;
	}
	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
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

}

