package com.novust.shared.dao.source;

import java.util.List;

public interface AppDataSource {
    boolean handlesClass(Class dataClass);
    List loadData(Class objectClass);
    void addData(Object data);
}
