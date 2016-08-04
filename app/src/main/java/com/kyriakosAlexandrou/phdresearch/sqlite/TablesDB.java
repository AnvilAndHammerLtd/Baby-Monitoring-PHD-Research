package com.kyriakosAlexandrou.phdresearch.sqlite;

public class TablesDB {
	private final String TABLE_MAIN = "MAIN";
	private final String time = "time";
	private final String isRunning = "is_timer_running";
	
	//getters and setters
	//USER
	public String getTABLE_MAIN() {
		return TABLE_MAIN;
	}
	public String getTime() {
		return time;
	}
	public String getIsRunning() {
		return isRunning;
	}
}