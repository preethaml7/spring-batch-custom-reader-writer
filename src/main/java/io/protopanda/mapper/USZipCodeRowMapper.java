package io.protopanda.mapper;

import io.protopanda.model.USZipCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class USZipCodeRowMapper implements RowMapper<USZipCode> {

    @Override
    public USZipCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        return USZipCode.builder()
                // .id(rs.getLong("id"))
                .city(rs.getString("city"))
                .county_fips(rs.getString("county_fips"))
                .county_fips_all(rs.getString("county_fips_all"))
                .county_name(rs.getString("county_name"))
                .county_names_all(rs.getString("county_names_all"))
                .county_weights(rs.getString("county_weights"))
                .density(rs.getString("density"))
                .lat(rs.getString("lat"))
                .lng(rs.getString("lng"))
                .imprecise(rs.getString("imprecise"))
                .military(rs.getString("military"))
                .parent_zcta(rs.getString("parent_zcta"))
                .zip(String.valueOf(rs.getLong("zip")))
                .population(rs.getString("population"))
                .state_id(rs.getString("state_id"))
                .state_name(rs.getString("state_name"))
                .timezone(rs.getString("timezone"))
                .zcta(rs.getString("zcta"))
                .build();
    }
}