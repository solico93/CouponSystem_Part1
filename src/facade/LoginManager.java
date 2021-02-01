package facade;

import coupons_system.ClientType;
import exceptions.CouponException;

public class LoginManager {
	private static LoginManager instance;
	
	private LoginManager() { }
	
	public static LoginManager getInstance() {
		if(instance==null)
			instance=new LoginManager();
		return instance;
	}
	public ClientFacade login(String email,String password,ClientType clientType) throws CouponException {
		ClientFacade clientFacade=null;
		switch(clientType) {
		case Administrator:
			clientFacade=new AdminFacade();
			break;
		case Company:
			clientFacade=new CompanyFacade();
			break;
		case Customer:
			clientFacade=new CustomerFacade();
			break;
		}
		try {
		if(clientFacade!=null) {
			try {
			if (clientFacade.login(email, password)) {
				System.out.println("Login Successed!");
				return clientFacade;
			}
			} catch(Exception e) {
				System.err.println("STOP! Login Falied! Invalid User or Password!");
				return null;
			}
		}
		} catch(Exception e) {
			System.err.println("STOP! Login Falied! Invalid User Type!");	
			return null;
		}
		return null;
	}

}


