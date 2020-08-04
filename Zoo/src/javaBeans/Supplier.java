package javaBeans;

public class Supplier {
	private long id;
	private String  supplier_name;
	private String email;
	private String title;
	private String type;
	private int phoneNumber ;
	private String address ;
	/**
	 * @param id
	 * 				the id of the supplier in the database, this id is a prime key in table
	 * @param supplier_name
	 * 				the name of the supplier 
	 * @param email
	 * 				the email of the supplier
	 * @param title
	 * 				short description of the product
	 * @param type
	 * 				 the type of product  
	 * @param phoneNumber
	 * 				the phone number of the supplier
	 * @param address
	 *				the address of the supplier
	 */
	public Supplier(long id, String supplier_name, String email, String title, String type, int phoneNumber,
			String address) {
		super();
		this.id = id;
		this.supplier_name = supplier_name;
		this.email = email;
		this.title = title;
		this.type = type;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplier_name=" + supplier_name + ", email=" + email + ", title=" + title
				+ ", type=" + type + ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}
	
}
