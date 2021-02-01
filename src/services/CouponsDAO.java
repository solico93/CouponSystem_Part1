package services;

import java.util.ArrayList;

import coupons_system.Coupon;
import exceptions.CouponException;


public interface CouponsDAO {

	public void addCoupons(Coupon coupon) throws CouponException;
	public void updateCoupon(Coupon coupon) throws CouponException;
	public void deleteCoupon(int couponID) throws CouponException;
	public Coupon getOneCoupon(int couponID) throws CouponException;
	public ArrayList<Coupon> getAllCoupons() throws CouponException;
	public void addCouponPurchase(int CustomerID,int couponID) throws CouponException;
	public void deleteCouponPurchase(int CustomerID,int couponID) throws CouponException;
	boolean isCouponExists(Coupon S) throws CouponException;
	public void deleteCouponPurchase(int couponID) throws CouponException;
	public void deleteExpiredCoupon() throws CouponException;
	public boolean isCustomerToCoupon(int customerID, int couponID) throws CouponException;
}