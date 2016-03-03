package com.novust.shared.dao;

import com.novust.shared.data.AppData;

import java.util.List;

public interface DataDao<T extends AppData> {
    Class<T> getDataType();
    List<T> getAllData();
    T getById(String id);
}
