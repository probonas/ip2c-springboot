package com.example.ip2c.playgrounds.dao.jpa.repos;


import com.example.ip2c.playgrounds.dao.jpa.entities.CountryEntity;

public interface CountriesRepository extends CrudRepository<CountryEntity, Integer> {
    CountryEntity findCountryByTwoLetterCode(String twoLetterCode);
}
