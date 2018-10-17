package com.novust.shared.dao;

import com.novust.shared.dao.source.AppDataSource;
import com.novust.shared.dao.source.AppDataSourceFactory;
import com.novust.shared.data.AppData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public abstract class BaseDataDao<T extends AppData> implements DataDao<T> {
    static Logger logger = LoggerFactory.getLogger(BaseDataDao.class);

    AppDataSource appDataSource;
    List<T> daoData;
    Map<String, T> keyToDataMap;

    @Autowired
    AppDataSourceFactory appDataSourceFactory;

    @PostConstruct
    void postConstruct() {
        keyToDataMap = newHashMap();

        appDataSource = appDataSourceFactory.getAppDataSource(getDataType());
        if(appDataSource == null) {
            logger.warn("Missing appDataSource for dataType {}", getDataType().getName());
            // throw new IllegalStateException("Missing appDataSource for dataType " + getDataType().getName());
            return;
        }
        loadData();
    }

    protected void setData(List<T> data) {
        daoData = data;
        data.stream().forEach(d -> keyToDataMap.put(d.getId(), d));
    }

    public void addData(T data) {
        appDataSource.addData(data);
        daoData.add(data);
    }

    @Override
    public List<T> getAllData() {
        return daoData;
    }

    @Override
    public T getById(String id) {
        return keyToDataMap.get(id);
    }

    void loadData() {
        List data = appDataSource.loadData(getDataType());
        setData(data);
    }
}
