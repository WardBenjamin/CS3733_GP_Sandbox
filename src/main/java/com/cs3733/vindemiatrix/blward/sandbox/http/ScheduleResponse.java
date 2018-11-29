package com.cs3733.vindemiatrix.blward.sandbox.http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleResponse {
	public final int id;
	
	public final int httpCode;
	public final String errorMessage;
	
	public final String startDate;
	public final String endDate;
	public final String startTime;
	public final String endTime;
	
	public final int days;
	public final int timeSlotsPerDay;
	
	public final TimeSlot[] timeSlots;
	
	public ScheduleResponse (int code, String msg) {
		this.id = 0;
		this.httpCode = code;
		errorMessage = msg;
		
		startDate = null;
		endDate = null;
		startTime = null;
		endTime = null;
		
		days = 0;
		timeSlotsPerDay = 0;
		
		timeSlots = new TimeSlot[0];
	}
	
	public ScheduleResponse(ScheduleRequest request) {
		this(request.startDate, request.endDate, request.startTime, request.endTime, request.slotMinutes);
	}
	
	public ScheduleResponse (String startDate, String endDate, String startTime, String endTime, int slotMinutes) {
		this.id = 1;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		
		boolean parseSucceeded = true;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date date1;
		Date date2;
		Date time1;
		Date time2;
		int days = 0;
		int numSlots = 0;
		
		try {
			date1 = dateFormat.parse(startDate);
			date2 = dateFormat.parse(endDate);
			time1 = timeFormat.parse(startTime);
			time2 = timeFormat.parse(endTime);
			
			long timeDifference = time2.getTime() - time1.getTime();
			long seconds = timeDifference / 1000;
			long minutes = seconds / 60;
			
			numSlots = (int)minutes / slotMinutes;
			
			long dateDifference = date2.getTime() - date1.getTime();
			// MS -> sec -> min -> hr -> day
			days = (int)(dateDifference / 1000 / 60 / 60 / 24);		
		} catch (ParseException e) {
			parseSucceeded = false;
			e.printStackTrace();
		}

		
		if(parseSucceeded) {
			this.httpCode = 200;
			this.errorMessage = "";
			
			this.days = days;
			this.timeSlotsPerDay = (int) numSlots;
			this.timeSlots = new TimeSlot[(int) (days * numSlots)];
			
			for(int i = 0; i < days; i++) {
				for(int j = 0; j < numSlots; j++) {
					timeSlots[i * days + j] = new TimeSlot();
				}
			}
		} else {
			this.httpCode = 422;
			this.errorMessage = "Failed to parse date or time";
			this.days = 0;
			this.timeSlotsPerDay = 0;
			this.timeSlots = new TimeSlot[0];
		}

	}
	
//	public String toString() {
//		return "Value(" + value + ")";
//	}
}
