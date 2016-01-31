package helpers;

import helpers.RequestNotificationSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import models.*;


public class RequestsNotificationHelper {
	public static long numberOfRequests=0;
	public static void checkForRequestsNotifications(){
		List<Requests> newRequests=new ArrayList<Requests>();
		System.out.println("///////////////////////THIS IS THREAD GOING THROUGH REQUESTS NOTIFICATION HELPER!!!");
		System.out.println("///////////////////////PRINTING NUMBER OF REQUESTS:"+ Requests.find.all().size() );
		//long lastRequestsId=Requests.getLastId();
		if(Requests.find.all().size()>0){
//			if(RequestNotificationSettings.isFirstPass==false){
//				for(Requests rs: Requests.find.all()){
//					if(rs.id>lastId){
//						RequestNotificationSettings.lastId=rs.id;
//						RequestNotificationSettings.isFirstPass=true;
//					}
//				}
//			}else{
//				lastId=RequestNotificationSettings.lastId;
//				for(Requests rs: Requests.find.all()){
//					if(rs.id>lastId){
//						RequestNotificationSettings.lastId=rs.id;
//						RequestsNotification rn=RequestsNotification.saveToDB();
//						rn.requestsList.add(rs);
//						rn.save();
//						rs.notification=rn;
//						rs.save();
//						//newRequests.add(rs);
//					}
//				}
//				if(newRequests.size()>0){
//					RequestsNotification rn=RequestsNotification.saveToDB();
////					for(Requests rq:newRequests){
////						rq.notification=rn;
////						rq.save();
////						//rn.requestsList.add(rq);
////						//rn.save();
////					}
//					rn.requestsList=newRequests;
//					rn.save();
//				}
				
			//}
			if(Requests.find.all().size()>numberOfRequests){
			RequestsNotification rn=RequestsNotification.saveToDB();
			for(Requests rs: Requests.find.all()){
				if(RequestsNotification.alreadyExists(rs)==false){
					
					rn.requestsList.add(rs);
					rn.save();
					rs.notification=rn;
					rs.save();
					//newRequests.add(rs);
					numberOfRequests=Requests.find.all().size();
				}
			}
			}
		}
	}
}
