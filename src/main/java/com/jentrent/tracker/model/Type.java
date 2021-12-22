package com.jentrent.tracker.model;

import java.util.ArrayList;
import java.util.List;

public enum Type{

	BUG, ENHANCEMENT, DOCUMENTATION, SUPPORT;

	public static final List<Type> TYPES;

	static{
		TYPES = new ArrayList<Type>();
		TYPES.add(BUG);
		TYPES.add(ENHANCEMENT);
		TYPES.add(DOCUMENTATION);
		TYPES.add(SUPPORT);
	}

	public String decodeToString(){

		switch (this){
			case BUG:
				return "Bug";
			case ENHANCEMENT:
				return "Enhancement";
			case DOCUMENTATION:
				return "Documentation";
			case SUPPORT:
				return "Support";
			default:
				return null;
		}

	}

	public Integer decodeToInt(){

		switch (this){
			case BUG:
				return 1;
			case ENHANCEMENT:
				return 2;
			case DOCUMENTATION:
				return 3;
			case SUPPORT:
				return 4;
			default:
				return null;
		}

	}

	public static Type getValue(Integer i){

		switch (i){
			case 1:
				return BUG;
			case 2:
				return ENHANCEMENT;
			case 3:
				return DOCUMENTATION;
			case 4:
				return SUPPORT;
			default:
				throw new IllegalArgumentException("Value " + i + " is an invalid Type");
		}

	}

}
