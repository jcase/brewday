package com.novust.shared.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.novust.shared.DaoDataInpuStreamSource;
import com.novust.shared.ObjectMapperSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class BaseDataDao<T> implements DataDao<T> {
    static Logger logger = LoggerFactory.getLogger(BaseDataDao.class);

    List<T> daoData;

    @Autowired
    DaoDataInpuStreamSource inputStreamSource;

    @Autowired
    ObjectMapperSource objectMapperSource;


    @PostConstruct
    void postConstruct() {
        loadData(inputStreamSource.getDataInputStream(getDataType()));
    }

    protected void setData(List<T> data) {
        daoData = data;
    }

    public void addData(T data) {
        daoData.add(data);
    }

    @Override
    public List<T> getAllData() {
        return daoData;
    }

    void loadData(InputStream dataInputStream) {
        ObjectMapper mapper = objectMapperSource.getObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, getDataType());
            List<T> data = mapper.readValue(dataInputStream, collectionType);
            setData(data);
        } catch (IOException e) {
            logger.error("Could not read datas", e);
            return;
        }
    }
}
