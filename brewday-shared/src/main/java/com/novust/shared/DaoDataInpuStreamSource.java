package com.novust.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

// Not a component; built via spring.xml
public class DaoDataInpuStreamSource {
    static Logger logger = LoggerFactory.getLogger(DaoDataInpuStreamSource.class);

    Map<String, String> dataPathMap = newHashMap();

    public void setDataPathMap(Map<String, String> dataPathMap) {
        this.dataPathMap = dataPathMap;
    }

    public InputStream getDataInputStream(Class dataClass) {
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
