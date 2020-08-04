package testing;




import connections.ConnectionPool;

import connections.ZooSystem;

import dao.PurchasingManDAO;
import dbdao.ProductDBDAO;
import dbdao.PurchasingManDBDAO;
import dbdao.WorkersDBDAO;
import enums.ClientType;
import enums.ProductType;
import facade.AdminFacade;
import facade.ProductClientFacade;
import facade.PurchasingFacade;
import javaBeans.Product;
import javaBeans.PurchasingMan;
import javaBeans.Workers;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.sql.Date;
import java.sql.SQLException;

public class dbTest {

	public static void main(String[] args) throws SQLException {
//		TODO Auto-generated method stub
//		PurchasingFacade a = new PurchasingFacade();
//		System.out.println(a.getAllProduct());
		AdminFacade admin = new AdminFacade();
		admin.login("admin", "1234", ClientType.ADMIN);
		

//	Workers b = new Workers(1,"yoni","1234","y@y.com");
	PurchasingMan c = new PurchasingMan(1,"moshe","1234","m@m");
	
	
	admin.createPurchasingMan(c);
//		admin.getAllWorker();
//		PurchasingMan a = new PurchasingMan(1,"yaniv","1234","k@k.com");
//		admin.createPurchasingMan(a);
		
//ProductDBDAO c = new ProductDBDAO();
//c.createProduct(product);
//

//	ProductDBDAO c = new ProductDBDAO();
//	
//	
//	Product p = new Product(2,"aaaa",5,ProductType.NEW,"ok",50, Date.valueOf("2020-03-20"), Date.valueOf("2020-06-20"));
//		c.createProduct(p);
//		c.addPurchasingProduct(1,1);
		
//		Collection<Product> b = a.getProductByPurchasingMen(2);
//		Collection<Product> b = a.getAllPurchasedProductByPrice(1, 1.0);
		
//		System.out.println(b);
		
//		ZooSystem zSystem = ZooSystem.getInstance();
//	
//		// -------Test - AdminFacade methods----------
//
//		AdminFacade zAdmin = (AdminFacade) zSystem.login("admin", "1234",ClientType.ADMIN );
//		Workers a = new Workers(2,"ysooi","1s34","@y.com");
//	zAdmin.createWorker(a);
//		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	
//	Date date= Date.valueOf("2020-03-20");
//	Product product = new Product(6,"yamin",250,"a","a",25,"",date); 
//	Product product = new Product(20, "food2", 2, ProductType.ACCEPTED, "hi nadav", 1, Date.valueOf("2020-03-20"), Date.valueOf("2020-06-20"));	
//		Product product=a.getProductsByTitle("food");
//	System.out.println(product.getType());
	
//	
		

//	PurchasingManDBDAO a = new PurchasingManDBDAO();
//	b.creatPurchasingMan(purchasingMan2);
//	purchasingMan2.toString();
	
//	String b= a.getAllPurchasingMen().toString();
	//a.removePurchasingMan(2);
//	 a.creatPurchasingMan(purchasingMan2);
//	 purchasingMan2.toString();
//	System.out.println( purchasingMan2.toString());
//	a.login(purchasingMan2.getPurch_name(), purchasingMan2.getPassword());
		
//		ZooSystem zSystem = ZooSystem.getInstance();
		
		
		
	}
}


//}