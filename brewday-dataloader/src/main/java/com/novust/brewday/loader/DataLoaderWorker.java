package com.novust.brewday.loader;

import com.novust.shared.dao.source.JsonFileDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoaderWorker {
    public static final Logger logger = LoggerFactory.getLogger(DataLoaderWorker.class);

    @Autowired
    JsonFileDataSource jsonFileDataSource;

    @Autowired
    MongoTemplate mongoTemplate;

    public void loadData(String loaderFile) {
        List data = jsonFileDataSource.loadData(LoaderData.class, loaderFile);
        data.stream().forEach(d -> handleLoaderDataEntry((LoaderData) d));
    }

    void handleLoaderDataEntry(LoaderData loaderData)  {
        Class<?> dataClass = null;
        try {
            dataClass = Class.forName(loaderData.clazz);
            List list = jsonFileDataSource.loadData(dataClass, loaderData.file);
            list.stream().forEach(d -> placeDataInDatabase(d));
        } catch (ClassNotFoundException e) {
            logger.error("Could not find class for loaderData {}; skipping");
        }
    }

    void placeDataInDatabase(Object object) {
        // TODO: be sure we have the proper annotation on it?
        logger.info("Loading data for {}", object.toString());
        mongoTemplate.save(object);
    }

    public static class LoaderData {
        public String clazz;
        public String file;

        public LoaderData() {
        }

        @Override
        public String toString() {
            return "LoaderData{" +
                    "clazz='" + clazz + '\'' +
                    ", file='" + file + '\'' +
                    '}';
        }
    }
}
