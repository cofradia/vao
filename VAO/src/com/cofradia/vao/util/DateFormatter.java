package com.cofradia.vao.util;

public class DateFormatter {

	public String getTimeStamp(String date, String time){
		String formattedDate = "";
		String[] dateArray = date.split("/");
		formattedDate = dateArray[2]+"-"+dateArray[1]+"-"+dateArray[0];
		if (time!= null && !time.isEmpty()){
			//TODO: Add T and time
			formattedDate += "T" + time;
		}
		//Ok
		return formattedDate;
	}
}
