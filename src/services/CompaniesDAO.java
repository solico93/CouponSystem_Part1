package services;

import java.util.ArrayList;
import coupons_system.Company;
import coupons_system.Coupon;
import exceptions.CouponException;


public interface CompaniesDAO {

	public boolean isCompanyExists(String email, String password) throws CouponException;
	public void addCompany(Company company) throws CouponException;
	public Company getCompany(String email, String password) throws CouponException;
	public void updateCompany(Company company) throws CouponException;
	public void deleteCompany(int companyID) throws CouponException;
	public Company getOneCompany(int companyID) throws CouponException;
	public ArrayList<Company> getAllCompanies() throws CouponException;
	public ArrayList<Coupon> getCompanyCoupons(int companyID) throws CouponException;
	public boolean isNameCompanyExists(String name) throws CouponException;
	public Company getCompanyByName(String name) throws CouponException;
}
