package com.novust.shared.dao;

import com.novust.shared.data.YeastData;
import org.springframework.stereotype.Component;

@Component
public class YeastDataDao extends BaseDataDao<YeastData> {
    @Override
    public Class<YeastData> getDataType() {
        return YeastData.class;
    }
}
