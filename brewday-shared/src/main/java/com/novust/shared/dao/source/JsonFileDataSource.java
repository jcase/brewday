package com.novust.shared.dao.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.novust.shared.ObjectMapperSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class JsonFileDataSource implements AppDataSource {
    static Logger logger = LoggerFactory.getLogger(JsonFileDataSource.class);

    @Autowired
    ObjectMapperSource objectMapperSource;

    Map<String, String> dataPathMap = newHashMap();


    public void setDataPathMap(Map<String, String> dataPathMap) {
        this.dataPathMap = dataPathMap;
    }

    @Override
    public boolean handlesClass(Class dataClass) {
        String name = dataClass.getName();
        return dataPathMap.containsKey(name);
    }

    @Override
    public List loadData(Class objectClass) {
        ObjectMapper mapper = objectMapperSource.getObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            List data = mapper.readValue(getDataInputStream(objectClass), collectionType);
            return data;
        } catch (IOException e) {
            logger.error("Could not read data", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void addData(Object data) {
        logger.warn("DataSource does not support adding data for data type {} !", data.getClass().getName());
    }

    InputStream getDataInputStream(Class dataClass) {
        String name = dataClass.getName();
        if(dataPathMap.containsKey(name)) {
            return getDefaultInputStream(dataPathMap.get(name));
        }
        logger.error("Could not find any dataInputStream for dataClass {}", dataClass);
        return null;
    }

    InputStream getDefaultInputStream(String dataPath) {
        InputStream resourceAsStream = getClass().getResourceAsStream(dataPath);
        return resourceAsStream;
    }
}
