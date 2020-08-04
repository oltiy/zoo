package enums;

public enum ProductType {
	NEW,ACCEPTED,EXAMINED,APPROVED,REJECTED;

	 
	public static ProductType convertFromString(String s) {
		ProductType type = null;
		switch (s) {
		
		case "NEW":
		case "New":
		case "new":
			type = ProductType.NEW;
			break;
		
			
		case "ACCEPTED":
		case "Accepted":
		case "accepted":
			type = ProductType.ACCEPTED;
			break;
	
		case "EXAMINED":
		case "Examined":
		case "examined":
			type = ProductType.EXAMINED;
			break;
		
		case "APPROVED":
		case "Approved":
		case "approved":
			type = ProductType.APPROVED;
			break;
		
		case "REJECTED":
		case "Rejected":
		case "rejected":
			type = ProductType.REJECTED;
			break;
	

		}
		if (type == null) {
			System.out.println("string dosent match to any type");

		}
		return type;
	}

	public ProductType getTipe() {

		return this;

	}
}
