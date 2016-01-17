package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import models.*;


public class ServiceNotificationHelper {
	public static void checkForServiceNotifications(){
		System.out.println("///////////////////////THIS IS THREAD GOING THROUGH SERVICE NOTIFICATION HELPER!!!");
		Date nextServiceDate=null;
		boolean isServiceDateNear=false;
		boolean isServiceMileageClose=false;
		int nextServiceMileage=0;
		System.out.println("///////////////////////PRINTING NUMBER OF SERVICE NOTIFICATIONS:"+ ServiceNotification.find.all().size() );

		if(ServiceNotificationSettings.find.all().size()>0){
		for(ServiceNotificationSettings sns : ServiceNotificationSettings.find.all() ){
			for(Vehicle sns_vhcl:sns.vehicles){
				if(sns.timeIntervalSize!=0){
					nextServiceDate=getNextServiceDate(sns, sns_vhcl);
					isServiceDateNear=isDateNear(sns, nextServiceDate);
				}
				if(sns.meterIntervalSize!=0){
					nextServiceMileage=getNextServiceMileage(sns, sns_vhcl);
					isServiceMileageClose=isServiceMileageClose(sns, sns_vhcl, nextServiceMileage);
				}
				if(sns.timeIntervalSize!=0 && sns.meterIntervalSize==0){
					if(isServiceDateNear==true){
						if(ServiceNotification.alreadyExists(sns_vhcl, sns.service)==false){
						ServiceNotification sn= ServiceNotification.saveToDB();
						sn.vehicle=sns_vhcl;
						sn.serviceForSN=sns.service;
						sn.nextServiceDate=nextServiceDate;
						sn.save();
					}}
											}
				if(sns.timeIntervalSize==0 && sns.meterIntervalSize!=0){
					if(isServiceMileageClose==true){
						if(ServiceNotification.alreadyExists(sns_vhcl, sns.service)==false){
					ServiceNotification sn=ServiceNotification.saveToDB();
					sn.vehicle=sns_vhcl;
					sn.serviceForSN=sns.service;
					sn.nextServiceMileage=nextServiceMileage;
					sn.save();
				}}
				}
				if(sns.timeIntervalSize!=0 && sns.meterIntervalSize!=0){
					if(ServiceNotification.alreadyExists(sns_vhcl, sns.service)==false){
						if(isServiceDateNear==true){
							ServiceNotification sn=ServiceNotification.saveToDB();
							sn.vehicle=sns_vhcl;
							sn.serviceForSN=sns.service;
							sn.nextServiceDate=nextServiceDate;
							sn.save();
						}
					}
					if(ServiceNotification.alreadyExists(sns_vhcl,sns.service)){
						if(isServiceMileageClose==true){
							ServiceNotification sn=ServiceNotification.saveToDB();
							sn.vehicle=sns_vhcl;
							sn.serviceForSN=sns.service;
							sn.nextServiceMileage=nextServiceMileage;
							sn.save();
						}
					}
				}
				
			}
			}
		}
					}

	
	private static long subtractDates(java.util.Date nextServiceDate, java.util.Date nowDate) {
		long result=0;
		long diff = nextServiceDate.getTime() - nowDate.getTime();
		result=diff / (1000 * 60 * 60 * 24);
		System.out.println("PRINTING DIFFERENCE BETWEEN EXPIRY DATE AND NOW:"+result);
		return result;
	}
	
