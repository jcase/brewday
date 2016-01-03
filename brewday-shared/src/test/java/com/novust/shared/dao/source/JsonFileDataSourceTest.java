package com.novust.shared.dao.source;

import com.novust.shared.ObjectMapperSource;
import com.novust.shared.data.HopData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class JsonFileDataSourceTest {
    public static final String testData = "/data/hopData.json";

    @InjectMocks
    JsonFileDataSource jsonFileDataSource;

    @Before
    public void setUp() throws Exception {
        jsonFileDataSource.objectMapperSource = new ObjectMapperSource();
        Map<String, String> dataMap = newHashMap();
        dataMap.put(HopData.class.getName(), testData);
        jsonFileDataSource.setDataPathMap(dataMap);
    }

    @Test
    public void testLoadData() throws Exception {
        List<HopData> hopDatas = jsonFileDataSource.loadData(HopData.class);
        assertNotNull(hopDatas);
        assertEquals(3, hopDatas.size());
        assertEquals("cascade", hopDatas.get(0).name);
        assertEquals("fuggle", hopDatas.get(1).name);
        assertEquals("citra", hopDatas.get(2).name);
    }

    InputStream getDefaultInputStream() {
        InputStream resourceAsStream = getClass().getResourceAsStream(testData);
        return resourceAsStream;
    }

}