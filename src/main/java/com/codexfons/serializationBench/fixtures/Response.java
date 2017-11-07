package com.codexfons.serializationBench.fixtures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Response implements Serializable {
    public Request request;

    public SortedMap<String, Attribute> mainFilters = new TreeMap<>();
    public SortedMap<String, Attribute> extraFilters = new TreeMap<>();

    public List<Item> items = new ArrayList<>();

    public static final Response TEST_RESPONSE = new Response() {{
        request = new Request() {{
            query = "cool";
            offset = 0;
            count = 24;
        }};

        mainFilters.put("cat", new Attribute() {{
            internalName = "cat";
            displayName = "Category";

            values.add(new AttributeValue() {{
                value = "Cheap stuff";
                frequency = 1234L;
                relevance = 0.123;
                isRelativeFrequency = false;
            }});
        }});

        extraFilters.put("notInteresting", new Attribute() {{
            internalName = "notInteresting";
            displayName = "idc";

            values.add(new AttributeValue() {{
                value = "Who cares";
                frequency = 1234L;
                relevance = 0.123;
                isRelativeFrequency = false;
            }});
        }});

        items.add(new Item() {{
            id = "ABC123";
            name = "Cool product";
            orderNumbers.put("default", "ABABABABAB-123345364");
        }});
    }};
}
