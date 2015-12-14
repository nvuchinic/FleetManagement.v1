package helpers;

import models.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class NotificationHelper {

	public static void checkDates(){
		RenewalNotification rn=null;
		System.out.println("///////////////////PRINTING NUMBER OF REGISTRATIONS:"+VehicleRegistration.find.all().size());
		if(VehicleRegistration.find.all().size()>0){
		for(VehicleRegistration vr:VehicleRegistration.find.all()){
			if(vr.checked==false){
			if(isDateNear(vr.expirationDate)){
				if(rn==null){
					rn=new RenewalNotification();
				}
				rn.registrations.add(vr);
				vr.checked=true;
				vr.notification=rn;
				vr.save();
				//rn.size++;
				rn.save();
			}
			}
			}
		}
		
		if(Insurance.find.all().size()>0){
		for(Insurance ins:Insurance.find.all()){
			if(ins.checked==false){
			if(isDateNear(ins.expirationDate)){
				if(rn==null){
					rn=new RenewalNotification();
				}
				rn.insurances.add(ins);
				ins.notification=rn;
				ins.save();
				//rn.size++;
				rn.save();
			}
			}
			}
		}
		
	}

	
	private static boolean isDateNear(Date expirationDate) {
		java.util.Date nowDate = new java.util.Date();
		java.util.Date expiryJavaDate = new java.util.Date(expirationDate.getTime());
		int thresholdToDays=thresholdToDays();
		System.out.println("PRINTING THRESHOLD IN DAYS:"+thresholdToDays);
		long expiryDateAndNowDateDifference=subtractDates(expiryJavaDate, nowDate);
		System.out.println("PRINTING DIFFERENCE BEETWEN EXPIRY DATE AND NOW DATE:"+expiryDateAndNowDateDifference);
		if(expiryDateAndNowDateDifference<=thresholdToDays){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static long subtractDates(java.util.Date expiryDate, java.util.Date nowDate) {
		long result=0;
		long diff = expiryDate.getTime() - nowDate.getTime();
		result=diff / (1000 * 60 * 60 * 24);
		System.out.println("PRINTING DIFFERENCE BETWEEN EXPIRY DATE AND NOW:"+diff);
		return result;
	}
	
	private static int thresholdToDays() {
		int thresholdToDays=0;
		String dayUnit="day(s)", weekUnit="week(s)", monthUnit="month(s)";
		NotificationSettings ns=NotificationSettings.getInstance();
		int timeScope=ns.threshold;
		String timeUnit=ns.timeUnit;
		if(timeUnit.equalsIgnoreCase(dayUnit))
		{
			thresholdToDays=timeScope;
		}
		if(timeUnit.equalsIgnoreCase(weekUnit)){
			thresholdToDays=7*timeScope;
		}
		if(timeUnit.equalsIgnoreCase(monthUnit)){
			thresholdToDays=30*timeScope;
		}
		return thresholdToDays;
	}
}
