package com.comcast.crm.generic.webdriverutility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {

	public int getRandomNumber() {
		Random random = new Random(); //By creating object of Random class
		int randomNumber = random.nextInt(5000);          //sets thet boundary here
		return randomNumber;
		
	}
	
	public String getSystemDateYYYYMMDD()
	{
		Date dateObj = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String date = sdf.format(dateObj);
		return date;
	}
	
	
	public String getRequiredDateYYYYMMDD(int days)
	{


		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd"); // here we can get the specific format of date we
																	// want
		// for next 30 DAYS  from today(using calender class)
		Calendar cal = sim.getCalendar();

		cal.add(Calendar.DAY_OF_MONTH, 30); // after30 DAYS
		String reqDate = sim.format(cal.getTime());
		return reqDate;
	}
	
}
