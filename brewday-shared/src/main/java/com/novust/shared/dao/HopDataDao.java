package com.novust.shared.dao;

import com.novust.shared.data.HopData;
import org.springframework.stereotype.Component;

@Component
public class HopDataDao extends BaseDataDao<HopData> {
    @Override
    public Class getDataType() {
        return HopData.class;
    }
}
