package dbdao;

import java.util.Collection;

import connections.ConnectionPool;
import dao.WorkersDAO;
import enums.ProductType;
import exceptions.CantFindIdWorkerException;
import javaBeans.Product;
import javaBeans.Workers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;

public class WorkersDBDAO implements WorkersDAO {
	Workers worker;
	
	private static String sql;
	private static ConnectionPool pool = ConnectionPool.getInstance();
	 
	
	 public WorkersDBDAO() {};
		// TODO Auto-generated constructor stub

	@Override
	public void createWorker(Workers workers) {
		// TODO Auto-generated method stub
		sql = "insert into workers (worker_name , password, email) values (?,?,?)";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			pstmt.setString(1, workers.getWorker_name());
			pstmt.setString(2, workers.getPassword());
			pstmt.setString(3, workers.getEmail());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
		
			rs.next();
			long id = rs.getLong(1);
			workers.setId(id);
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		
	}
	

	@Override
	public void removeWorker(long id) {
		// TODO Auto-generated method stub
		sql = "delete from  workers where id=?";
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
	public void updateWorker(Workers worker) {
		// TODO Auto-generated method stub
		sql = " update  workers set worker_name = ? , password = ?, email=? where id =?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, worker.getWorker_name());
			pstmt.setString(2, worker.getPassword());
			pstmt.setString(3, worker.getEmail());
			pstmt.setLong(4, worker.getId());
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
	public Workers getWorker(long id) throws CantFindIdWorkerException {
		// TODO Auto-generated method stub
		sql = " select * from workers where id =?";
		Connection con = pool.getConnection();

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setLong(1, id);
			ResultSet rst = pstmt.executeQuery();

			rst.next();
			String name = rst.getString(2);
			String pass = rst.getString(3);
			String email = rst.getString(4);

			worker = new Workers(id,name, pass, email);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (worker == null) {
			throw new CantFindIdWorkerException("cant find worker id");
							}
		else
			{
			return worker;
			}
	}
	
	
	  public Collection<Workers> getWorkerrByName(String workerName) {
			
		  
		  Collection<Workers> workers = new HashSet<Workers>();
			Workers worker;
		  
			sql = "select * from workers where worker_name=?";
			Connection con = pool.getConnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, workerName);
				ResultSet rst = pstmt.executeQuery();
				while (rst.next()) {
					long id = rst.getLong(1);
					String name = rst.getString(2);
					String password = rst.getString(3);
					String email = rst.getString(4);
					worker = new Workers(id, name, password, email);
					workers.add(worker);
				}
			}catch (SQLException e) {

				e.printStackTrace();
			} finally {
				pool.returnConnection(con);
			}
			if(workers.isEmpty()) {
				System.out.println("there are no workers in the Database");
			}
		   return workers;
		  		  
	   };
	  
	@Override
	public Collection<Workers> getAllWorkers() {
		// TODO Auto-generated method stub
		Collection<Workers> workers = new HashSet<Workers>();
		Workers worker;
		sql= "select * from workers";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);){
			ResultSet rst = pstmt.executeQuery();
			while(rst.next()) {
				long id = rst.getLong(1);
				String name = rst.getString(2);
				String pas = rst.getString(3);
				String email = rst.getString(4);
				worker = new Workers(id, name, pas, email);
				workers.add(worker);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			pool.returnConnection(con);
		}
		
		if(workers.isEmpty()) {
			System.out.println("there are no workers in the Database");
		}
		return workers; 
	}

	public Collection<Product> getProductsByWorker(){
		Collection<Product> products = new HashSet<Product>();
		
		Product product;
		sql = " select * from product inner join worker_product on worker_product.id_product = product.id";
		
		
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				long Productid = rst.getLong(1);
				String title = rst.getNString(2);
				int amount = rst.getInt(3);
				ProductType type = ProductType.convertFromString(rst.getString(4));
				String message = rst.getString(5);
				double price = rst.getDouble(6);
				LocalDate start_date = rst.getDate(7).toLocalDate();
				LocalDate end_date = rst.getDate(8).toLocalDate();
				product = new Product(Productid, title,amount, type, message, price, start_date,end_date);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			pool.returnConnection(con);
		}
		
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no  products to this worker");
		}
				return products;
				
				
			};
	
		
		public Collection<Product> getAllUsedProductByWorker(long workerId){
			Collection<Product> products = new HashSet<>();
			Product product;
			
			sql = " select * from product inner join worker_product on worker_product.id_product = product.id where worker_product.id_worker =?";
			
			Connection con = pool.getConnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql);) {
				pstmt.setLong(1, workerId);
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
			}
				catch (SQLException e) {
					e.printStackTrace();
				} finally {
					pool.returnConnection(con);
				}
				if(products.isEmpty()) {
					System.out.println("set is empty, there is no products in the data");

				}
				return products;
				}
			
			
			
			
			
			
		
		
	
	
	

	@Override
	public Collection<Workers> getWorkerByProducts(long ProductId) {
		Collection<Workers> workers = new HashSet<Workers>();
		Workers worker;
		
		sql = " select * from workers inner join worker_product on 	worker_product.id_worker = id where worker_product.id_product =?";
		
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setLong(1, ProductId);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				long id = rst.getLong(1);
				String workerName = rst.getString(2);
				String password = rst.getString(3);
				String email = rst.getString(4);
				worker = new Workers(id, workerName, password,email);
				workers.add(worker);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(con);
		}
		if (workers.isEmpty()) {
			System.out.println("set is empty, there is no workers in the data");
		}
		return workers;
	};
	
	public Collection<Product> getAllUsedProductByType(long workerId, ProductType productType) {
		Collection<Product> products = new HashSet<Product>();
		Product product;
		sql = " select * from product left outer join worker_product on worker_product.id_product=product.id where worker_product.id_worker =? and product.type =?";

		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, workerId);
			pstmt.setString(2, productType.toString());
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
		} finally {
			pool.returnConnection(con);
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no products in with this type");
		}
		return products;
	}

	public Collection<Product> getAllUsedProductsByPrice(long workerId, double productPrice) {
		Collection<Product> products = new HashSet<Product>();
		Product product;
		sql = " select * from product left outer join worker_product on worker_product.id_product =product.id where worker_product.id_worker =? and product.price <?";

		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, workerId);
			pstmt.setDouble(2, productPrice);
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
		} finally {
			pool.returnConnection(con);
		}
		if (products.isEmpty()) {
			System.out.println("set is empty, there is no products with this price");
		}
		return products;
	}
		
		
		public boolean isWorkerHoldThisProduct(long workerId, long productId) {

			sql = " select * from worker_product where worker_product.id_worker =? and worker_product.id_product =?";
			Connection con = pool.getConnection();
			boolean check = false;
			try (PreparedStatement pstmt = con.prepareStatement(sql))

			{
				pstmt.setLong(1, workerId);
				pstmt.setLong(2, productId);
				ResultSet rst = pstmt.executeQuery();
				if (rst.next()) {
					check = true;
				} else {
					check = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				pool.returnConnection(con);
			}
			return check;
	

		}
	
	@Override
	public boolean login(String workerName, String password) {
		boolean check = false;
		sql = "select password from workers where worker_name= ?";
		Connection con = pool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, workerName);
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

			return check;
		}
	}

