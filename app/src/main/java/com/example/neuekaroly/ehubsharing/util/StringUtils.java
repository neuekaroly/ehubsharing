package com.example.neuekaroly.ehubsharing.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class StringUtils {
    public static String connectorTypesStringBuilder(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        String connectorTypes[] = str.split(",");

        LinkedList<String> list = new LinkedList<>(Arrays.asList(connectorTypes));

        Map<String, Integer> hashMap = CollectionUtils.getCardinalityMap(list);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            stringBuilder.append("\n" + entry.getValue() + " x " + entry.getKey());
        }

        return stringBuilder.toString();
    }

    public static String chargerCostStringTransformer(int cost) {
        if(cost == 0) {
            return "Free";
        } else {
            return cost + " " + "Ft";
        }
    }
}
