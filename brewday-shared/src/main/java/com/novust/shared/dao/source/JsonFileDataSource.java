package com.novust.shared.dao.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.novust.shared.ObjectMapperSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class JsonFileDataSource implements AppDataSource {
    static Logger logger = LoggerFactory.getLogger(JsonFileDataSource.class);

    @Autowired
    ObjectMapperSource objectMapperSource;

    Map<String, String> dataPathMap = newHashMap(); // contents are populated via a spring config file


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

    // TODO return more info in a helper Tuple like object
    // TODO First, the data; second, whether or not it was a classpath resource or a filepath resource
    public List loadData(Class objectClass, String dataInputPath) {
        ObjectMapper mapper = objectMapperSource.getObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            List data = mapper.readValue(getDefaultInputStream(dataInputPath), collectionType);
            return data;
        } catch (URISyntaxException | IOException e) {
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

    InputStream getDefaultInputStream(String dataPath) throws IOException, URISyntaxException {
        Resource dataPathResource = pathToResource(dataPath);
        InputStream resourceAsStream = dataPathResource.getInputStream();
        if(resourceAsStream == null) {
            throw new IOException("Invalid dataPath " + dataPath);
        }
        return resourceAsStream;
    }

     Resource pathToResource(String filePath) throws URISyntaxException {
         URI uri = new URI(filePath);
         if(StringUtils.isBlank(uri.getScheme())) {
             filePath = "file:" + (filePath.startsWith("/") ? filePath : "/" + filePath);
         }
         DefaultResourceLoader resourceLoader = new DefaultResourceLoader(getClass().getClassLoader());
         return resourceLoader.getResource(filePath);
    }

}
