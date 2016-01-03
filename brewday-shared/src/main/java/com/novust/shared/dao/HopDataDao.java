package com.novust.shared.dao;

import com.novust.shared.data.HopData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HopDataDao extends BaseDataDao<HopData> {
    @Override
    public Class getDataType() {
        return HopData.class;
    }

    public boolean exists(String id) {
        List<HopData> allData = getAllData();
        return allData.stream().anyMatch(h -> (StringUtils.equals(h.id, id)));
    }

}
