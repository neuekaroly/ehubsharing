package com.example.neuekaroly.ehubsharing;

import com.example.neuekaroly.ehubsharing.util.StringUtils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    /**
     * Test cases for the StringUtils.connectorTypesStringBuilder() method
     */
    @Test(expected = NullPointerException.class)
    public void connectorTypesStringBuilder_GivenNull_ThrowsNullPointerException() {
        StringUtils.connectorTypesStringBuilder(null);
    }

    @Test
    public void connectorTypesStringBuilder_givenEmptyString_ReturnEmptyString() {
        assertEquals("", StringUtils.connectorTypesStringBuilder(""));
    }

    @Test
    public void connectorTypesStringBuilder_givenValidStringWithOneConnnectorType_ReturnValidString() {
        assertEquals("\n2 x Type2", StringUtils.connectorTypesStringBuilder("Type2,Type2"));
    }

    @Test
    public void connectorTypesStringBuilder_givenValidStringWithTwoConnnectorType_ReturnValidString() {
        assertEquals("\n2 x Type2\n2 x Type3", StringUtils.connectorTypesStringBuilder("Type2,Type2,Type3,Type3"));
    }

    /**
     * Test cases for the StringUtils.chargerCostStringTransformer() method
     */
    @Test
    public void chargerCostStringTransformer_GivenNullInteger_ReturnFreeString() {
        assertEquals("Free", StringUtils.chargerCostStringTransformer(0));
    }

    @Test
    public void chargerCostStringTransformer_GivenNoNullInteger_ReturnNumberString() {
        assertEquals("10 Ft", StringUtils.chargerCostStringTransformer(10));
    }
}
