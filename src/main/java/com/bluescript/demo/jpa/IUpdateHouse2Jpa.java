package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.HouseEntity;

public interface IUpdateHouse2Jpa extends JpaRepository<HouseEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE HOUSE SET PROPERTYTYPE = :caHPropertyType , BEDROOMS = :db2HBedroomsSint , VALUE = :db2HValueInt , HOUSENAME = :caHHouseName , HOUSENUMBER = :caHHouseNumber , POSTCODE = :caHPostcode WHERE POLICYNUMBER = :db2PolicynumInt", nativeQuery = true)
    void updateHouseByCaHPropertyTypeAndDb2HBedroomsSintAndDb2HValueInt(
            @Param("caHPropertyType") String caHPropertyType, @Param("db2HBedroomsSint") int db2HBedroomsSint,
            @Param("db2HValueInt") int db2HValueInt, @Param("caHHouseName") String caHHouseName,
            @Param("caHHouseNumber") String caHHouseNumber, @Param("caHPostcode") String caHPostcode,
            @Param("db2PolicynumInt") int db2PolicynumInt);
}
