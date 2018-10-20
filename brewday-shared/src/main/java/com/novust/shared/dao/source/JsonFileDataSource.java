package com.novust.shared.dao.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.novust.shared.ObjectMapperSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

    /**
     * Use this when the path to a given resource is expected to be relative to some parent resource.
     *
     * Useful for when that path was ready FROM that parent resource.
\     */
    public List loadData(Class objectClass, String dataInputPath, Resource parentResource) {
        ObjectMapper mapper = objectMapperSource.getObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            Resource resourceRelative = parentResource.createRelative(dataInputPath);
            try(InputStream inputStream = resourceRelative.getInputStream()) {
                List data = mapper.readValue(inputStream, collectionType);
                return data;
            }
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

    InputStream getDefaultInputStream(String dataPath) throws IOException, URISyntaxException {
        Resource dataPathResource = pathToResource(dataPath);
        System.out.println("CURRENT DIRECTORY : " + System.getProperty("user.dir"));
        InputStream resourceAsStream = dataPathResource.getInputStream();
        if(resourceAsStream == null) {
            throw new IOException("Invalid dataPath " + dataPath);
        }
        return resourceAsStream;
    }

     public Resource pathToResource(String filePath) throws URISyntaxException {
         URI uri = new URI(filePath);
         if(StringUtils.isBlank(uri.getScheme())) {
             // If not based a FQP, it's relative to current working dir.
             filePath = "file:" + (filePath.startsWith("/") ? filePath : "./" + filePath);
         }
         DefaultResourceLoader resourceLoader = new DefaultResourceLoader(getClass().getClassLoader());
         return resourceLoader.getResource(filePath);
    }

}
