package test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import coupons_system.Category;
import coupons_system.ClientType;
import coupons_system.Company;
import coupons_system.Coupon;
import coupons_system.Customer;
import daily_job.CouponExpirationDailyJob;
import exceptions.CouponException;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import facade.LoginManager;

public class Test {			

	@SuppressWarnings("deprecation")
	public static void testAll() throws CouponException {
		
				CouponExpirationDailyJob DailyTask = new CouponExpirationDailyJob();
				Thread thread = new Thread(DailyTask);
				thread.start();
				
				java.util.Date date = new java.util.Date("01/01/2020");
				java.sql.Date startDate = new Date(date.getTime());
				
				java.util.Date date1 = new java.util.Date("2021/01/01");
				java.sql.Date endDate1 = new Date(date1.getTime());

				java.util.Date date2 = new java.util.Date("2021/01/28");
				java.sql.Date endDate2 = new Date(date2.getTime());

				java.util.Date date3 = new java.util.Date("2021/01/30");
				java.sql.Date endDate3 = new Date(date3.getTime());

				java.util.Date date4 = new java.util.Date("2021/05/05");
				java.sql.Date endDate4 = new Date(date4.getTime());

				java.util.Date date5 = new java.util.Date("2021/03/03");
				java.sql.Date endDate5 = new Date(date5.getTime());
				
				Company company1=new Company("Amdocs","amdocs@com.co.il","111");
				Company company2=new Company("Roy","Roy@com.co.il","222");
				Company company3=new Company("Docs","Docs@com.co.il","333");
				Company company4=new Company("Sclass","Sclass@com.co.il","444");
				Company company5=new Company("Soya","Soya@com.co.il","555");

				
				Customer customer1=new Customer("Natheer","Sharek","Natheer@com.co.il","111");
				Customer customer2=new Customer("Salim","Masri","Salim@com.co.il","222");
				Customer customer3=new Customer("Zienb","Garbou","Zienb@com.co.il","333");
				Customer customer4=new Customer("Dawod","Kabha","Dawod@com.co.il","444");
				Customer customer5=new Customer("Mahmod","Nono","Mahmod@com.co.il","555");
			
				
				LoginManager lm=LoginManager.getInstance();
				
				System.out.println("Enter Type Client: Administrator Or Company Or Customer");

				//Login By Administrator Client
				ClientFacade CF=lm.login("admin@admin.com", "admin", ClientType.Administrator);
				
				//Login By Company Client
				//ClientFacade CF=lm.login("amdocs@com.co.il","111", ClientType.Company);
				
				//Login By Customer Client
				//ClientFacade CF=lm.login("Natheer@com.co.il","111", ClientType.Customer);
				boolean flagStopSystem=true;

				while(flagStopSystem) {
				if (CF instanceof AdminFacade) {
					System.out.println("Welcome Admin Facade");
					System.out.println("____________________");

					AdminFacade adminF=(AdminFacade)CF;
					
					System.out.println("Start Function addCompany(Company company) Add Companies");	

					adminF.addCompany(company1);
					adminF.addCompany(company2);
					adminF.addCompany(company3);
					adminF.addCompany(company4);
					adminF.addCompany(company5);
					
					System.out.println("Start Function updateCompany(Company company) Update Companies");	
					Company updatedCompany1=new Company(company1.getId(),"Amdocs","amdocs2021@com.co.il","4213");
					Company updatedCompany2=new Company(company2.getId(),"Roy","Roy21@com.co.il","2242132");
					Company updatedCompany3=new Company(company3.getId(),"Docs","Docs2122@com.co.il","334323");
					Company updatedCompany4=new Company(company4.getId(),"Sclass","Sclass2223@com.co.il","432144");
					Company updatedCompany5=new Company(company5.getId(),"Soya","Soya211@com.co.il","553235");
					adminF.updateCompany(updatedCompany1);
					adminF.updateCompany(updatedCompany2);
					adminF.updateCompany(updatedCompany3);
					adminF.updateCompany(updatedCompany4);
					adminF.updateCompany(updatedCompany5);
					
					System.out.println("Start Function getAllCompanies() Get All Companies");	

					printCompany(adminF.getAllCompanies());
					
					System.out.println("Start Function getOneCompany() Get One Company");	

					System.out.println(adminF.getOneCompany(company1.getId()).toString());
					System.out.println(adminF.getOneCompany(company2.getId()).toString());
					System.out.println(adminF.getOneCompany(company3.getId()).toString());
					System.out.println(adminF.getOneCompany(company4.getId()).toString());
					System.out.println(adminF.getOneCompany(company5.getId()).toString());

					System.out.println("Start Function deleteCompany(int companyId) Delete Companies");	

					adminF.deleteCompany(company1.getId());
					adminF.deleteCompany(company2.getId());
					adminF.deleteCompany(company3.getId());
					adminF.deleteCompany(company4.getId());
					adminF.deleteCompany(company5.getId());
					
					System.out.println("Start Function addCustomer(Customer customer) Add Customers");	

					adminF.addCustomer(customer1);
					adminF.addCustomer(customer2);
					adminF.addCustomer(customer3);
					adminF.addCustomer(customer4);
					adminF.addCustomer(customer5);

					System.out.println("Start Function updateCustomer(Customer customer) Update Customers");	

					adminF.updateCustomer(customer1);
					adminF.updateCustomer(customer2);
					adminF.updateCustomer(customer3);
					adminF.updateCustomer(customer4);
					adminF.updateCustomer(customer5);
					
					System.out.println("Start Function getAllCustomers() Get All Customers");	

					printCustomer(adminF.getAllCustomers());

					System.out.println("Start Function getOneCustomer(int customerId()) Get One Customer");	

					System.out.println(adminF.getOneCustomer(customer1.getId()).toString());
					System.out.println(adminF.getOneCustomer(customer2.getId()).toString());
					System.out.println(adminF.getOneCustomer(customer3.getId()).toString());
					System.out.println(adminF.getOneCustomer(customer4.getId()).toString());
					System.out.println(adminF.getOneCustomer(customer5.getId()).toString());
					
					System.out.println("Start Function deleteCustomer(int customerId()) Delete Customers");	

					adminF.deleteCustomer(customer1.getId());
					adminF.deleteCustomer(customer2.getId());
					adminF.deleteCustomer(customer3.getId());
					adminF.deleteCustomer(customer4.getId());
					adminF.deleteCustomer(customer5.getId());
				}
				
				if (CF instanceof CompanyFacade) {
					
					CompanyFacade companyF=(CompanyFacade)CF;
					
					System.out.println("Welcome To "+ companyF.getCompanyDetails().getName()+" Coupons System!");
					System.out.println("____________________");
					
					Coupon coupon1= new Coupon(companyF.getCompanyID(), Category.valueOf(0),"t1",
							 "description1" , startDate ,endDate1,1,5.5,"image1");
					Coupon coupon2= new Coupon(companyF.getCompanyID(), Category.valueOf(1),"t2",
							 "description2" , startDate ,endDate2,1,10.5,"image2");
					Coupon coupon3= new Coupon(companyF.getCompanyID(), Category.valueOf(2),"t3",
							 "description3" , startDate ,endDate3,1,20,"image3");
					Coupon coupon4= new Coupon(companyF.getCompanyID(), Category.valueOf(3),"t4",
							 "description4" , startDate ,endDate4,1,30,"image4");
					Coupon coupon5= new Coupon(companyF.getCompanyID(), Category.valueOf(1),"t4",
							 "description5" , startDate ,endDate5,1,40,"image5");
					
					System.out.println("Start Function addCoupons(Coupon coupon) Add Coupons");	

					companyF.addCoupons(coupon1);
					companyF.addCoupons(coupon2);
					companyF.addCoupons(coupon3);
					companyF.addCoupons(coupon4);
					companyF.addCoupons(coupon5);
					
					System.out.println("Start Function updateCoupon(Coupon coupon) Update Coupons");	

					companyF.updateCoupon(coupon1);
					companyF.updateCoupon(coupon2);
					companyF.updateCoupon(coupon3);
					companyF.updateCoupon(coupon4);
					companyF.updateCoupon(coupon5);

					System.out.println("Start Function getCompanyCoupons() 5 Coupons");	

					printCoupons(companyF.getCompanyCoupons());
					
					System.out.println("Start Function getCompanyCoupons(Category category) Get Company Coupons By Category");	

					System.out.println(companyF.getCompanyCoupons(Category.valueOf(0)));
					System.out.println(companyF.getCompanyCoupons(Category.valueOf(1)));
					System.out.println(companyF.getCompanyCoupons(Category.valueOf(2)));
					System.out.println(companyF.getCompanyCoupons(Category.valueOf(3)));

					System.out.println("Start Function getCompanyCoupons(double maxPrice) Get Company Coupons By Max Price");	

					System.out.println(companyF.getCompanyCoupons(1));
					System.out.println(companyF.getCompanyCoupons(10));
					System.out.println(companyF.getCompanyCoupons(20));
					System.out.println(companyF.getCompanyCoupons(30));
					System.out.println(companyF.getCompanyCoupons(70));

					System.out.println("Start Function getCompanyCoupons(double maxPrice) Get Company Details");	

					System.out.println(companyF.getCompanyDetails().toString());
					
					System.out.println("Start Function deleteCoupon(int couponId) 5 Coupons");	

					companyF.deleteCoupon(coupon1.getId());
					companyF.deleteCoupon(coupon2.getId());
					companyF.deleteCoupon(coupon3.getId());
					companyF.deleteCoupon(coupon4.getId());
					companyF.deleteCoupon(coupon5.getId());
				}
				
				if (CF instanceof CustomerFacade) {
					CustomerFacade customerF=(CustomerFacade)CF;
					
					System.out.println("Welcome "+ customerF.getCustomerDetails().getFirstName()+" "+customerF.getCustomerDetails().getLastName()+" To Coupons System!");
					System.out.println("____________________");
					
					Coupon coupon1=customerF.getOneCoupon("t1");
					Coupon coupon2=customerF.getOneCoupon("t2");
					Coupon coupon3=customerF.getOneCoupon("t3");
					Coupon coupon4=customerF.getOneCoupon("t4");
					Coupon coupon5=customerF.getOneCoupon("t5");
					
					System.out.println("Start Function purchaseCoupon(Coupon coupon) purchase 5 Coupons");	

					customerF.purchaseCoupon(coupon1);
					customerF.purchaseCoupon(coupon2);
					customerF.purchaseCoupon(coupon3);
					customerF.purchaseCoupon(coupon4);
					customerF.purchaseCoupon(coupon5);
					
					System.out.println("Start Function getCustomerCoupons() Get Customer Coupons");	

					printCoupons(customerF.getCustomerCoupons());
					
					System.out.println("Start Function getCustomerCoupons(Category category) Get Customer Coupons By Category");	

					System.out.println(customerF.getCustomerCoupons(Category.valueOf(0)));
					System.out.println(customerF.getCustomerCoupons(Category.valueOf(1)));
					System.out.println(customerF.getCustomerCoupons(Category.valueOf(2)));
					System.out.println(customerF.getCustomerCoupons(Category.valueOf(3)));
					
					System.out.println("Start Function getCustomerCoupons(double maxPrice) Get Customer Coupons By Max Price");	

					System.out.println(customerF.getCustomerCoupons(1));
					System.out.println(customerF.getCustomerCoupons(10));
					System.out.println(customerF.getCustomerCoupons(20));
					System.out.println(customerF.getCustomerCoupons(30));
					System.out.println(customerF.getCustomerCoupons(70));

					System.out.println("Start Function getCustomerDetails() Get Customer Details");	

					System.out.println(customerF.getCustomerDetails());
				}
				flagStopSystem = continueOrShutDown(flagStopSystem);
			}

				
	}

	//Print Without ToString() Because We MUST To Validate The Value Isn't Null
	public static void printCompany(ArrayList<Company> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
	
	public static void printCustomer(ArrayList<Customer> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
	
	public static void printCoupons(ArrayList<Coupon> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}
	
	public static boolean continueOrShutDown(boolean flagStopSystem) {
		System.out.println("Do You Want Login Or ShutDown The System?\n Press Y=Login Or N=ShutDown");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean inputValidate=true;
		while (inputValidate) {
			char yesOrNo=sc.next().charAt(0);
			if(yesOrNo=='Y'||yesOrNo=='y') {
				flagStopSystem=true;
				inputValidate=false;
			}
			else if(yesOrNo=='N'||yesOrNo=='n') {
				flagStopSystem=false;
				inputValidate=false;
			}
			else {
				System.out.println("Please Enter Correct Input Again:\n Press Y=Login Or N=ShutDown");
				yesOrNo = sc.next().charAt(0);
				inputValidate=true;
			}
		}
		return flagStopSystem;
	}

}
