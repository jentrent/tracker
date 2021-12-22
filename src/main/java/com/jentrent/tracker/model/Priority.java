package com.jentrent.tracker.model;

public enum Priority{

	LOW, MEDIUM, HIGH, CRITICAL;

	public String decodeToString(){

		switch (this){
			case LOW:
				return "Low";
			case MEDIUM:
				return "Medium";
			case HIGH:
				return "High";
			case CRITICAL:
				return "High";
			default:
				return null;
		}

	}

	public Integer decodeToInt(){

		switch (this){
			case LOW:
				return 1;
			case MEDIUM:
				return 2;
			case HIGH:
				return 3;
			case CRITICAL:
				return 4;
			default:
				return null;
		}

	}

	public static Priority getValue(Integer i){

		switch (i){
			case 1:
				return LOW;
			case 2:
				return MEDIUM;
			case 3:
				return HIGH;
			case 4:
				return CRITICAL;
			default:
				throw new IllegalArgumentException("Value " + i + " is an invalid Priority");
		}

	}

}
