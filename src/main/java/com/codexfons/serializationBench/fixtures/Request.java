package com.codexfons.serializationBench.fixtures;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
    public String query;
    public long offset;
    public long count;
    public String group;
    public Map<String, Attribute> selectedFilters = new HashMap<>();
}
