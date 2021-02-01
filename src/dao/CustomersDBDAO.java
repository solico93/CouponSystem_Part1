package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connections.ConnectionPool;
import coupons_system.Category;
import coupons_system.Coupon;
import coupons_system.Customer;
import exceptions.CouponException;
import services.CustomersDAO;

public class CustomersDBDAO implements CustomersDAO  {

	private ConnectionPool pool=ConnectionPool.getInstance();	
	private Connection connection;
	
	@Override
	public void addCustomer(Customer customer)throws CouponException  {	
	  try {	
		  connection = pool.getConnection();
		  String query = "INSERT INTO coupons_forward_cusotmer.customers (first_name,last_name,email,password) VALUES (?,?,?,?)";
		  PreparedStatement preparedStatement = connection.prepareStatement(query);
		  preparedStatement.setString(1, customer.getFirstName());
		  preparedStatement.setString(2, customer.getLastName());
		  preparedStatement.setString(3, customer.getEmail());
		  preparedStatement.setString(4, customer.getPassword());
		  preparedStatement.executeUpdate();
		  customer.setId(getCustomer(customer.getEmail(), customer.getPassword()).getId());
		  System.out.println("Customer Added");

	  } catch (SQLException e) {
		  	throw new CouponException("DB ERROR! Create New Customer Failed.");
	  } catch (CouponException e) {
		  	throw new CouponException("APP ERROR! Create New Customer Failed.");
	  }finally {
		  	pool.restoreConnection(connection);
	  }
	}
	
	@Override
	public void updateCustomer(Customer customer) throws CouponException {
		 try {	
			  connection = pool.getConnection();
			  String query = "UPDATE coupons_forward_cusotmer.customers SET password = ?, email = ? ,first_name=?, last_name=? WHERE (id = ?)";
			  PreparedStatement preparedStatement = connection.prepareStatement(query);
			  preparedStatement.setString(1, customer.getPassword());
			  preparedStatement.setString(2, customer.getEmail());
			  preparedStatement.setString(3, customer.getFirstName());
			  preparedStatement.setString(4, customer.getLastName());
			  preparedStatement.setInt(5, customer.getId());
			  preparedStatement.executeUpdate();
			  customer.setId(getCustomer(customer.getEmail(), customer.getPassword()).getId());
			  System.out.println("Updated");
		 } catch (SQLException e) {
				throw new CouponException("DB ERROR! Update Customer Failed.");
		 } catch (Exception e) {
				throw new CouponException("System ERROR! Update Customer Failed.");
		 } finally {
				pool.restoreConnection(connection);
		 }
	}

	@Override
	public void deleteCustomer(int customerID) throws CouponException  {
		try {
			connection = pool.getConnection();
  		    String query = "delete from customers where id=?";
  		    PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.executeUpdate();
            System.out.println("Customer Deleted");
		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Delete Customer Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Delete Customer Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponException {
		ArrayList<Customer> getAllCustomer = new ArrayList<Customer>() ;
        try {
        	connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers");
            ResultSet rs = preparedStatement.executeQuery();
            if(rs!=null) {
	            while (rs.next()) {
	            	Customer customer=new Customer(
	            			rs.getInt("id"),
	            			rs.getString("first_Name"),
	            			rs.getString("last_Name"),
	            			rs.getString("email"),
	            			rs.getString("password"));
	            	customer.setCoupons(getCustomerCoupons(customer.getId()));
	           		getAllCustomer.add(customer);
	            }
	        }
            else
            	System.out.println("No Customers In The DB!");
            
        } catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Customers Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Customers Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
        return getAllCustomer;
    }
	
	@Override
	public Customer getOneCustomer(int customerID) throws CouponException  {
		Customer customer = new Customer();
        try {
        	connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where id=?");
            preparedStatement.setInt(1, customerID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	customer.setFirstName((rs.getString("first_Name")));
            	customer.setLastName((rs.getString("last_Name")));
            	customer.setEmail(rs.getString("email"));
            	customer.setPassword(rs.getString("password"));
            	customer.setId(customerID);
            	customer.setCoupons(getCustomerCoupons(customerID));
                return customer;
            }
            else {
            	System.err.println("Customer Doesn't Exists!");
                return customer;
            }
        } catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Customer Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Customer Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
    }
	
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponException {
		try {
			boolean bool=false;
			connection = pool.getConnection();
			String query = "SELECT * FROM coupons_forward_cusotmer.customers WHERE email=? AND password=? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) 
				bool=true;
			return bool;

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Check Failed If Exists Customer.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Check Failed If Exists Customer.");
		} finally {
			pool.restoreConnection(connection);
		}
		
	}

	@Override
	public void deleteCustomerCoupons(int customerID)throws CouponException {
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM  coupons_forward_cusotmer.customers_vs_coupons WHERE customer_id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				String query = "DELETE FROM coupons_forward_cusotmer.customers_vs_coupons WHERE customer_id=?";
				PreparedStatement stat = connection.prepareStatement(query);
				stat.setInt(1, customerID);
				int couponId=rs.getInt("coupon_id");
				stat.executeUpdate();
				System.out.println("Deleted Purchase Coupon Id = "+couponId+" To Customer Id = "+ customerID);
			}

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Delete Customer Purchase Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Delete Customer Purchase Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	
	//we have to return all coupon related to this customer
	@Override
	public ArrayList<Coupon> getCustomerCoupons(int CustomerID)throws CouponException {
			ArrayList<Coupon> arr = new ArrayList<>();
			try{
				connection = pool.getConnection();				
				String query1 = "select coupon_id from coupons_forward_cusotmer.customers_vs_coupons where customer_id=?";
				PreparedStatement stat1 = connection.prepareStatement(query1);
				stat1.setInt(1,CustomerID);
				ResultSet rs = stat1.executeQuery();
				while (rs.next()) {	
					int coupon_id=rs.getInt("coupon_id");
					CouponsDBDAO c = new CouponsDBDAO();
					arr.add(c.getOneCoupon(coupon_id));
				}
				return arr;

			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Getting Customer Coupons Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Getting Customer Coupons Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
	}

	@Override
	public Coupon getOneCouponByTitle(String titleCoupon) throws CouponException {
		Coupon c1 = null;
		try{
			connection = pool.getConnection();
			String sql = "SELECT * FROM  coupons_forward_cusotmer.coupons WHERE title=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, titleCoupon);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				c1 = new Coupon(
				resultSet.getInt("id"),
				resultSet.getInt("company_id"),
				Category.valueOf(resultSet.getInt("category_id")),	
				resultSet.getString("title"),
				resultSet.getString("description"),
			    resultSet.getDate("start_date"),
				resultSet.getDate("end_date"),
				resultSet.getInt("amount"),
				resultSet.getDouble("price"),
				resultSet.getString("image"));
			}
			return c1;

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Coupon By Title Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Coupon By Title Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(String email, String password) throws CouponException {
		Customer c=null;
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM coupons_forward_cusotmer.customers WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				c=new Customer();
            	c.setFirstName(rs.getString("first_name"));
            	c.setLastName(rs.getString("last_name"));
            	c.setEmail(email);
            	c.setPassword(password);
            	c.setId(rs.getInt("id"));
            	c.setCoupons(getCustomerCoupons(c.getId()));
            	return c;
			}
			else {
				System.out.println("Customer Isn't Exists");
				return c;
			}
		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Customer Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Customer Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
}
