package dao;

import java.sql.SQLException;
import java.util.Collection;

import exceptions.CantFindIdWorkerException;

import javaBeans.Product;
import javaBeans.Workers;


public interface WorkersDAO {
	
	
public void createWorker(Workers workers);



	/**
	 * receives a worker id and removes this worker from the database
	 * 
	 * @param id
	 *            receives a company's id and removes this worker from the
	 *            database
	 */
	public void removeWorker(long id);

	/**
	 * update a worker at the database
	 * 
	 * @param worker
	 *            receives a worker's instance and updates this worker in
	 *            the database
	 */
	public void updateWorker(Workers worker);

	/**
	 * by worker id, get this worker from the database
	 * 
	 * @param id,
	 *            this worker id
	 * @throws SQLException 
	 * @throws CantFindIdWorkerException
	 *             if this id dosen't exists in the database this exception
	 *             throw
	 */

	public Workers getWorker(long id) throws CantFindIdWorkerException ;

	/**
	 * returns an ArrayList of all the worker that exists in the database
	 * 
	 * @return an ArrayList of all the worker that exists in the database
	 */
	
	public Collection<Workers> getWorkerrByName(String workerName);
	public Collection<Workers> getAllWorkers();
	
	public Collection<Product> getProductsByWorker();
	public Collection<Workers> getWorkerByProducts(long id);
	
	public Collection<Product> getAllUsedProductByWorker(long workerId);
	public boolean isWorkerHoldThisProduct(long workerId, long productId);
	
	
	
	
	



	/**
	 * compare by the database if the name and the password are correct
	 * 
	 * @param workerName
	 *            the worker's name
	 * @param password
	 *            the worker's password
	 * @return true value if there is a match, false if there is no match
	 */

	public boolean login(String workerName, String password);

}



