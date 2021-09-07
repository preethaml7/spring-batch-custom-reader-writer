drop table if exists us_zipcodes;
CREATE TABLE us_zipcodes
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    zip               VARCHAR(255) NULL,
    lat               VARCHAR(255) NULL,
    lng               VARCHAR(255) NULL,
    city              VARCHAR(255) NULL,
    state_id          VARCHAR(255) NULL,
    state_name        VARCHAR(255) NULL,
    zcta              VARCHAR(255) NULL,
    parent_zcta       VARCHAR(255) NULL,
    population        VARCHAR(255) NULL,
    density           VARCHAR(255) NULL,
    county_fips       VARCHAR(255) NULL,
    county_name       VARCHAR(255) NULL,
    county_weights    VARCHAR(255) NULL,
    county_names_all  VARCHAR(255) NULL,
    county_fips_all   VARCHAR(255) NULL,
    imprecise         VARCHAR(255) NULL,
    military          VARCHAR(255) NULL,
    timezone          VARCHAR(255) NULL,
    is_processed      VARCHAR(255) NULL,
    api_response      TEXT         NULL,
    api_response_time VARCHAR(255) NULL

);
commit;

drop table if exists us_zipcodes_processed;
CREATE TABLE us_zipcodes_processed
(
    id                INT PRIMARY KEY AUTO_INCREMENT,
    zip               VARCHAR(255) NULL,
    lat               VARCHAR(255) NULL,
    lng               VARCHAR(255) NULL,
    city              VARCHAR(255) NULL,
    state_id          VARCHAR(255) NULL,
    state_name        VARCHAR(255) NULL,
    zcta              VARCHAR(255) NULL,
    parent_zcta       VARCHAR(255) NULL,
    population        VARCHAR(255) NULL,
    density           VARCHAR(255) NULL,
    county_fips       VARCHAR(255) NULL,
    county_name       VARCHAR(255) NULL,
    county_weights    VARCHAR(255) NULL,
    county_names_all  VARCHAR(255) NULL,
    county_fips_all   VARCHAR(255) NULL,
    imprecise         VARCHAR(255) NULL,
    military          VARCHAR(255) NULL,
    timezone          VARCHAR(255) NULL,
    is_processed      VARCHAR(255) NULL,
    api_response      TEXT         NULL,
    api_response_time VARCHAR(255) NULL

);
commit;