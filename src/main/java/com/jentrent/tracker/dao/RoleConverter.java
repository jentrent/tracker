package com.jentrent.tracker.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.jentrent.tracker.model.Role;

@Converter
public class RoleConverter implements AttributeConverter<Role, Integer>{

	public Integer convertToDatabaseColumn(Role value){

		return value.decodeToInt();
	}

	public Role convertToEntityAttribute(Integer value){

		return Role.getValue(value);
	}

}