	private static int thresholdToDays(ServiceNotificationSettings sns) {
		int thresholdToDays=0, week=7, month=30;
		String dayUnit="day(s)", weekUnit="week(s)", monthUnit="month(s)";
		int timeScope=sns.timeIntervalSize;
		String timeUnit=sns.timeIntervalUnit;
		if(timeUnit.equalsIgnoreCase(dayUnit))
		{
			thresholdToDays=timeScope;
		}
		if(timeUnit.equalsIgnoreCase(weekUnit)){
			thresholdToDays=week*timeScope;
		}
		if(timeUnit.equalsIgnoreCase(monthUnit)){
			thresholdToDays=month*timeScope;
		}
		return thresholdToDays;
	}
	
	
	public static boolean isDateNear(ServiceNotificationSettings sns, Date nextServiceDate) {
		java.util.Date nowDate = new java.util.Date();
		java.util.Date nextServiceDateJavaDate = new java.util.Date(nextServiceDate.getTime());
		int thresholdToDays=thresholdToDays(sns);
		System.out.println("PRINTING THRESHOLD IN DAYS:"+thresholdToDays);
		long expiryDateAndNowDateDifference=subtractDates(nextServiceDateJavaDate, nowDate);
		System.out.println("PRINTING DIFFERENCE BEETWEN EXPIRY DATE AND NOW DATE:"+expiryDateAndNowDateDifference);
		if(expiryDateAndNowDateDifference<=thresholdToDays){
			return true;
		}
		else{
			return false;
		}
	}
	
	
//	private static Date getNextServiceDate(ServiceNotificationSettings sns, Vehicle v) {
//		String date = "1950-01-01";
//	    java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
//
//		Calendar cal = Calendar.getInstance();
//	    cal.set( Calendar.YEAR, 1950 );
//	    cal.set( Calendar.MONTH, Calendar.JANUARY );
//	    cal.set( Calendar.DATE, 1 );
//		Date nextServiceDate=null, lastServiceDate=null;
//		final Date DEFAULT_DATE=java.sql.Date.valueOf(date);
//		
//		lastServiceDate=DEFAULT_DATE;
//		for(Maintenance mn:v.maintenances){
//			for(Service mn_srv:mn.services){
//				if(mn_srv.stype.equalsIgnoreCase(sns.service.stype)){
//					if(mn.mDate.after(lastServiceDate)){
//						lastServiceDate=mn.mDate;
//					}
//				}
//			}
//		}
//		if(lastServiceDate.compareTo(DEFAULT_DATE)==0){
//			lastServiceDate=new java.sql.Date(Calendar.getInstance().getTime().getTime());
//			nextServiceDate=calculateNextServiceDate(sns, lastServiceDate);
//		}else{
//			nextServiceDate=calculateNextServiceDate(sns, lastServiceDate);
//		}
//		return nextServiceDate;
//	}
	
	
	private static Date getNextServiceDate(ServiceNotificationSettings sns, Vehicle v) {
		Date nextServiceDate=null;
		Date settingsDate=sns.snsDate;
		nextServiceDate=calculateNextServiceDate(sns, settingsDate);
		return nextServiceDate;
	}
	
	
	private static Date calculateNextServiceDate(ServiceNotificationSettings sns, Date settingsDate){
		String timeUnitDay="day(s)";
		String timeUnitWeek="week(s)";
		String timeUnitMonth="month(s)";
		Date nextServiceDate=null;
		java.util.Calendar nextServiceDateCal = Calendar.getInstance();
		nextServiceDateCal.setTime(settingsDate);
		if(sns.timeIntervalUnit.equalsIgnoreCase(timeUnitDay)){
			nextServiceDateCal.add(Calendar.DAY_OF_MONTH, sns.timeIntervalSize);
		}
		if(sns.timeIntervalUnit.equalsIgnoreCase(timeUnitWeek)){
			nextServiceDateCal.add(Calendar.WEEK_OF_MONTH, sns.timeIntervalSize);
		}
		if(sns.timeIntervalUnit.equalsIgnoreCase(timeUnitMonth)){
			nextServiceDateCal.add(Calendar.MONTH, sns.timeIntervalSize);
		}
		nextServiceDate=new java.sql.Date(nextServiceDateCal.getTime().getTime());
		return nextServiceDate;
	}
	
	
//	private static int getNextServiceMileage(ServiceNotificationSettings sns, Vehicle v){
//		int nextServiceMileage=0, lastServiceMileage=0;
//		for(Maintenance mn:v.maintenances){
//			for(Service mnSrv:mn.services){
//				if(mnSrv.stype.equalsIgnoreCase(sns.service.stype)){
//					if(mn.odometer>lastServiceMileage){
//						lastServiceMileage=mn.odometer;
//					}
//				}
//			}
//		}
//		if(lastServiceMileage==0){
//		lastServiceMileage=v.odometer;
//		}
//		nextServiceMileage=calculateNextServiceMileage(sns, lastServiceMileage);
//		return nextServiceMileage;
//	}
//	
	
	private static int getNextServiceMileage(ServiceNotificationSettings sns, Vehicle v){
		int nextServiceMileage=0, settingsMileage=0;
		for(VehicleServiceNotificationSettingsMileage snsMileage:sns.snsMileages){
			if(snsMileage.vid==v.id){
				settingsMileage=snsMileage.mileage;
			}
		}
		nextServiceMileage=calculateNextServiceMileage(sns, settingsMileage);
		return nextServiceMileage;
	}
	
	
	public static int calculateNextServiceMileage(ServiceNotificationSettings sns, int lastServiceMileage){
		return lastServiceMileage+sns.meterIntervalSize;
	}
	
	
	public static boolean isServiceMileageClose(ServiceNotificationSettings sns, Vehicle v, int nextServiceMileage){
		int nowMileage=v.odometer;
		int threshold=sns.meterThresholdSize;
		if(nextServiceMileage<=(nowMileage+threshold)){
			return true;
		}else{
			return false;
		}
	}
}
