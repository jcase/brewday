package com.novust.shared.dao.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AppDataSourceFactory {

    @Autowired
    List<AppDataSource> dataSourceList;

    public AppDataSource getAppDataSource(Class clazz) {
        AppDataSource val = null;
        if(dataSourceList != null) {
            Optional<AppDataSource> first = dataSourceList.stream().filter(dsl -> dsl.handlesClass(clazz)).findFirst();
            val = first.orElse(null);
        }
        return val;
    }

}
