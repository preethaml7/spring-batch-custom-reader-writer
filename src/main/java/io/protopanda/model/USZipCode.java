package io.protopanda.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class USZipCode {

    private Long id;
    private String zip;
    private String lat;
    private String lng;
    private String city;
    private String state_id;
    private String state_name;
    private String zcta;
    private String parent_zcta;
    private String population;
    private String density;
    private String county_fips;
    private String county_name;
    private String county_weights;
    private String county_names_all;
    private String county_fips_all;
    private String imprecise;
    private String military;
    private String timezone;
    private String is_processed;
    private String api_response;
    private String api_response_time;

}