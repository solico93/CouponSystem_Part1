package coupons_system;

import java.util.ArrayList;
public class Company {

	
	private int id;
	private String name;
	private String email;
	private String password;
	private ArrayList<Coupon> coupons;
	
	public Company() { }

	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons=null;
	}

	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}
	public int indexCoupon(Coupon coupon) {
		for (int i = 0; i < this.coupons.size(); i++) {
			if(this.coupons.get(i).getId()==coupon.getId())
				return i;
		}
		return -1;
	}
	@Override
	public String toString() {
		return "company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}
 
}
