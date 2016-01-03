package com.novust.shared.dao.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class MongoDBDataSource implements AppDataSource {
    public static Logger logger = LoggerFactory.getLogger(MongoDBDataSource.class);

    Map<String, String> dataTableMap = newHashMap();

    @Autowired
    MongoTemplate mongoTemplate;

    public void setDataTableMap(Map<String, String> dataTableMap) {
        this.dataTableMap = dataTableMap;
    }

    @Override
    public boolean handlesClass(Class dataClass) {
        return dataTableMap.containsKey(dataClass.getName());
    }

    @Override
    public List loadData(Class objectClass) {
        List all = mongoTemplate.findAll(objectClass);
        return all;
    }

    @Override
    public void addData(Object data) {
        logger.info("Adding data for class {} : {}", data.getClass().getName(), data.toString());
        mongoTemplate.save(data);
    }
}
