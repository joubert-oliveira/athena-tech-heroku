package com.athena.tech.api.domain.commons.adapters;

public class GenericAdapter {
    public Boolean adapterBitToBolean(Integer value) {
        if (value == 1) {
            return true;
        } else if (value == 0) {
            return false;
        }
        return null;
    }

    public Integer adapterBooleanToBit(Boolean value) {
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }

    public String adapterBooleanToString(Boolean value) {
        if (value) {
            return "True";
        } else {
            return "False";
        }
    }
}

