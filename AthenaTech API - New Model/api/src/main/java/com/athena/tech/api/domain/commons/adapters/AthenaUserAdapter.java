package com.athena.tech.api.domain.commons.adapters;

import com.athena.tech.api.application.web.response.AthenaUser.AthenaUserFormatedResponse;
import com.athena.tech.api.domain.entity.user.AthenaUserObj;
import com.athena.tech.api.domain.entity.user.AthenaUserReport;
import com.athena.tech.api.domain.enums.AccountType;

public class AthenaUserAdapter {

    public AccountType adapterStringToAccountType(String value) {
            return AccountType.valueOf(value.toUpperCase());
    }

    public String adapterAccountTypeToString(AccountType value) {
        if (value.equals(AccountType.STUDENT)) {
            return "STUDENT";
        } else if (value.equals(AccountType.INSTRUCTOR)) {
            return "INSTRUCTOR";
        } else {
            return "INVALID_ACCOUNT";
        }
    }

    public AthenaUserFormatedResponse adapterAthenaUserObjToAthenaUserFormatedResponse(AthenaUserObj athenaUserObj) {
        return new AthenaUserFormatedResponse(
                athenaUserObj.getFullName(),
                athenaUserObj.getNickname(),
                athenaUserObj.getEmail(),
                athenaUserObj.getAccountType(),
                athenaUserObj.getActive());
    }

    public AthenaUserReport adapterAthenaUserObjToAthenaUserReport(AthenaUserObj athenaUserObj) {
        GenericAdapter genericAdapter = new GenericAdapter();

        return new AthenaUserReport(
                athenaUserObj.getIdUser(),
                athenaUserObj.getFullName(),
                athenaUserObj.getNickname(),
                adapterAccountTypeToString(athenaUserObj.getAccountType()),
                genericAdapter.adapterBooleanToString(athenaUserObj.getActive())
        );
    }
}
