package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import coupons_system.Category;
import coupons_system.Coupon;
import coupons_system.Customer;
import dao.CouponsDBDAO;
import dao.CustomersDBDAO;
import exceptions.CouponException;
import services.CouponsDAO;
import services.CustomersDAO;

public class CustomerFacade extends ClientFacade {
	
	private CustomersDAO customersDBDAO ;
	private CouponsDAO  couponsDBDAO;
	private int customerID;	
	
	public CustomerFacade() {
		 customersDBDAO =new CustomersDBDAO();	
		 couponsDBDAO=new CouponsDBDAO();
	}
	
	public int getId() {
		return customerID;
	} 

	@Override
	public boolean login(String email, String password) throws CouponException {
		boolean isCompanyExist = customersDBDAO.isCustomerExists(email, password);
		if (isCompanyExist) {
			this.customerID=customersDBDAO.getCustomer(email, password).getId();
			return isCompanyExist;
		}
			return isCompanyExist;	
	}
	
	/**
	 * 
	 * check if the coupon is not duplicated for the user.
	 * check if the coupon end date is not expired.
	 * check if the amount of the coupon is available.
	 * convert the date to string that why we can comparable with our coupon end date.
	 * @param coupon
	 * @throws CouponException
	 * @throws SQLException 
	 */
	
	
	public void purchaseCoupon(Coupon coupon) throws CouponException  {
		if(coupon!=null) {
			Date currentDate = new Date();
			Date endDate = coupon.getEndDate();
			int ifExpired = endDate.compareTo(currentDate);//should>0
			if (coupon.getAmount()>0) {
				if(ifExpired>0) {
					if(!couponsDBDAO.isCustomerToCoupon(customerID,coupon.getId())) {
				coupon.setAmount(coupon.getAmount()-1);
				//After Purchase Update Amount -1
				couponsDBDAO.updateCoupon(coupon);
				couponsDBDAO.addCouponPurchase(customerID, coupon.getId());
					}else
						System.err.println("This Customer Bought The Coupon Can't Add Again");
				}else
					System.err.println("Can't Add The Expired Coupon.");
			}else {
					System.err.println("Sorry, Amount Of This Coupon Finished.");
			}
		}
		else
			System.err.println("STOP! Coupon Information Null! Purchase Coupon Failed!"); 
		}
	

	public ArrayList<Coupon> getCustomerCoupons() throws CouponException {
		return customersDBDAO.getCustomerCoupons(this.customerID);
	}
		
	public ArrayList<Coupon> getCustomerCoupons(Category category) throws CouponException {
		ArrayList<Coupon> temp=customersDBDAO.getCustomerCoupons(this.customerID);
		ArrayList<Coupon> rs=null;
		int index=-1;
		for (int i = 0; i < temp.size(); i++) 
			if(temp.get(i).getCategory().equals(category)) {
				rs=new ArrayList<Coupon>();
				index=i;
				break;
			}
		if(index!=-1) {
			for (int j = index; j < temp.size(); j++) 
				if(temp.get(j).getCategory().equals(category)) {
					Coupon c=temp.get(j);
					rs.add(c);
				}
		}
		return rs;
	    }
		
		
		public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponException{
			ArrayList<Coupon> temp=customersDBDAO.getCustomerCoupons(this.customerID);
			ArrayList<Coupon> rs=null;
			int index=-1;
			for (int i = 0; i < temp.size(); i++) 
				if(temp.get(i).getPrice()<=maxPrice) {
					rs=new ArrayList<Coupon>();
					index=i;
					break;
				}
			if(index!=-1) {
				for (int j = index; j < temp.size(); j++)
					if(temp.get(j).getPrice()<=maxPrice) {
						Coupon c=temp.get(j);
						rs.add(c);
					}
			}
			return rs;
		}
		
		public Customer getCustomerDetails() throws CouponException {
			return customersDBDAO.getOneCustomer(customerID);
		}
		
		public Coupon getOneCoupon(String titleCoupon) throws CouponException {
			return customersDBDAO.getOneCouponByTitle(titleCoupon);
		}
}