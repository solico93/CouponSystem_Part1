package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connections.ConnectionPool;
import coupons_system.Company;
import coupons_system.Coupon;
import exceptions.CouponException;
import services.CompaniesDAO;

public class CompaniesDBDAO implements CompaniesDAO{
	
	private ConnectionPool pool=ConnectionPool.getInstance();	
	private Connection connection;
	
	@Override
	public void addCompany(Company company) throws CouponException {	
	  try {	
		  connection = pool.getConnection();
		  String query = "INSERT INTO coupons_forward_cusotmer.companies (name,email,password) VALUES (?,?,?)";
		  PreparedStatement preparedStatement = connection.prepareStatement(query);		           
		  preparedStatement.setString(1, company.getName());
		  preparedStatement.setString(2, company.getEmail());
		  preparedStatement.setString(3, company.getPassword());
		  preparedStatement.executeUpdate();
		  company.setId(getCompany(company.getEmail(), company.getPassword()).getId());
		  System.out.println("Company Added");

	   } catch (SQLException e) {
		   	throw new CouponException("DB ERROR! Create New Company Failed.");
	   } catch (CouponException e) {
			throw new CouponException("APP ERROR! Create New Company Failed.");
	   }finally {
			pool.restoreConnection(connection);
	   }
	}

	
	@Override
	public void updateCompany(Company company) throws CouponException  {
		 try {	
			  connection = pool.getConnection();
			  String query = "UPDATE coupons_forward_cusotmer.companies SET password = ?, email = ?  WHERE name = ?";
			  PreparedStatement preparedStatement = connection.prepareStatement(query);  
			  preparedStatement.setString(1, company.getPassword());
			  preparedStatement.setString(2, company.getEmail());
			  preparedStatement.setString(3, company.getName());
			  preparedStatement.executeUpdate();
			  company.setId(getCompany(company.getEmail(), company.getPassword()).getId());
			  System.out.println("Updated");
		 } catch (SQLException e) {
				throw new CouponException("DB ERROR! Update Company Failed.");
		 } catch (Exception e) {
				throw new CouponException("System ERROR! Update Company Failed.");
		 } finally {
				pool.restoreConnection(connection);
		 }
	}
	
	@Override
	public void deleteCompany(int companyID) throws CouponException {
		try {
			connection = pool.getConnection();
	  		String query = "delete from companies where id=?";
	  		PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, companyID);
	        preparedStatement.executeUpdate();
	  		System.out.println("Company Deleted");

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Delete Company Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Delete Company Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	
	@Override
	public ArrayList<Company> getAllCompanies() throws CouponException {
		ArrayList<Company> getAllCompany = new ArrayList<Company>() ;
        try {
        	connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from companies");
            ResultSet rs = preparedStatement.executeQuery();
            if(rs!=null) {
	            while (rs.next()) {
	            	 Company company = new Company(
	            			 rs.getInt("id"),
	            			 rs.getString("name"),
	            			 rs.getString("email"),
	            			 rs.getString("password"));
	            	 company.setCoupons(getCompanyCoupons(company.getId()));
	            	 getAllCompany.add(company);
	            }
            }
            else
            	System.out.println("No Companies In The DB!");
            
        } catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Companies Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Companies Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
        return getAllCompany;
    }

	
	@Override
	public Company getOneCompany(int companyID) throws CouponException {
		Company company = null;
        try {
        	connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from companies where id=?");
            preparedStatement.setInt(1, companyID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	company=new Company();
            	company.setName(rs.getString("name"));
            	company.setEmail(rs.getString("email"));
            	company.setPassword(rs.getString("password"));
            	company.setId(companyID);
            	company.setCoupons(getCompanyCoupons(company.getId()));
                return company;
            }
            else {
            	System.err.println("Company Doesn't Exists!");
                return company;
            }
            
        } catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Company Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Company Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
    }

	@Override
	public boolean isCompanyExists(String email, String password) throws CouponException {
		boolean bool = false;
		try {
			connection = pool.getConnection();
			String sql = "SELECT * FROM coupons_forward_cusotmer.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) 
				bool = true;
			return bool;

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Check Failed If Exists Company.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Check Failed If Exists Company.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	@Override
	public boolean isNameCompanyExists(String name) throws CouponException {
		try {
			boolean bool = false;
			connection = pool.getConnection();
			String sql = "SELECT * FROM coupons_forward_cusotmer.companies WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) 
				bool = true;
			return bool;

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Check Failed If Exists Name Company.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Check Failed If Exists Name Company.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	@Override
	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponException {
			ArrayList<Coupon> arr = new ArrayList<>();
			try{
				connection = pool.getConnection();				
				String query = "select * from coupons_forward_cusotmer.coupons  where company_id=?";
				PreparedStatement stat = connection.prepareStatement(query);
				stat.setInt(1,companyID);
				ResultSet rs = stat.executeQuery();

				while (rs.next()) {
					int coupon_id=rs.getInt("id");
					CouponsDBDAO c = new CouponsDBDAO();
					arr.add(c.getOneCoupon(coupon_id));
				}
				return arr;

			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Getting Company Coupons Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Getting Company Coupons Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
	}
	
	@Override
	public Company getCompany(String email, String password) throws CouponException {
		try {
			connection = pool.getConnection();
			Company c=null;
			String sql = "SELECT * FROM coupons_forward_cusotmer.companies WHERE email = ? AND password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				c=new Company();
            	c.setName(rs.getString("name"));
            	c.setEmail(email);
            	c.setPassword(password);
            	c.setId(rs.getInt("id"));
            	c.setCoupons(getCompanyCoupons(c.getId()));
            	return c;
			}
			else {
				System.out.println("Company Isn't Exists");
				return c;
			}

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Company Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Company Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}
	
	@Override
	public Company getCompanyByName(String name) throws CouponException{
		try {
			connection = pool.getConnection();
			Company c=null;
			String sql = "SELECT * FROM coupons_forward_cusotmer.companies WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				c=new Company();
            	c.setName(rs.getString("name"));
            	c.setEmail(rs.getString("email"));
            	c.setPassword(rs.getString("password"));
            	c.setId(rs.getInt("id"));
            	c.setCoupons(getCompanyCoupons(c.getId()));
            	return c;
			}
			else {
				System.out.println("Company Isn't Exists");
				return c;
			}

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Getting Company Failed.");
		} catch (Exception e) {
			throw new CouponException("System ERROR! Getting Company Failed.");
		} finally {
			pool.restoreConnection(connection);
		}
	}

}
