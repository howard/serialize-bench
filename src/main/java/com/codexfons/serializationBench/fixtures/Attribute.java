package com.codexfons.serializationBench.fixtures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Attribute implements Serializable {
    public String internalName;
    public String displayName;
    public AttributeOrder order;
    public AttributeType type;
    public List<AttributeValue> values = new ArrayList<>();
}
