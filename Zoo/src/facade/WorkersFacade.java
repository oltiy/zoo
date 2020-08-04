package facade;

import java.util.Collection;
import java.util.Set;

import dbdao.ProductDBDAO;
import dbdao.WorkersDBDAO;
import enums.ClientType;
import enums.ProductType;

import exceptions.CantFindProductException;
import exceptions.LoginFiledException;
import javaBeans.Product;
import javaBeans.Workers;

public class WorkersFacade implements ProductClientFacade {

	private ProductDBDAO productD= new ProductDBDAO();
	private WorkersDBDAO workersD = new  WorkersDBDAO();
	
	private Workers worker;
	private long workerLogin;
	private ProductType type;

	
	public WorkersFacade() {
		super();
		// TODO Auto-generated constructor stub
	}


	public WorkersFacade(Workers worker) {
		super();
		this.worker = worker;
	}
	

	public void usedProduct(long workerLogin, long productId) throws CantFindProductException {
		if(workersD.isWorkerHoldThisProduct(workerLogin, productId)){
			System.out.println("worker allowed to hold only one cproductId with the same title");
		}
		else
		{
			Product product = productD.getProduct(productId);
		
		if(product.getAmount()>0) {
			productD.addWorkerProduct(workerLogin, productId);
		
		product = new Product(product.getId(),product.getTitle(),product.getAmount() -1, product.getType(),product.getMessage(), product.getPrice() , product.getStart_date(),product.getEnd_date());
		productD.updateProduct(product);
		
		System.out.println("product was purchased");
		} else {
			System.out.println(
					"the product validity expired or the quota limited and there are no more product to purchase");
		}
			
		}
	}
	
	
	public Collection<Product> getAllPurchasedProducts(long workerLogin) throws CantFindProductException {
		Set<Product> workerProduct = (Set<Product>) workersD.getAllUsedProductByWorker(workerLogin);
		if (workerProduct.isEmpty()) {
			throw new CantFindProductException();
		}
		return workerProduct;
	}
	
	public Collection<Product> getAllPurchasedProductsByPrice(long customerId, double productPrice)
			throws CantFindProductException {
		Set<Product> products = (Set<Product>) workersD.getAllUsedProductsByPrice(customerId, productPrice);
				
		if (products.isEmpty()) {
			throw new CantFindProductException("The customer doesn't hold products cheaper than " + productPrice);
		}
		return products;
	}


	@Override
	public ProductClientFacade login(String name, String password, ClientType type) {

		if (!type.equals(ClientType.WORKERS)) {
			throw new LoginFiledException("the login is failed, type need to bo worker");
		}

		boolean check = workersD.login(name, password);
		if (check) {
			Collection<Workers> worker = workersD.getWorkerrByName(name);
			
			return this;
		}
		
		return null;
	}
	
	public Workers getWorkerLogin() {
		return worker;
	}
	
	public void setWorkerLogin(Workers worker) {
		long workerLogin = worker.getId();
	}

	}


