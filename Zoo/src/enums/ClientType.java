package enums;

public enum ClientType {
	PURCHASING, WORKERS, ADMIN;
	 
		public static ClientType convertFromString(String s) {
			ClientType type = null;
			
			switch (s) {
			case "PURCHASING":
			case "Purchasing":
			case "purchasing":
			type = ClientType.PURCHASING;
			break;
			
			case "WORKERS":
			case "Workers":
			case "workers":
				type= ClientType.WORKERS;
			
			break;
			
			case "ADMIN":
			case "Admin":
			case "admin":
				type = ClientType.ADMIN;
				break;
		
			}
			if (type == null) {
				System.out.println("string dosent match to any type");
			}

			return type;

}
}
