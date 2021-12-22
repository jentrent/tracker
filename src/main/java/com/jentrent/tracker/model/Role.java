package com.jentrent.tracker.model;

import java.util.ArrayList;
import java.util.List;

public enum Role{

	TRKADMIN, ANALYST, DEVELOPER, TESTER, SYSADMIN, PM;

	public static final List<Role> ROLES;

	static{
		ROLES = new ArrayList<Role>();
		ROLES.add(TRKADMIN);
		ROLES.add(ANALYST);
		ROLES.add(DEVELOPER);
		ROLES.add(TESTER);
		ROLES.add(SYSADMIN);
		ROLES.add(PM);
	}

	public String decodeToString(){

		switch (this){
			case TRKADMIN:
				return "TrackerAdmin";
			case ANALYST:
				return "Analyst";
			case DEVELOPER:
				return "Developer";
			case TESTER:
				return "Tester";
			case SYSADMIN:
				return "SysAdmin";
			case PM:
				return "PM";
			default:
				return null;
		}

	}

	public Integer decodeToInt(){

		switch (this){
			case TRKADMIN:
				return 1;
			case ANALYST:
				return 2;
			case DEVELOPER:
				return 3;
			case TESTER:
				return 4;
			case SYSADMIN:
				return 5;
			case PM:
				return 6;
			default:
				return null;
		}

	}

	public static Role getValue(Integer i){

		switch (i){
			case 1:
				return TRKADMIN;
			case 2:
				return ANALYST;
			case 3:
				return DEVELOPER;
			case 4:
				return TESTER;
			case 5:
				return SYSADMIN;
			case 6:
				return PM;
			default:
				throw new IllegalArgumentException("Value " + i + " is an invalid Role");
		}

	}

}
