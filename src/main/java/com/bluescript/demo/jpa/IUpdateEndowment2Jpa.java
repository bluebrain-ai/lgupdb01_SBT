package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.EndowmentEntity;

public interface IUpdateEndowment2Jpa extends JpaRepository<EndowmentEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ENDOWMENT SET WITHPROFITS = :caEWithProfits , EQUITIES = :caEEquities , MANAGEDFUND = :caEManagedFund , FUNDNAME = :caEFundName , TERM = :db2ETermSint , SUMASSURED = :db2ESumassuredInt , LIFEASSURED = :caELifeAssured WHERE POLICYNUMBER = :db2PolicynumInt", nativeQuery = true)
    void updateEndowmentByCaEWithProfitsAndCaEEquitiesAndCaEManagedFund(@Param("caEWithProfits") String caEWithProfits,
            @Param("caEEquities") String caEEquities, @Param("caEManagedFund") String caEManagedFund,
            @Param("caEFundName") String caEFundName, @Param("db2ETermSint") int db2ETermSint,
            @Param("db2ESumassuredInt") int db2ESumassuredInt, @Param("caELifeAssured") String caELifeAssured,
            @Param("db2PolicynumInt") int db2PolicynumInt);
}
