package com.jentrent.tracker.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.jentrent.tracker.model.Status;

@Converter
public class StatusConverter implements AttributeConverter<Status, Integer>{

	public Integer convertToDatabaseColumn(Status value){

		return value.decodeToInt();
	}

	public Status convertToEntityAttribute(Integer value){

		return Status.getValue(value);
	}

}
