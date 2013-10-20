/***************************************************************
Filename: UnixTime.java
Author: Christian Heckendorf
Created Date: 9/28/13
Purpose: Gets the system time as a Unix timestamp
Features: All session protected features
***************************************************************/
package edu.cs673.plm;

public class UnixTime {
	public static final long MINUTE = 60;
	public static final long HOUR = MINUTE*60;
	public static final long DAY = HOUR*24;

	/***************************************************************
	Function name: currentTime
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Gets the system time as a Unix timestamp
	***************************************************************/
	public static long currentTime(){
		return (long) (System.currentTimeMillis() / 1000L);
	}
}
