package connections;

import enums.ClientType;
import exceptions.LoginFiledException;
import facade.AdminFacade;
import facade.ProductClientFacade;
import facade.PurchasingFacade;
import facade.WorkersFacade;
import thread.DailyExpirationTask;

public class ZooSystem {
	
	private DailyExpirationTask removeExpiredProduct = new DailyExpirationTask();
	private static ZooSystem instance = new ZooSystem();
	private Thread dilayProductTask =new Thread(removeExpiredProduct);


	private ZooSystem() {
		dilayProductTask.start();
	}
	
	public static ZooSystem getInstance() {
	return instance;
}
	public ProductClientFacade login(String name, String password, ClientType type) throws LoginFiledException {
		ProductClientFacade login = null;
		switch (type) {
		case WORKERS:
			WorkersFacade workerFacade = new WorkersFacade();
			login = workerFacade.login(name, password, type);
			if (login != null) {
				System.out.println("welcome worker " + " " + name);
				return workerFacade;
			}
			break;
		case PURCHASING:
			PurchasingFacade purchasingFacade = new PurchasingFacade();
			login = purchasingFacade.login(name, password, type);
			if (login != null) {
				System.out.println("welcome" + " " + name + " " + "Purchasingman");
				return purchasingFacade;
			}
			break;
		case ADMIN:
			AdminFacade adminFacade = new AdminFacade();
			login = adminFacade.login(name, password, type);
			if (login != null) {
				System.out.println("welcome" + " " + name);
				return adminFacade;
			}
		default:
			break;

		}
		return null;
	}
	
	public void shoutDown() {
		if (dilayProductTask.isAlive()) {
			removeExpiredProduct.stop();

		}
		dilayProductTask.interrupt();

	}
	
	
}