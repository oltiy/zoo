package dao;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import javaBeans.Product;
import javaBeans.PurchasingMan;

public interface PurchasingManDAO {

public void creatPurchasingMan (PurchasingMan PurchasingMan);
	
public void removePurchasingMan(long id); 	

public void updatePurchasingMan(PurchasingMan PurchasingMan);

public PurchasingMan getSpecificPurchasingMan(long id) throws SQLException;

public Collection<PurchasingMan> getAllPurchasingMen();
public Collection<Product> getAllPurchasedProductByPrice(long purchasingId, double productPrice);
public Collection<PurchasingMan> getPurchasingManByProduct(long productId);
public Collection<Product> getProductByPurchasingMen(long purchasingManId);
//public Product getPurchasProductByPurchasingMan(PurchasingMan purchasingMan, long productId);
public Collection<Product> getPurchasingManProductByDate(long purchasingMan, Date date);
public  PurchasingMan getPurchasingManByName(String PurchasingManName);


/**
 * returns an ArrayList all products that this PurchasingMen purchased
 * 
 * @return an  ArrayList all products that this PurchasingMen purchased
 */
//public Collection<Product> getProductsbyPurchaser();
/**
 * compare by the database if the name and the password are correct
 * 
 * @param purch_name
 *            the Purchasing Man name
 * @param password
 *            the PurchasingMans password
 * @return true value if there is a match, false if there is no match
 */
public boolean login(String purch_name, String password);
}
