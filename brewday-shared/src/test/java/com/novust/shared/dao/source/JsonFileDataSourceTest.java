package com.novust.shared.dao.source;

import com.novust.shared.ObjectMapperSource;
import com.novust.shared.data.HopData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class JsonFileDataSourceTest {
    static final String dataClasspath = "classpath:/data/hopData.json";

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @InjectMocks
    JsonFileDataSource jsonFileDataSource;

    @Before
    public void setUp() {
        jsonFileDataSource.objectMapperSource = new ObjectMapperSource();
    }

    @Test
    public void testLoadDataFromClasspath() {
        Map<String, String> dataMap = newHashMap();
        dataMap.put(HopData.class.getName(), dataClasspath);
        jsonFileDataSource.setDataPathMap(dataMap);

        List<HopData> hopDatas = jsonFileDataSource.loadData(HopData.class);
        assertNotNull(hopDatas);
        assertEquals(3, hopDatas.size());
        assertEquals("cascade", hopDatas.get(0).name);
        assertEquals("fuggle", hopDatas.get(1).name);
        assertEquals("citra", hopDatas.get(2).name);
    }

    @Test
    public void testLoadDataFromAbsolutePath() throws IOException {
        // Get our classpath resource packaged with this test and put it somewhere on the filesystem to be sure
        // we can find it.
        File file = folder.newFile("testLoadDataFromAbsolutePath-data.json");
        Resource resource = new DefaultResourceLoader(getClass().getClassLoader()).getResource(dataClasspath);
        InputStream inputStream = resource.getInputStream();
        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();

        Map<String, String> dataMap = newHashMap();
        dataMap.put(HopData.class.getName(), file.getAbsolutePath());
        jsonFileDataSource.setDataPathMap(dataMap);

        List<HopData> hopDatas = jsonFileDataSource.loadData(HopData.class);
        assertNotNull(hopDatas);
        assertEquals(3, hopDatas.size());
        assertEquals("cascade", hopDatas.get(0).name);
        assertEquals("fuggle", hopDatas.get(1).name);
        assertEquals("citra", hopDatas.get(2).name);
    }
}