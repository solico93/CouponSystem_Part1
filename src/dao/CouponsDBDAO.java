package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connections.ConnectionPool;
import coupons_system.Category;
import coupons_system.Coupon;
import services.CouponsDAO;
import exceptions.CouponException;



public class CouponsDBDAO implements CouponsDAO {

		
	private ConnectionPool pool=ConnectionPool.getInstance();	
	private Connection connection;

		/**
		 * @throws CouponException 
		 * @Description:
		 * A method that gets Coupon object to construct an SQL query from it's values
		 * and insert the object values into the related table.
		 * */
		@Override
		public void addCoupons(Coupon coupon) throws CouponException {
			try {
				connection = pool.getConnection();
				String sql = "INSERT INTO  coupons_forward_cusotmer.coupons (company_id , category_id,"
						+ "title , description , start_date , end_date , amount , price , image) VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1,coupon.getCompanyID());
				statement.setInt(2, coupon.getCategory().getValue());
				statement.setString(3, coupon.getTitle());
				statement.setString(4, coupon.getDescription());
				statement.setDate(5,   coupon.getStartDate());
				statement.setDate(6, coupon.getEndDate());
				statement.setInt(7, coupon.getAmount());
				statement.setDouble(8, coupon.getPrice());
				statement.setString(9, coupon.getImagePath());
				statement.execute();
				
				//set company ID
				coupon.setCompanyID(coupon.getCompanyID());
				statement.close();
				
				// set Coupon ID
				int couponID = 0;
				String query = "SELECT * FROM  coupons_forward_cusotmer.coupons WHERE title=?";
				PreparedStatement stat = connection.prepareStatement(query);
				stat.setString(1, coupon.getTitle());
				ResultSet rs = stat.executeQuery();
				while (rs.next()) {
					couponID = rs.getInt("id");
					coupon.setId(couponID);
				}
				stat.close();
				System.out.println("Coupon Created\n");
				
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Add Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Add Coupon Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		
		/**
		 * @throws CouponException 
		 * @Description:
		 * A method that gets Coupon object to construct an SQL query with it's values
		 *  update the object values into the related table.
		 *  */
		@Override
		public void updateCoupon(Coupon coupon) throws CouponException{//, Company curCompany) {	
			try {
				connection = pool.getConnection();
				
				String sql = "UPDATE  coupons_forward_cusotmer.coupons SET"
						+ " end_date=? ,amount=? , price=? WHERE (id=?)";
				PreparedStatement statement1 = connection.prepareStatement(sql);
				
				statement1.setDate(1, coupon.getEndDate());
				statement1.setInt(2, coupon.getAmount());
				statement1.setDouble(3, coupon.getPrice());
				statement1.setInt(4, coupon.getId());
				statement1.executeUpdate();
				coupon.setId(coupon.getId());
				System.out.println("Coupon Updated!");
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Update Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Update Coupon Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		

		/**
		 * @throws CouponException 
		 * @Description:
		 * Remove Coupon.
		 * Will remove Coupons from coupon tables depends on coupon_ID and company_ID. 
		 */
		@Override
		public void deleteCoupon(int couponID) throws CouponException {
			try {
				if(getOneCoupon(couponID)!=null) {
					String query = "DELETE FROM coupons_forward_cusotmer.coupons WHERE id=?";
					PreparedStatement stat = connection.prepareStatement(query);
					stat.setInt(1, couponID);
					stat.executeUpdate();
					System.out.println("Coupon Id = "+ couponID + " Deleted");
				}
				else
					System.err.println("Coupon Doesn't Exist");
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Delete Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Delete Coupon Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}

		/**
		 * @Description:
		 * A method that return all coupons
		 * the method send a query to the database and retrieves results.
		 * with ResultSet and While loop the method construct Coupon object and add it the the collection. 
		 * @return Set<Coupon> set
		 * @throws CouponException 
		 */
		@Override
		public synchronized ArrayList<Coupon> getAllCoupons() throws CouponException {
			
			ArrayList<Coupon> arr = new ArrayList<>();
			try{
				connection = pool.getConnection();
				
				String sql = "SELECT * FROM  coupons_forward_cusotmer.coupons";
				Statement statement = connection.createStatement(); 
				ResultSet rs = statement.executeQuery(sql);
				if(rs==null)
					System.err.println("No Coupons In the DB!");
				else {
					while (rs.next()) {
						Coupon coupon = new Coupon();
						coupon.setId(rs.getInt("id"));
						coupon.setCompanyID(rs.getInt("company_id"));
						coupon.setCategory(Category.valueOf(rs.getInt("category_id")));
						coupon.setTitle(rs.getString("title"));
						coupon.setDiscription(rs.getString("description"));
						coupon.setStartDate(rs.getDate("start_date"));
						coupon.setEndDate(rs.getDate("end_Date"));
						coupon.setAmount(rs.getInt("amount"));
						coupon.setPrice(rs.getDouble("price"));
						coupon.setImagePath(rs.getString("image"));
						arr.add(coupon);
					}
				}
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Get Coupons Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Get Coupons Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
			return arr;
		}
		
		/**
		 * @throws CouponException 
		 * @Description:
		 * A method that return coupon By id
		 * the method send a query to the database and retrieves results.
		 */
		@Override
		public Coupon getOneCoupon(int couponID) throws CouponException {
			Coupon c1 = null;
			try{
				connection = pool.getConnection();
				String sql = "SELECT * FROM  coupons_forward_cusotmer.coupons WHERE id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, couponID);
				ResultSet rs = statement.executeQuery();				
				if(rs.next()) {
					c1 = new Coupon(rs.getInt("id"),
						rs.getInt("company_id"),
						Category.valueOf(rs.getInt("category_id")),
						rs.getString("title"),
						rs.getString("description"),
						rs.getDate("start_date"),
						rs.getDate("end_date"),
						rs.getInt("amount"),
						rs.getDouble("price"),
						rs.getString("image"));
				}
				else
					System.err.println("No Coupons In the DB!");
				
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Get Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Get Coupon Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
			return c1;
		}

		/**
		 * @throws CouponException 
		 * @Description:
		 * A method that return wether the coupon in our db or not
		 * the method send a query to the database and retrieves results.
		 */
		
		
		@Override
		public boolean isCouponExists(Coupon coupon) throws CouponException {
			boolean flag = false;
			try {
				connection = pool.getConnection();
				String sql = "SELECT * FROM coupons_forward_cusotmer.coupons where id= ?";
				PreparedStatement st = connection.prepareStatement(sql);
				st.setInt(1, coupon.getId());
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					flag = true;
				}
				return flag;

			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Check Coupon Exsist Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Check Coupon Exsit Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		
		/**
		 * @throws CouponException 
		 * @Description:
		 * A method that gets an customerID and couponID 
		 * and insert the coupon values into the related table.
		 * */
		@Override
		public void addCouponPurchase(int customerID, int couponID) throws CouponException {
			
			try {
				connection = pool.getConnection();
				boolean rs = isCustomerToCoupon(customerID, couponID);
				if(!rs) 
				{
					String sql = "INSERT INTO  coupons_forward_cusotmer.customers_vs_coupons (customer_id,coupon_id) VALUES (?,?)";
					PreparedStatement stetment = connection.prepareStatement(sql);
					stetment.setInt(1, customerID);
					stetment.setInt(2, couponID);
					stetment.executeUpdate();
					
					System.out.println("couponID = "+couponID+" customerID = " + customerID);
					System.out.println("Successed!");
				}
				else
					System.err.println("This Customer Bought This Coupon Before!");
			
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Purchase Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Purchase Coupon Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		
		/**
		 * @throws CouponException 
		 * @Description:
		 * Method that checks if the customer belongs to the same given coupon
		 * The test relies on a scan depends on coupon_ID and company_ID from The test relies on a scan table . 
		 */
		@Override
		public boolean isCustomerToCoupon(int customerID, int couponID) throws CouponException {
		
			try {
			connection = pool.getConnection();
			boolean bool=false;
			String query="SELECT * FROM coupons_forward_cusotmer.customers_vs_coupons WHERE customer_id=? AND coupon_id=?";
			PreparedStatement stat=connection.prepareStatement(query);
			stat.setInt(1, customerID);
			stat.setInt(2, couponID);
			ResultSet rs=stat.executeQuery();
			if(rs.next())
				bool=true;
			return bool;
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Check Coupon To Customer Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Check Coupon To Customer Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
			
		}


		
		/**
		 * @throws CouponException 
		 * @Description:
		 * Remove Coupon_vs_customer that belongs to the same given coupon.
		 * Will remove Coupons from coupon tables depends on coupon_ID and company_ID. 
		 */
		@Override
		public void deleteCouponPurchase(int customerID, int couponID) throws CouponException {
			try {
				connection = pool.getConnection();
				boolean rs = isCustomerToCoupon(customerID, couponID);
				if(rs) {
					String sql2 = "DELETE FROM  coupons_forward_cusotmer.customers_vs_coupons WHERE customer_id=? AND coupon_id=?";
					PreparedStatement stetment = connection.prepareStatement(sql2);
					stetment.setInt(1, customerID);
					stetment.setInt(2, couponID);
					stetment.executeUpdate();
					System.out.println("couponID= "+couponID+" customerID+ " + customerID);
					System.out.println("Delete Successed");
				}else
					System.err.println("This Customer Didn't Buy This Coupon Before!");

			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Delete Coupon Purchase Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Delete Coupon Purchase Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		
		@Override
		public void deleteCouponPurchase(int couponID) throws CouponException {
			try {
				connection = pool.getConnection();
				String sql = "SELECT * FROM  coupons_forward_cusotmer.customers_vs_coupons WHERE coupon_id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, couponID);
				ResultSet rs = statement.executeQuery();
				if(rs.next()) {
					String query = "DELETE FROM coupons_forward_cusotmer.customers_vs_coupons WHERE coupon_id=?";
					PreparedStatement stat = connection.prepareStatement(query);
					stat.setInt(1, couponID);
					stat.executeUpdate();
					System.out.println("Purchase Coupon ID= "+couponID);
					System.out.println("Deleted Successed");
				}

			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Delete Coupon Purchase Failed.");
			} catch (Exception e) {
				throw new CouponException("System ERROR! Delete Coupon Purchase Failed.");
			} finally {
				pool.restoreConnection(connection);
			}
		}
		
		@Override
		public void deleteExpiredCoupon() throws CouponException {
			try {
				java.util.Date currentDate = new java.util.Date();
				ArrayList<Coupon> listCoupons= getAllCoupons();
				for (int i = 0; i < listCoupons.size(); i++) {
					if(listCoupons.get(i).getEndDate().compareTo(currentDate)<0) {
						System.out.println("Synchronization To Delete Expired Coupon!");
						deleteCouponPurchase(listCoupons.get(i).getId());
						deleteCoupon(listCoupons.get(i).getId());
					}
				}
			} catch (Exception e) {
				throw new CouponException("System ERROR! Delete Expired Coupon Failed.");
			}
		}
		
}