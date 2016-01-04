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
        return loadData(objectClass, getDataInputPath(objectClass));
    }

    public List loadData(Class objectClass, String dataInputPath) {
        ObjectMapper mapper = objectMapperSource.getObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            List data = mapper.readValue(getDefaultInputStream(dataInputPath), collectionType);
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

    String getDataInputPath(Class dataClass) {
        String name = dataClass.getName();
        return dataPathMap.containsKey(name) ? dataPathMap.get(name) : null;
    }

    InputStream getDefaultInputStream(String dataPath) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(dataPath);
        if(resourceAsStream == null) {
            throw new IOException("Invalid dataPath " + dataPath);
        }
        return resourceAsStream;
    }
}
