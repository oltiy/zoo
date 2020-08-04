package javaBeans;

import java.time.LocalDate;
import java.util.Date;

import enums.ProductType;


public class Product {
	private long id;
	private String title;
	private int amount;
	private ProductType type;
	private String message;
	private double price;
	private LocalDate start_date;
	private LocalDate end_date;
	
	
	/**
	 * 
	 * the constructor of the Product
	 * 
	 * @param id
	 *            the id of the product in the database
	 * @param title
	 *            short description of the product
	 * @param amount
	 *            the number of available specific product   
	 * @param type
	 *            the type of product    
	 * @param message
	 *            a message about of the product
	 * @param price
	 *            the price of the product
     * @param image
	 *            an image of the product             
	
	 * @param endDate
	 *            the date that states the end of product validation
	 */
	
	public Product() {
		
	}
	
	public Product(long id, String title, int amount, ProductType type, String message, double price, LocalDate start_date,
			LocalDate end_date) {
		super();
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public Product( String title, int amount, ProductType type, String message, double price, LocalDate start_date,
			LocalDate end_date) {
		super();
		this.title = title;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.start_date = start_date;
		this.end_date = end_date;
	}





	public long getId() {
		return id;
	}





	public void setId(long id) {
		this.id = id;
	}





	public String getTitle() {
		return title;
	}





	public void setTitle(String title) {
		this.title = title;
	}





	public int getAmount() {
		return amount;
	}





	public void setAmount(int amount) {
		this.amount = amount;
	}





	public ProductType getType() {
		return type;
	}





	public void setType(ProductType type) {
		this.type = type;
	}





	public String getMessage() {
		return message;
	}





	public void setMessage(String message) {
		this.message = message;
	}





	public double getPrice() {
		return price;
	}





	public void setPrice(double price) {
		this.price = price;
	}




	public LocalDate getStart_date() {
		return start_date;
	}





	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}






	public LocalDate getEnd_date() {
		return end_date;
	}





	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}





	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", amount=" + amount + ", type=" + type + ", message="
				+ message + ", price=" + price + ", start_date=" + start_date + ", end_date=" + end_date + "]";
	}
}

