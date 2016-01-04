package com.novust.shared.dao.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MongoDBDataSource implements AppDataSource {
    public static Logger logger = LoggerFactory.getLogger(MongoDBDataSource.class);

    List<String> mongoBackedData = newArrayList();

    @Autowired
    MongoTemplate mongoTemplate;

    public void setMongoBackedData(List<String> mongoBackedData) {
        this.mongoBackedData = mongoBackedData;
    }

    @Override
    public boolean handlesClass(Class dataClass) {
        return mongoBackedData.contains(dataClass.getName());
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
