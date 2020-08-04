package facade;


import java.util.Collection;
import java.util.Set;
import dbdao.PurchasingManDBDAO;
import dbdao.WorkersDBDAO;
import dbdao.ProductDBDAO;
import enums.ClientType;
import exceptions.CantFindIdWorkerException;
import exceptions.CantFindPurchasingManException;
import javaBeans.Workers;
import javaBeans.Product;
import javaBeans.PurchasingMan;

public class AdminFacade implements ProductClientFacade {
	
	private ProductDBDAO productD= new ProductDBDAO();
	private PurchasingManDBDAO purchaM = new  PurchasingManDBDAO();
   private WorkersDBDAO workersD = new  WorkersDBDAO();
	
	private final String NAME = "admin";
	private final String PASSWORD = "1234";
	
	public AdminFacade() {
	}

	
	
	
	public void createPurchasingMan( PurchasingMan purchasingMan) {
		
		
			purchaM.creatPurchasingMan(purchasingMan);
	
			System.out.println("The purchasingMan name exists in our data ");
		
		};
		
	
		
		
		
	public void removePurchasingMan(long id) throws CantFindPurchasingManException {
			
			PurchasingMan check = purchaM.getSpecificPurchasingMan(id);

			if (check == null) {
				throw new CantFindPurchasingManException("Could not find purchasingman for remove");
			}
			
			purchaM.removePurchasingMan(id);
		}
	
	public void updatePurchasingMan(PurchasingMan purchasingManToUpdate) throws CantFindPurchasingManException{
		
		PurchasingMan check = purchaM.getSpecificPurchasingMan(purchasingManToUpdate.getId());
		
		if (check == null) {
			throw new CantFindPurchasingManException("Could not find purchasingMan to update");
		}
		
		purchasingManToUpdate.setPurch_name(check.getPurch_name());
		purchasingManToUpdate.setPassword(check.getPassword());
		purchasingManToUpdate.setEmail(check.getEmail());
		purchaM.updatePurchasingMan(purchasingManToUpdate);
	}
		public PurchasingMan getPurchasingMan(long id) throws CantFindPurchasingManException {
			PurchasingMan purchasingMan = purchaM.getSpecificPurchasingMan(id);
			if (purchasingMan == null) {
				throw new CantFindPurchasingManException("Cant Find PurchasingMan");
			}
			return purchasingMan;
		}
		
		public Collection<PurchasingMan> getAllPurchasingMen() throws CantFindPurchasingManException{
			
			Collection<PurchasingMan> purchasingMens = purchaM.getAllPurchasingMen();
			
			if(purchasingMens.isEmpty()) {
				throw new CantFindPurchasingManException("The purchasingMan list it empty");
			}
			
			return purchasingMens;
			}
		
		public void createWorker(Workers workers) throws CantFindIdWorkerException {
			Collection<Workers> check = workersD.getWorkerrByName(workers.getWorker_name());
					
			if (check.isEmpty()) {
				workersD.createWorker(workers);
			} else {
				throw new CantFindIdWorkerException("The Worker name exists in our data");
			}

		}
		
		public void removeWorker(long workerId) throws CantFindIdWorkerException {

			Workers removeWorkers = workersD.getWorker(workerId);
		
			if (removeWorkers != null) {
				Set<Product> products = (Set<Product>) workersD.getProductsByWorker();
					for (Product product : products) {
						productD.removeFromWorkerProduct(workerId, product.getId());
					Product upDateRemovedProduct = new Product(product.getId(), product.getTitle(), product.getAmount() + 1,
							product.getType(),product.getMessage(),product.getPrice(),product.getStart_date(),product.getEnd_date());
					productD.updateProduct(upDateRemovedProduct);
				}
					workersD.removeWorker(workerId);;
			} else {
				throw new CantFindIdWorkerException("can't find Worker");
			}

		}
		
		public void updateWorker(Workers workerToUpdate) throws CantFindIdWorkerException {
			Workers check = workersD.getWorker(workerToUpdate.getId());
			if (check == null) {
				throw new CantFindIdWorkerException("can't find worker");
			}
			
			workersD.updateWorker(workerToUpdate);
		}
		
		
		public Workers getworker(long id) throws CantFindIdWorkerException {
			Workers worker = workersD.getWorker(id);
			if (worker == null) {
				throw new CantFindIdWorkerException("can't find Worker by Id");
			}
			return worker;
		}

		public Collection<Workers> getAllWorker() throws CantFindIdWorkerException {
			Collection<Workers> workers = workersD.getAllWorkers();
			if (workers.isEmpty()) {
				throw new CantFindIdWorkerException("can't find Worker in the Worker collection");
			}
			return workers;
		}
		
	
	@Override
	public AdminFacade login(String name, String password, ClientType type) {
		if (!type.equals(ClientType.ADMIN)) {
			System.out.println("You are trying to login with the wrong type");
			return null;
		}


		if (name.equals(NAME) && password.equals(PASSWORD)) {
			return this;
		} else {
			System.out.println("One of the parameters is not correct");
			return null;
		}
	}


}
