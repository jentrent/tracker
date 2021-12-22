package com.jentrent.tracker.model;

import java.util.ArrayList;
import java.util.List;

public enum Status{

	NEW, DEVELOPMENT, TESTING, VERIFIED, CLOSED;

	public static final List<Status> STATUSES;

	static{
		STATUSES = new ArrayList<Status>();
		STATUSES.add(NEW);
		STATUSES.add(DEVELOPMENT);
		STATUSES.add(TESTING);
		STATUSES.add(VERIFIED);
		STATUSES.add(CLOSED);
	}

	public String decodeToString(){

		switch (this){
			case NEW:
				return "New";
			case DEVELOPMENT:
				return "Development";
			case TESTING:
				return "Testing";
			case VERIFIED:
				return "Verified";
			case CLOSED:
				return "Closed";
			default:
				return null;
		}

	}

	public Integer decodeToInt(){

		switch (this){
			case NEW:
				return 1;
			case DEVELOPMENT:
				return 2;
			case TESTING:
				return 3;
			case VERIFIED:
				return 4;
			case CLOSED:
				return 5;
			default:
				return null;
		}

	}

	public static Status getValue(Integer i){

		switch (i){
			case 1:
				return NEW;
			case 2:
				return DEVELOPMENT;
			case 3:
				return TESTING;
			case 4:
				return VERIFIED;
			case 5:
				return CLOSED;
			default:
				throw new IllegalArgumentException("Value " + i + " is an invalid Status");
		}

	}

}
