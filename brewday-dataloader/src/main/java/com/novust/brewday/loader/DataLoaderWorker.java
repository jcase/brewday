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
        List<LoaderData> data = jsonFileDataSource.loadData(LoaderData.class, loaderFile);
        // TODO loadData will return a tuple saying if resources are a file or classpath resource.
        // TODO let handleLoaderDataEntry know which
        data.stream().forEach(d -> handleLoaderDataEntry(d));
    }

    void handleLoaderDataEntry(LoaderData loaderData)  {
        // TODO we'll pass in whether or not we're using classpath or file resources. Use that information
        // TODO when loading the resources. (if scheme is specified, use that, otherwise, use the specified scheme)
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
