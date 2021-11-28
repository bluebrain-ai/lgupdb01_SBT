package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.MotorEntity;

public interface IUpdateMotor2Jpa extends JpaRepository<MotorEntity, String> {
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE MOTOR SET MAKE = :caMMake , MODEL = :caMModel , VALUE = :db2MValueInt , REGNUMBER = :caMRegnumber , COLOUR = :caMColour , CC = :db2MCcSint , YEAROFMANUFACTURE = :caMManufactured , PREMIUM = :db2MPremiumInt , ACCIDENTS = :db2MAccidentsInt WHERE POLICYNUMBER = :db2PolicynumInt", nativeQuery = true)
    void updateMotorByCaMMakeAndCaMModelAndDb2MValueInt(@Param("caMMake") String caMMake,
            @Param("caMModel") String caMModel, @Param("db2MValueInt") int db2MValueInt,
            @Param("caMRegnumber") String caMRegnumber, @Param("caMColour") String caMColour,
            @Param("db2MCcSint") int db2MCcSint, @Param("caMManufactured") String caMManufactured,
            @Param("db2MPremiumInt") int db2MPremiumInt, @Param("db2MAccidentsInt") int db2MAccidentsInt,
            @Param("db2PolicynumInt") int db2PolicynumInt);
}
