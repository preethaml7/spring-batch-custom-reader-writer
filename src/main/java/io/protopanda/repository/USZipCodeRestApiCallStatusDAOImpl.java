package io.protopanda.repository;

import io.protopanda.model.USZipCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class USZipCodeRestApiCallStatusDAOImpl implements USZipCodeRestApiCallStatusDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean updateRestApiCallStatusInDB(USZipCode USZipCode, String isProcessed, String apiResponse, String apiResponseTime) {

        String insertQuery = "INSERT INTO testdb.us_zipcodes_processed (id, zip, lat, lng, city, state_id, state_name, zcta, parent_zcta, " +
                "population, density, county_fips, county_name,county_weights, county_names_all, county_fips_all, imprecise, military, " +
                "timezone, is_processed, api_response, api_response_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            return updateDatabase(USZipCode, apiResponse, apiResponseTime, insertQuery);
        } catch (Exception ex) {

            log.error("^^^^^^^ Exception in updating processed table: Retrying again ^^^^^^^ " + ex.getMessage());

            return updateDatabase(USZipCode, apiResponse, apiResponseTime, insertQuery);
        }
    }

    private boolean updateDatabase(USZipCode uSZipCode, String apiResponse, String apiResponseTime, String insertQuery) {
        return jdbcTemplate.update(insertQuery, uSZipCode.getId(), uSZipCode.getZip(), uSZipCode.getLat(),
                uSZipCode.getLng(), uSZipCode.getCity(), uSZipCode.getState_id(), uSZipCode.getState_name()
                , uSZipCode.getZcta(), uSZipCode.getParent_zcta(), uSZipCode.getPopulation(),
                uSZipCode.getDensity(), uSZipCode.getCounty_fips(),
                uSZipCode.getCounty_name(), uSZipCode.getCounty_weights(),
                uSZipCode.getCounty_names_all(), uSZipCode.getCounty_fips_all(),
                uSZipCode.getImprecise(), uSZipCode.getMilitary(),
                uSZipCode.getTimezone(), "true", apiResponse, apiResponseTime) > 0;
    }
}
