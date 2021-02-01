package facade;

import java.util.ArrayList;
import coupons_system.Company;
import coupons_system.Coupon;
import coupons_system.Customer;
import dao.CompaniesDBDAO;
import dao.CouponsDBDAO;
import dao.CustomersDBDAO;
import exceptions.CouponException;
import services.CompaniesDAO;
import services.CouponsDAO;
import services.CustomersDAO;

public class AdminFacade extends ClientFacade {

	private final String email="admin@admin.com";
	private final String password="admin";
	
	private CustomersDAO customersDBDAO ;
	private CompaniesDAO companiesDBDAO ;
	private CouponsDAO  couponsDBDAO;
	
	public AdminFacade() {
		customersDBDAO =new CustomersDBDAO();
		companiesDBDAO =new CompaniesDBDAO();
		couponsDBDAO=new CouponsDBDAO();
	}
	
	@Override
	public boolean login(String email, String password) {
		if (this.email.equals(email) && this.password.equals(password))
			return true;
		return false;
	}
	
	public void addCompany(Company company) throws CouponException {
		if (company != null) {
			if(!companiesDBDAO.isCompanyExists(company.getEmail(),company.getPassword())) {
				if (!companiesDBDAO.isNameCompanyExists(company.getName())) 
					if (company.getPassword() != null)
						companiesDBDAO.addCompany(company);
					else {
						company.setId(companiesDBDAO.getCompanyByName(company.getName()).getId());
						System.err.println("STOP! Password Requiered! Update Add Failed!");
					}
				else {
					company.setId(companiesDBDAO.getCompanyByName(company.getName()).getId());
					System.err.println("Company Name Exists, Can't Add");
				}
			}
			else {
				company.setId(companiesDBDAO.getCompany(company.getEmail(), company.getPassword()).getId());
				System.err.println("Company Exists, Can't Add");
			}
		}
		else 
			System.err.println("STOP! Company Information Null! Add Company Failed!"); 
		
	}

	public void updateCompany(Company company) throws CouponException {
		if (company != null) {
			String companyName = company.getName();
			if (companyName != null && !companyName.equals(companiesDBDAO.getOneCompany(company.getId()).getName())) {
				if (company.getPassword() != null)
					companiesDBDAO.updateCompany(company);
				else
					System.err.println("STOP! Password Requiered! Update Company Failed!"); 
			}
			else
				System.err.println("STOP! Company Name Can't Update! Update Company Failed!"); 
		}
		else 
			System.err.println("STOP! Company Information Null! Update Company Failed!"); 
	}
	
	public void deleteCompany(int companyID) throws CouponException {
			ArrayList<Coupon> companyCoupons = companiesDBDAO.getCompanyCoupons(companyID);
			if(companyCoupons.size() == 0) {
				companiesDBDAO.deleteCompany(companyID);
			}
			else{
				for(Coupon coupon: companyCoupons) 
					couponsDBDAO.deleteCouponPurchase(coupon.getId());	
				for(Coupon coupon: companyCoupons) 
					couponsDBDAO.deleteCoupon(coupon.getId());	
				companiesDBDAO.deleteCompany(companyID);
			}
	}
	
	public ArrayList<Company> getAllCompanies() throws CouponException {
		return companiesDBDAO.getAllCompanies();
	}
	
	public Company getOneCompany(int companyID) throws CouponException {
		return companiesDBDAO.getOneCompany(companyID);
	}
	

	public void addCustomer(Customer customer) throws CouponException {
		if (!customersDBDAO.isCustomerExists(customer.getEmail(), customer.getPassword())) {
			customersDBDAO.addCustomer(customer);
		}
		else
			System.err.println("Customer Exists, Can't Add");
	}
	
	public void updateCustomer(Customer customer) throws CouponException {
		if (customer != null) {
			if (customer.getPassword() != null)
				customersDBDAO.updateCustomer(customer);
			else
				System.err.println("STOP! Password Requiered! Update Customer Failed!");
		}
		else 
			System.err.println("STOP! Customer Information Null! Update Customer Failed!");
	}

	public void deleteCustomer(int customerID) throws CouponException {
		if(getOneCustomer(customerID)!=null) {
			ArrayList<Coupon> customerCoupons = customersDBDAO.getCustomerCoupons(customerID);
			if(customerCoupons.size() == 0) {
				customersDBDAO.deleteCustomer(customerID);
			}
			else {
				customersDBDAO.deleteCustomerCoupons(customerID);
				customersDBDAO.deleteCustomer(customerID);
			}
		}
		else
			System.err.println("STOP! Customer Information Null! Delete Customer Failed!");
	}

	public ArrayList<Customer> getAllCustomers() throws CouponException {
		return customersDBDAO.getAllCustomers();
	}
	
	public Customer getOneCustomer(int customerID) throws CouponException {
		return customersDBDAO.getOneCustomer(customerID);
	}
}
