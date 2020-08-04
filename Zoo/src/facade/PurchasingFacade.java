package facade;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



import dbdao.PurchasingManDBDAO;
import dbdao.WorkersDBDAO;
import dbdao.ProductDBDAO;
import enums.ClientType;
import exceptions.CantFindProductException;
import exceptions.CantFindPurchasingManProductsExceptions;
import javaBeans.Workers;
import javaBeans.Product;
import javaBeans.PurchasingMan;


public class PurchasingFacade implements ProductClientFacade{
	
	private ProductDBDAO productD= new ProductDBDAO();
	private PurchasingManDBDAO purchaM = new  PurchasingManDBDAO();
	private WorkersDBDAO workersD = new  WorkersDBDAO();
	
	private PurchasingMan purchasingMan;
	long loginIdPurchasingMan;
	

	public void createProduct(Product product) {
		Collection<Product> check =  productD.getProductsByTitle(product.getTitle());
		if (check == null) {
			productD.createProduct(product);
			productD.addPurchasingProduct(loginIdPurchasingMan, product.getId());
		} else {
			System.out.println("The product tilte name exists in our data");
		}

	}
	
	
	public void removeProductOptions(long productid) throws CantFindProductException {
		boolean check = productD.getProductByPurchasing(loginIdPurchasingMan, productid);
				
		if (check) {
			productD.removeFromPurchasingProduct(loginIdPurchasingMan, productid);
			System.out.println("the product deleted successfully");
		} else {
			throw new CantFindProductException("Can't find product to remove");
		}
	}
	
	
	public void updateProduct(Product product) throws CantFindProductException {
		Product originalProduct = productD.getProduct(product.getId());
		if (originalProduct == null) {
			throw new CantFindProductException("Can't find product to update");
		}
		originalProduct.setEnd_date(product.getEnd_date());
		originalProduct.setAmount(product.getAmount());
		originalProduct.setMessage(product.getMessage());
		originalProduct.setPrice(product.getPrice());
		originalProduct.setStart_date(product.getStart_date());
		originalProduct.setTitle(product.getTitle());
		originalProduct.setType(product.getType());
		productD.updateProduct(originalProduct);

	}
	

	public Collection<Product> getAllProduct(){
		return  productD.getAllProducts();
		
	}
	
	
//	public Collection<Product> getProduct(long purchasingManId) throws CantFindProductException {
//		Collection<Product> product = purchaM.getProductByPurchasingMen(purchasingManId);
//		if (product == null) {
//			throw new CantFindProductException(
//					"Cand find Product  for purchasingMan: " + purchasingMan.getPurch_name()); 
//		}
//		return product;

	

	public Collection<Product> getAllProductByPurchasingMan() throws CantFindPurchasingManProductsExceptions {
		Collection<Product> products = purchaM.getProductByPurchasingMen(loginIdPurchasingMan);
		if (products.isEmpty()) {
			throw new CantFindPurchasingManProductsExceptions("There is no Products for " + purchasingMan.getPurch_name());
		}
		return products;

	}
	
	public Collection<Product> getProductsByPrice(double price) throws CantFindPurchasingManProductsExceptions {
		Set<Product> products = (Set<Product>) purchaM.getAllPurchasedProductByPrice(loginIdPurchasingMan, price);
		if (products.isEmpty()) {
			throw new CantFindPurchasingManProductsExceptions("There is no Products lower the price: " + price);
		}
		return products;
	}
	
	public Collection<Product> getProductByDate(Date date) throws CantFindPurchasingManProductsExceptions {
		Set<Product> products = (Set<Product>) purchaM.getPurchasingManProductByDate(loginIdPurchasingMan, date);
		if (products.isEmpty()) {
			throw new CantFindPurchasingManProductsExceptions("There is no Products before date: " + date);
		}
		return products;
	}
	

	

	
	
	

	@Override
	public ProductClientFacade login(String name, String password, ClientType type) {
		if (!type.equals(ClientType.PURCHASING )) {
			System.out.println("type need to bee PURCHASING ");
			return null;
		}
		purchasingMan = (PurchasingMan) purchaM.getPurchasingManByName(name);
	

		if (purchasingMan.getPassword().equals(password)) {

//			Set<Product> PurchasingManProduct = (Set<Product>) purchaM.getProductByPurchasingMen(loginIdPurchasingMan.getId());
//			
//			loginIdPurchasingMan.
//			Product(PurchasingManProduct);
			return this;
		} else {
			System.out.println("One of the parameters is not correct");
			return null;
		}

}
}