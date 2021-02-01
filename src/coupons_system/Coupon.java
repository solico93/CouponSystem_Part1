package coupons_system;

import java.sql.Date;
public class Coupon  {
	
	private int id;
	private int companyID;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String imagePath;
	
	public Coupon() {
		
	}
	
	public Coupon(int id, int companyID, Category category, String title,String description,Date startDate, Date endDate, int amount,
			double price, String imagePath) {
		
		this.id = id;
		this.companyID = companyID;
		this.category = category;
		this.title = title;
		this.description=description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imagePath = imagePath;
	}
	public Coupon( int companyID, Category category ,String title,String description,Date startDate, Date endDate, int amount,
			double price, String imagePath) {
		
		this.companyID = companyID;
		this.category = category;
		this.title = title;
		this.description=description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imagePath = imagePath;
	}
	
	public Coupon(Category category ,String title,String description,Date startDate, Date endDate, int amount,
			double price, String imagePath) {
		
		this.category = category;
		this.title = title;
		this.description=description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imagePath = imagePath;
	}
	
	//to update the coupon
public Coupon( int id , int companyID, Date endDate, int amount,double price) {
	
	this.id=id;
	this.companyID = companyID;
	this.amount = amount;
	this.price = price;
	this.endDate = endDate;
}


	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDiscription(String discription) {
		this.description = discription;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "coupon [id=" + id + ", companyID=" + companyID + ", Category=" + this.category + ", title=" + title
				+ ", Description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ ", imagePath=" + imagePath + "]";
	}
	
	
	
	
}