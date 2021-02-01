package services;

import java.util.ArrayList;

import coupons_system.Coupon;
import coupons_system.Customer;
import exceptions.CouponException;

public interface CustomersDAO {

	public boolean isCustomerExists(String email,String password) throws CouponException ;
	public void addCustomer(Customer customer) throws CouponException ;
	public void updateCustomer(Customer customer) throws CouponException ;
	public void deleteCustomer(int customerID) throws CouponException ;
	public Customer getOneCustomer(int customerID) throws CouponException ;
	public ArrayList<Customer> getAllCustomers() throws CouponException ;
	ArrayList<Coupon> getCustomerCoupons(int companyID) throws CouponException;
	public Coupon getOneCouponByTitle(String titleCoupon) throws CouponException;
	public void deleteCustomerCoupons(int customerID) throws CouponException;
	public Customer getCustomer(String email, String password) throws CouponException;
}
