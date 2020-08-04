package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import connections.ConnectionPool;
import dao.ProductDAO;
import enums.ProductType;
import exceptions.CantFindIdException;
import exceptions.CantFindProductException;
import javaBeans.Product;
import javaBeans.PurchasingMan;

public class ProductDBDAO implements ProductDAO {
	

	Product product;
	private static String sql;
	private static String sql2;
	private static String sql3;
	private static ConnectionPool pool = ConnectionPool.getInstance();

	


	public ProductDBDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createProduct(Product product) {
		// TODO Auto-generated method stub
	
		
		sql = "insert into product (title ,amount, type, message , price , start_date, end_date) values (?,?,?,?,?,?,?)";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, product.getTitle());
			pstmt.setInt(2, product.getAmount());
			pstmt.setString(3, product.getType().name());
			pstmt.setString(4, product.getMessage());
			pstmt.setDouble(5, product.getPrice());
			pstmt.setDate(6, java.sql.Date.valueOf(product.getStart_date()));
			pstmt.setDate(7, java.sql.Date.valueOf(product.getEnd_date()));
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			long id = rs.getLong(1);
			product.setId(id);
			System.out.println("the product " + product.getTitle()+ " successfully created");
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			pool.returnConnection(con);
		}
	

		
		
	}

	@Override
	public void removeProduct(long id) {
		// TODO Auto-generated method stub
		sql = "delete from product where id=?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		System.out.println("the product  id number "+ id + " successfully removed");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}

		
	}

	@Override
	public void updateProduct(Product product) {
		Connection con = pool.getConnection();
		sql = "update product set title = ? ,amount  = ? , type = ? , message  = ? , price = ?, start_date=?  , end_date = ? where id =?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, product.getTitle());
			pstmt.setInt(2, product.getAmount());
			pstmt.setString(3, product.getType().name());
			pstmt.setString(4, product.getMessage());
			pstmt.setDouble(5, product.getPrice());
			pstmt.setDate(6, java.sql.Date.valueOf(product.getStart_date()));
			pstmt.setDate(7, java.sql.Date.valueOf(product.getEnd_date()));
			pstmt.setLong(8, product.getId());

			pstmt.executeUpdate();
System.out.println("update "+ product.getTitle() + " was successful ");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);

		}
		
	}

	@Override
	public Product getProduct(long id) throws CantFindProductException  {
		Product product = null;
		sql = " select * from product where id =?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, id);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type = ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				Double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(id, title,amount, type, message, price, start_date,end_date);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			pool.returnConnection(con);
		}
		if (product == null) {
			System.out.println("there is no coupon with this id");
			throw new CantFindProductException();

		} else {
			return product;
		}
	}
	
	
	/**
	 * 
	 * @return checking is there this product combine with in purchase man database 
	 */
	public boolean getProductByPurchasing(long pruchId, long productId) throws CantFindIdException {
		boolean check = false;
		sql = " select * from purchasing_product where id_pruch =? and id_product =?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, pruchId);
			pstmt.setLong(2, productId);

			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				check = true;
			}
			if (!check) {
				System.out.println("there is no product with this id");
				throw new CantFindIdException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		return check;
	}
	
	
	public Collection<Product> getProductsByTitle(String title)   {
		Product product = null;
		Collection<Product> products= new HashSet<>();
		sql = " select * from product where title=?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, title);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				long id = rst.getLong(1);
				int amount = rst.getInt(3);
				ProductType type = ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				Double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(id, title,amount, type, message, price, start_date,end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			pool.returnConnection(con);
		}
		if (product == null) {
			System.out.println("there is no Product with this title");
			return null;

		} else {
			return products;
		}}
	
	
	public Collection<Product>  getProductsByType(ProductType productType)   {
		Product product = null;
		Collection<Product> products= new HashSet<>();
		sql = " select * from product where type=?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, productType.name());
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				long id = rst.getLong(1);
				String title = rst.getNString(2);
				int amount = rst.getInt(3);
				String message = rst.getString(5);
				Double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(id, title,amount, productType, message, price, start_date,end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			pool.returnConnection(con);
		}
		if (product == null) {
			System.out.println("there is no Product with this type");
			return null;

		} else {
			return products;
		}
	}
	
	
	
	
	@Override
	public Collection<Product> getAllProducts() {
		Connection con = pool.getConnection();
		Collection<Product> products= new HashSet<>();
		Product product;
		sql= " select * from product";	
		try (PreparedStatement stmt = con.prepareStatement(sql);) {

			ResultSet rst = stmt.executeQuery();
			while (rst.next()) {
				long id = rst.getLong(1);
				String title = rst.getString(2);
				int amount = rst.getInt(3);
				ProductType type = ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(id, title,amount, type, message, price, start_date,end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no product in the database");
		}
		return  products ;
	}

	
	
	public void addPurchasingProduct(long purchasingManId, long productId) {
		sql = "insert into purchasing_product values(?,?) ";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, purchasingManId);
			pstmt.setLong(2, productId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
	}


	public void addWorkerProduct(long workerId, long productId) {
		sql = "insert into worker_product values(?,?) ";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, workerId);
			pstmt.setLong(2, productId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
	}

//to check it
	public void removeFromPurchasingProduct(long purchasingManId, long productId) {
		sql = "delete from purchasing_product where id_pruch = ? and id_product = ? ";
		sql2 = "delete from worker_product where id_product = ? ";
		sql3 = "delete from product where id= ?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);
				PreparedStatement pstmt2 = con.prepareStatement(sql2);
				PreparedStatement pstmt3 = con.prepareStatement(sql3);) {
			pstmt.setLong(1, purchasingManId);
			pstmt.setLong(2, purchasingManId);

			pstmt2.setLong(1, purchasingManId);
			pstmt3.setLong(1, purchasingManId);

			pstmt.executeUpdate();
			pstmt2.executeUpdate();
			pstmt3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
	}

	
	public void removeFromWorkerProduct(long workerId, long productId)  {
		sql = "delete from worker_product where id_worker= ? and id_coupon = ? ";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, workerId);
			pstmt.setLong(2, productId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
	}

	@Override
	public Collection<Product> getAllProductByEndDate(LocalDate end_date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param date
	 * @return receive coupons by Date
	 */
	
//	public Collection<Product> getAllProductByEndDate(LocalDate end_date) {
//		Collection<Product> products = new HashSet<>();
//		Product product;
//
//		sql = "select * from product where end_date < ?";
//		Connection con = pool.getConnection();
//		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
//			pstmt.setDate(1, new java.sql.Date(end_date.getTime()));
//
//			ResultSet rst = pstmt.executeQuery();
//			while (rst.next()) {
//
//				long id = rst.getLong(1);
//				String title = rst.getString(2);
//				int amount = rst.getInt(3);
//				ProductType type = ProductType.convertFromString(rst.getString(4));
//				String message = rst.getString(5);
//				double price = rst.getDouble(6);
//				LocalDate start_date = rst.getDate(7).toLocalDate();
//				
//
//				product = new Product(id, title, amount, type, message, price, start_date,end_date);
//				products.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if (products.isEmpty()) {
//			System.out.println("For your attention there are no orders after- " + end_date);
//		}
//		return products;
//	
//	}


	
}
