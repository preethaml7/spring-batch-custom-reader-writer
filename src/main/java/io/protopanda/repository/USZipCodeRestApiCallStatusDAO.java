package io.protopanda.repository;

import io.protopanda.model.USZipCode;

public interface USZipCodeRestApiCallStatusDAO {

    boolean updateRestApiCallStatusInDB(USZipCode USZipCode, String isProcessed, String apiResponse, String apiResponseTime);
}
