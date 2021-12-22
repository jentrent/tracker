package com.jentrent.tracker.service;

import java.util.ArrayList;
import java.util.List;

public class TrackerException extends Exception{

	private List<String> errors;

	public TrackerException(String message){

		super(message);
		errors = new ArrayList<String>();
		addError(message);

	}

	public TrackerException(){

		errors = new ArrayList<String>();
	}

	public void addError(String error){

		errors.add(error);
	}

	public Boolean hasErrors(){

		return errors.size() > 0;
	}

	public List<String> getErrors(){

		return errors;
	}

}
