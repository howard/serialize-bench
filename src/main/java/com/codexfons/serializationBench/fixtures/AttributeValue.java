package com.codexfons.serializationBench.fixtures;

import java.io.Serializable;

public class AttributeValue implements Serializable {
    public String value;
    public long frequency;
    public double relevance;
    public boolean isRelativeFrequency;
}
