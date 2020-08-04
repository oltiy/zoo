package dao;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import enums.ProductType;
import exceptions.CantFindProductException;
import javaBeans.Product;
import javaBeans.PurchasingMan;

public interface ProductDAO {
	
public void createProduct(Product product);


public void removeProduct(long id);

public void updateProduct(Product product);

public Product getProduct(long id) throws CantFindProductException;
public Collection<Product> getProductsByType(ProductType productType)  ; 

public Collection<Product> getAllProducts();

public Collection<Product> getAllProductByEndDate(LocalDate end_date);


}



