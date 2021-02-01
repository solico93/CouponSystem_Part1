package facade;

import exceptions.CouponException;
import services.CompaniesDAO;
import services.CouponsDAO;
import services.CustomersDAO;

public abstract class ClientFacade {

	
	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email,String password) throws CouponException;
}
