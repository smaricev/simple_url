package com.marichely.simpleurl.dao;

import com.marichely.simpleurl.exception.InvalidResultSetSizeException;

import java.util.List;

public abstract class JooqRepository {
    protected  <T> T fetchFromResultList(String searchElement, List<T> result) throws Exception {
        if(result.size()>1){
            throw new Exception("Database returned more than 1 record for "+ searchElement );
        }
        if(result.isEmpty()){
            throw new InvalidResultSetSizeException("No records found for "+ searchElement);
        }
        return result.get(0);
    }
}
