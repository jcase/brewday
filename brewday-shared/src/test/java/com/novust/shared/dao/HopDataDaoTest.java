package com.novust.shared.dao;

import com.novust.shared.ObjectMapperSource;
import com.novust.shared.data.HopData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by johncase on 9/28/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class HopDataDaoTest {
    public static final String testData = "/data/hopData.json";

    @InjectMocks
    HopDataDao hopDataDao;

    @Before
    public void setUp() throws Exception {
        hopDataDao.objectMapperSource = new ObjectMapperSource();

    }

    @Test
    public void testLoadData() throws Exception {
        hopDataDao.loadData(getDefaultInputStream());
        List<HopData> hopDatas = hopDataDao.getAllData();
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