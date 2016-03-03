package com.novust.shared.dao;

import com.novust.shared.data.VerbData;
import org.springframework.stereotype.Component;

@Component
public class VerbDataDao extends BaseDataDao<VerbData> {
    @Override
    public Class<VerbData> getDataType() {
        return VerbData.class;
    }
}
