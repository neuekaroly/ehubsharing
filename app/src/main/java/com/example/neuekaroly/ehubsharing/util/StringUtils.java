package com.example.neuekaroly.ehubsharing.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class StringUtils {
    /**
     * This method make the right output string from the connector-types
     */
    public static String connectorTypesStringBuilder(String str) {
        if(str.equals("")) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        String connectorTypes[] = str.split(",");

        LinkedList<String> list = new LinkedList<>(Arrays.asList(connectorTypes));

        Map<String, Integer> hashMap = CollectionUtils.getCardinalityMap(list);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            stringBuilder.append("\n" + entry.getValue() + " x " + entry.getKey());
        }

        return stringBuilder.toString();
    }

    /**
     * This method make the right output string from charger-cost
     */
    public static String chargerCostStringTransformer(int cost) {
        if(cost == 0) {
            return "Free";
        } else {
            return cost + " " + "Ft";
        }
    }
}
