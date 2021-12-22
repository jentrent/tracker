package com.jentrent.tracker.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.jentrent.tracker.model.Type;

@Converter
public class TypeConverter implements AttributeConverter<Type, Integer>{

	public Integer convertToDatabaseColumn(Type value){

		return value.decodeToInt();
	}

	public Type convertToEntityAttribute(Integer value){

		return Type.getValue(value);
	}

}
