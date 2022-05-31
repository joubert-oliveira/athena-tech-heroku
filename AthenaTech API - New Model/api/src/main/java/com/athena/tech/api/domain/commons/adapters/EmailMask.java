package com.athena.tech.api.domain.commons.adapters;

public class EmailMask {
    public String MaskEmail(String value) {
        return value.substring(1, 2);
    }
}
