package daily_job;

import dao.CouponsDBDAO;
import exceptions.CouponException;
import services.CouponsDAO;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDAO couponsDao;
	private boolean quit;
	private final long sleepTime;

	public CouponExpirationDailyJob() {
		couponsDao = new CouponsDBDAO();
		quit=true;
		sleepTime=1000*3600*24;;
	}
	@Override
	public void run() {
		while(quit) {
			try {
				couponsDao.deleteExpiredCoupon();
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			} catch (CouponException e) {
				//no need
			}
		}
	}
	public void stop() {
		this.quit = false;
	}
}
