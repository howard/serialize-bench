package com.codexfons.serializationBench.fixtures;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class Item implements Serializable {
    public String id;
    public String name;
    public URL url;
    public double price;
    public Currency currency;
    public double taxRate;
    public SortedMap<String, String> orderNumbers = new TreeMap<>();
    public Map<String, Attribute> attributes = new HashMap<>();
    public Map<String, String> properties = new HashMap<>();
    public double relevance;
}
