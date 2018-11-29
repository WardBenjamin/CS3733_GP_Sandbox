package com.cs3733.vindemiatrix.blward.sandbox.http;

public class ScheduleRequest {
	public final String startDate;
	public final String endDate;
	public final String startTime;
	public final String endTime;
	public final int slotMinutes;
	
	public ScheduleRequest (String startDate, String endDate, String startTime, String endTime, int slotMinutes) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.slotMinutes = slotMinutes;
	}
	
	public String toString() {
		return "Schedule(" + startDate + "," + endDate + "," + startTime + "," + endTime + "," + slotMinutes + ")";
	}
}
