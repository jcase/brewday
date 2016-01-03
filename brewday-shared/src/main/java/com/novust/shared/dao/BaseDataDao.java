package com.novust.shared.dao;

import com.novust.shared.dao.source.AppDataSource;
import com.novust.shared.dao.source.AppDataSourceFactory;
import com.novust.shared.data.AppData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class BaseDataDao<T extends AppData> implements DataDao<T> {
    static Logger logger = LoggerFactory.getLogger(BaseDataDao.class);

    AppDataSource appDataSource;
    List<T> daoData;

    @Autowired
    AppDataSourceFactory appDataSourceFactory;

    @PostConstruct
    void postConstruct() {
        appDataSource = appDataSourceFactory.getAppDataSource(getDataType());
        if(appDataSource == null) {
            throw new IllegalStateException("Missing appDataSource for dataType " + getDataType().getName());
        }
        loadData();
    }

    protected void setData(List<T> data) {
        daoData = data;
    }
    public void addData(T data) {
        appDataSource.addData(data);
        daoData.add(data);
    }

    @Override
    public List<T> getAllData() {
        return daoData;
    }

    void loadData() {
        List data = appDataSource.loadData(getDataType());
        setData(data);
    }
}
