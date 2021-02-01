package facade;

import java.util.ArrayList;
import coupons_system.Category;
import coupons_system.Coupon;
import coupons_system.Company;
import dao.CompaniesDBDAO;
import dao.CouponsDBDAO;
import exceptions.CouponException;
import services.CompaniesDAO;
import services.CouponsDAO;

public class CompanyFacade extends ClientFacade{

	private CompaniesDAO companiesDBDAO ;
	private CouponsDAO  couponsDBDAO;
	private int companyID;
	
	public int getCompanyID() {
		return companyID;
	}
	
	public CompanyFacade() {
		companiesDBDAO =new CompaniesDBDAO();
		couponsDBDAO=new CouponsDBDAO();
	}

	@Override
	public boolean login(String email, String password) throws CouponException {
		boolean isCompanyExist = companiesDBDAO.isCompanyExists(email, password);
		if (isCompanyExist) {
			this.companyID=companiesDBDAO.getCompany(email, password).getId();
			return isCompanyExist;
		}
			return isCompanyExist;

	}
	
	/**
	 * @Description:
	 * if we disable the addCoupon function so we will lost the id. So we use this function to get coupon id 
	 * @return Coupon id
	 * @throws CouponException
	 */
	
	public int getCouponId(Coupon coupon) throws CouponException {
		for (int i = 0; i < getCompanyCoupons().size(); i++) 
			if(getCompanyCoupons().get(i).getTitle().equals(coupon.getTitle())) 
				coupon.setId(getCompanyCoupons().get(i).getId());
		return coupon.getId();
	}

	public void addCoupons(Coupon coupon) throws CouponException {
		if (coupon != null) {
			boolean flag=true;
			ArrayList<Coupon> cList=companiesDBDAO.getCompanyCoupons(this.companyID);
			int size=cList.size();
			for (int i=0; i < size; i++)
				if(cList.get(i).getTitle().equals(coupon.getTitle())&&cList.get(i).getCompanyID()==coupon.getCompanyID()){	
					flag=false;
					coupon.setId(cList.get(i).getId());
					break;
				}
			if(flag)
				couponsDBDAO.addCoupons(coupon);
			else {
				System.err.println("Can't Add Similar Title Coupon To This Company!");	
			}
		}
		else
			System.err.println("STOP! Coupon Information Null! Add Coupon Failed!"); 

	}

	public void updateCoupon(Coupon coupon) throws CouponException {
		if (coupon != null) 
			if(couponsDBDAO.isCouponExists(coupon))
				couponsDBDAO.updateCoupon(coupon);
		else 
			System.err.println("STOP! Coupon Information Null! Update Company Failed!"); 
	}
	
	public void deleteCoupon(int couponID) throws CouponException {     
		couponsDBDAO.deleteCouponPurchase(couponID);	
		couponsDBDAO.deleteCoupon(couponID);	
	}
	
	public ArrayList<Coupon> getCompanyCoupons() throws CouponException {
		return companiesDBDAO.getCompanyCoupons(companyID);
    }
	
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponException {
		ArrayList<Coupon> temp=companiesDBDAO.getCompanyCoupons(companyID);
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
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponException {	
		ArrayList<Coupon> temp=companiesDBDAO.getCompanyCoupons(companyID);
		ArrayList<Coupon> rs=null;
		int index=-1;
		for (int i = 0; i < temp.size(); i++) 
			if(temp.get(i).getPrice()<=maxPrice) {
				rs=new ArrayList<Coupon>();
				index=i;
				break;
			}
		if(index!=-1) {
			for (int j = index; j < temp.size(); j++) {
				if(temp.get(j).getPrice()<=maxPrice) {
					Coupon c=temp.get(j);
					rs.add(c);
				}
			}
		}
		return rs;
	}
	
	public Company getCompanyDetails() throws CouponException {       
		return companiesDBDAO.getOneCompany(companyID);
	}
}
