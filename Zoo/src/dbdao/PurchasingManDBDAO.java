package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import connections.ConnectionPool;
import dao.PurchasingManDAO;
import enums.ProductType;
import exceptions.CantFindProductException;
import exceptions.CantFindPurchasingManException;
import javaBeans.Product;
import javaBeans.PurchasingMan;

public class PurchasingManDBDAO implements PurchasingManDAO {
	PurchasingMan purchasingMan;
	private static String sql;
	private static ConnectionPool pool = ConnectionPool.getInstance();
	
	public PurchasingManDBDAO() {};
	
	
	@Override
	public void creatPurchasingMan(PurchasingMan PurchasingMan) {
		sql = "insert into purchasing (purch_name , password, email) values (?,?,?)";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, PurchasingMan.getPurch_name());
			pstmt.setString(2, PurchasingMan.getPassword());
			pstmt.setString(3, PurchasingMan.getEmail());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
		
			rs.next();
			long id = rs.getLong(1);
			PurchasingMan.setId(id);
			PurchasingMan.toString();
			System.out.println("the PurchasingMan " + PurchasingMan.getPurch_name()+ " successfully created");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			pool.returnConnection(con);
		}
		
	}

	@Override
	public void removePurchasingMan(long id) {
		sql = "delete from  purchasing where id=?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
	}

	@Override
	public void updatePurchasingMan(PurchasingMan purchasingMan) {
	
		sql = "update  purchasing set purch_name = ? , password = ?, email=? where id =?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, purchasingMan.getPurch_name());
			pstmt.setString(2, purchasingMan.getPassword());
			pstmt.setString(3, purchasingMan.getEmail());
			pstmt.setLong(4, purchasingMan.getId());
			pstmt.executeUpdate();
			 int count = pstmt.executeUpdate();
			 if(count==0) {
				 System.out.println("there was no data with this id ");
			 }
			 else {
				System.out.println("the update was successful");
			 }
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		
	}

	@Override
	public PurchasingMan getSpecificPurchasingMan(long id) throws CantFindPurchasingManException {
		sql = " select * from purchasing where id =?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setLong(1, id);
			ResultSet rst = pstmt.executeQuery();

			rst.next();
			String name = rst.getString(2);
			String pass = rst.getString(3);
			String email = rst.getString(4);

			purchasingMan = new PurchasingMan(id, name, pass, email);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (purchasingMan == null) {
			throw new CantFindPurchasingManException();
		} else {
			return purchasingMan;
		}
	};

	public  PurchasingMan getPurchasingManByName(String PurchasingManName) {
		
//		Collection<PurchasingMan> PurchasingMens = new HashSet<>();
		PurchasingMan purchasingMan =null;
		
		sql = "select * from purchasing where purch_name =?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql))
			{
				pstmt.setString(1, PurchasingManName);
				ResultSet rst = pstmt.executeQuery();
				while (rst.next()) {
					long id = rst.getLong(1);
					String name = rst.getString(2);
					String password = rst.getString(3);
					String email = rst.getString(4);
					purchasingMan = new PurchasingMan(id,name, password, email);
//					PurchasingMens.add(purchasingMan);
					
				                     }	
		   }catch(SQLException e) {			
			   	e.printStackTrace();
		   } finally {
					pool.returnConnection(con);
					}
		if (purchasingMan == null) {
			System.out.println("PurchasingMan set is empty");
	
	}
		return purchasingMan;
	}
			
		
	@Override
	public Collection<PurchasingMan> getAllPurchasingMen() {
		Collection<PurchasingMan> PurchasingMens = new HashSet<>();
		PurchasingMan purchasingMan;

		sql = " select * from  purchasing";

		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {

			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				long id = rst.getLong(1);
				String name = rst.getString(2);
				String pas = rst.getString(3);
				String email = rst.getString(4);
				purchasingMan = new PurchasingMan(id, name, pas, email);
				PurchasingMens.add(purchasingMan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (PurchasingMens.isEmpty()) {
			System.out.println("PurchasingMan set is empty");
		}
		return PurchasingMens;
	}

	
	
	public Collection<Product> getProductByPurchasingMen(long purchasingManId) {

		Collection<Product> products = new HashSet<>();
		Product product;

		
		sql = "select * from product inner join purchasing_product on purchasing_product.id_product = product.id where purchasing_product.id_pruch = ?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			
			pstmt.setLong(1, purchasingManId);
			ResultSet rst = pstmt.executeQuery();
			
			while (rst.next()) {
				long id = rst.getLong(1);
				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type = ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				
				product = new Product(id,title, amount, type, message, price, start_date, end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no products in the data");
		}
		return products;
	}
	
//	@Override
/*	public Collection<Product> getProductsbyPurchaser() {
		Collection<Product> products = new HashSet<>();
		Product product;

		sql = "select * from purchasing inner join purchasing_product on purchasing_product.product_id = id";

		Connection con = pool.getConnection();
		try (Statement stmt = con.createStatement();) {

			ResultSet rst = stmt.executeQuery(sql);
			while (rst.next()) {
				long id = rst.getLong(1);
				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type =  ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				Date start_date = rst.getDate(7);
				Date end_date = rst.getDate(8);
				product = new Product(id, title, amount, type, message, price, start_date, end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no products in the data");
		}
		return products;
	}
*/
	
	
	@Override
	public boolean login(String purch_name, String password) {
		boolean check = false;
		sql="select password from purchasing where purch_name =?";

		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, purch_name);
			ResultSet rst = pstmt.executeQuery();
			rst.next();
			String pass = rst.getString(1);
			if (pass.equals(password)) {
				check = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
//System.out.println(check);
		return check;
	}


	@Override
	public Collection<Product> getAllPurchasedProductByPrice(long purchasingId, double productPrice) {
		Collection<Product> products = new HashSet<>();

		sql = "select * from product inner join purchasing_product on purchasing_product.id_product = product.id  where purchasing_product.id_pruch=? and product.price=? ";
		Connection con = pool.getConnection();
	
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, purchasingId);
			pstmt.setDouble(2,productPrice);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				long id = rst.getLong(1);
				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type =  ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				Product product = new Product(id, title, amount, type, message, price, start_date, end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no products in the data");
		}
		return products;
		}
		


	@Override
	public Collection<PurchasingMan> getPurchasingManByProduct(long productId) {
		Collection<PurchasingMan> PurchasingMens = new HashSet<>();
		PurchasingMan purchasingMan;

		sql = "select * from purchasing inner join purchasing_product on purchasing_product.id_pruch = purchasing.id where purchasing_product.id_product =?";

		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, productId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				long id = rst.getLong(1);
				String name = rst.getString(2);
				String pas = rst.getString(3);
				String email = rst.getString(4);
				purchasingMan = new PurchasingMan(id, name, pas, email);
				PurchasingMens.add(purchasingMan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (PurchasingMens.isEmpty()) {
			System.out.println("PurchasingMan set is empty");
		}
		return PurchasingMens;

}
	/**
	* @return a collection of all the Product in the database by PurchasingMan id
*/
	
//	to check it again for zoo
	
//	public Product getPurchasProductByPurchasingMan(PurchasingMan purchasingMan, long productId) {
//		Product product = null;
//
//		sql = "select * from product inner join purchasing_product on purchasing_product.id_product = product.id where purchasing_product.id_pruch = ? and product.id = ?";
//		Connection con = pool.getConnection();
//		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
//			pstmt.setLong(1, purchasingMan.getId());
//			pstmt.setLong(2, productId);
//
//			ResultSet rst = pstmt.executeQuery();
//			rst.next();
//
//			long id = rst.getLong(1);
//			String title = rst.getString(2);
//			int amount = rst.getInt(3);
//			ProductType type =  ProductType.convertFromString(rst.getString(4));
//			String message = rst.getString(5);
//			double price = rst.getDouble(6);
//			Date start_date = rst.getDate(7);
//			Date end_date = rst.getDate(8);
//			product = new Product(id, title, amount, type, message, price, start_date, end_date);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (product == null) {
//			System.out.println("coupon does not exist");
//		}
//		return product;
//	}
	

	
	public Collection<Product> getPurchasingManProductByDate(long purchasingMan, Date date){
		
		Collection<Product> products = new HashSet<>();
		Product product;

		sql = " select * from product inner join purchasing_product on purchasing_product.id_product = product.id where purchasing_product.id_pruch = ? and end_date < ?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, purchasingMan);
			pstmt.setDate(2, new java.sql.Date(date.getTime()));

			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {

				long id = rst.getLong(1);
				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type =  ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(id, title, amount, type, message, price, start_date, end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (products.isEmpty()) {
			System.out.println("there is no products lower date  from: " + date);
		}
		return products;
		
		
	}
	


	

}