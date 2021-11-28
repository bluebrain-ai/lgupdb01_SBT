package com.bluescript.demo.jpa;

import com.bluescript.demo.entity.PolicyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IUpdatePolicy4Jpa extends JpaRepository<PolicyEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE POLICY SET ISSUEDATE = :caIssueDate , EXPIRYDATE = :caExpiryDate , LASTCHANGED = CURRENT TIMESTAMP , BROKERID = :db2BrokeridInt , BROKERSREFERENCE = :caBrokersref WHERE ROWID =:rowId", nativeQuery = true)
    void updatePolicyByCaIssueDateAndCaExpiryDateAndDb2BrokeridInt(@Param("caIssueDate") String caIssueDate,
            @Param("caExpiryDate") String caExpiryDate, @Param("db2BrokeridInt") int db2BrokeridInt,
            @Param("caBrokersref") String caBrokersref, @Param("rowId") int rowId);
}
