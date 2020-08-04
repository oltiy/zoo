package thread;

import dbdao.ProductDBDAO;
import dbdao.PurchasingManDBDAO;
import dbdao.WorkersDBDAO;
import javaBeans.Product;
import javaBeans.PurchasingMan;
import javaBeans.Workers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class DailyExpirationTask implements Runnable {

	private boolean running = false;
	private Thread thread;
	private ProductDBDAO productD= new ProductDBDAO();
	private PurchasingManDBDAO purchaM = new  PurchasingManDBDAO();
	private WorkersDBDAO workersD = new  WorkersDBDAO();
	
	
	
	@Override
	public void run() {
		while(running) {
// fix it check it if the reee is problem			
//			Date date = new Date();
			LocalDate date = LocalDate.now();
			Collection<Product> expiredProduct = productD.getAllProductByEndDate(date);
			for(Product p: expiredProduct) {
				Collection<Workers> workersrWhoHoldExpiredProduct = workersD.getWorkerByProducts(p.getId());
					for(Workers worker: workersrWhoHoldExpiredProduct) {
						productD.removeFromWorkerProduct(worker.getId(),p.getId() );
					}
				Collection<PurchasingMan> 	purchasingManWhoHoldExpiredProduct =  purchaM.getPurchasingManByProduct(p.getId());
				for(PurchasingMan prchasingMan : purchasingManWhoHoldExpiredProduct) {
					productD.removeFromPurchasingProduct(prchasingMan.getId(), p.getId());
				}
				
				productD.removeProduct(p.getId());
				
				}
			
			
			try {
				Thread.sleep(24 * 60 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	// Start method
	public synchronized void start() {

		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// Stop method
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
