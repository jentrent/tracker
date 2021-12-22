package com.jentrent.tracker.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.jentrent.tracker.model.Priority;

@Converter
public class PriorityConverter implements AttributeConverter<Priority, Integer>{

	public Integer convertToDatabaseColumn(Priority value){

		return value.decodeToInt();
	}

	public Priority convertToEntityAttribute(Integer value){

		return Priority.getValue(value);
	}

}
