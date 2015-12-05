package com.novust.shared.dao;

import java.util.List;

public interface DataDao<T> {
    Class<T> getDataType();
    List<T> getAllData();
}
