package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Amount;

//import javax.management.Query;

import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao{
	private Session session;
	private Transaction tx;

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		org.hibernate.SessionFactory sessionFactory = configuration.buildSessionFactory();
    	session = sessionFactory.openSession();
	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		session.close();
	}

	@Override
	public Employee getEmployee(int userNumber, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		ArrayList<Product> inventory = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("select product from Product product");
			List<Product> productList = q.list();
			
			inventory.addAll(productList);
			
			for (Product product : inventory) {
				System.out.println(product);
				//set wholesalerPrice and publicPrice with price
				product.setWholesalerPrice(new Amount(product.getPrice()));
				product.setPublicPrice(new Amount(product.getPrice()*2));
			}
			
			tx.commit();
			System.out.println("Get Inventory Successfully.");
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}

		return inventory;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		try {
			tx = session.beginTransaction();

			//we generate and save all new Mark elements
			for (Product product : inventory) {
				ProductHistory inventoryHistory = new ProductHistory(product.getId(), product.getName(), product.getPrice(), product.isAvailable(), product.getStock());
				session.save(inventoryHistory);			
			}
			// we update this information in the Database
			tx.commit();
			System.out.println("Write Inventory Successfully.");
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		try {
			System.out.println(product.getPrice());

			tx = session.beginTransaction();

			session.save(product);

			tx.commit();
			System.out.println("Product inserted Successfully.");
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	
	}

	@Override
	public void addStock(String name, int stock) {
		// TODO Auto-generated method stub
		try {
			for (Product product : this.getInventory()) {
				if (product.getName().equals(name)) {
					product.setStock(product.getStock() + stock);
					tx = session.beginTransaction();
					session.save(product);
					tx.commit();
					System.out.println("Stock added Successfully.");					
				}				
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(String name) {
		// TODO Auto-generated method stub		
		try {
			for (Product product : this.getInventory()) {
				if (product.getName().equals(name)) {
					tx = session.beginTransaction();
					session.remove(product);
					tx.commit();
					System.out.println("Deleted Successfully.");					
				}				
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback(); // Roll back if any exception occurs.
			e.printStackTrace();
		}
	}

}
