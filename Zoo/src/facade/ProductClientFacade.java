package facade;

import enums.ClientType;

public interface ProductClientFacade {
	public ProductClientFacade  login(String name, String password, ClientType type);

}
