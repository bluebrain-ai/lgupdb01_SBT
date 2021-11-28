package com.bluescript.demo.jpa;

import java.util.stream.Stream;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.dto.IfetchDb2PolicyCursorJpaDto;
import com.bluescript.demo.entity.PolicyEntity;

/** QueryHints.FETCH_SIZE --> Modify Based on Performance */// ROWID as rowid,
public interface IfetchDb2PolicyCursorJpa extends JpaRepository<PolicyEntity, String> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "1000"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true") })
    @Query(value = "SELECT ROWID as rowid,ISSUEDATE as db2Issuedate,EXPIRYDATE as db2Expirydate,LASTCHANGED as db2Lastchanged,BROKERID as db2BrokeridInt,BROKERREFERENCE as db2Brokersref FROM POLICY WHERE ( CUSTOMERNUMBER = :db2CustomernumInt AND POLICYNUMBER = :db2PolicynumInt )", nativeQuery = true)
    @Transactional(readOnly = true)
    Stream<IfetchDb2PolicyCursorJpaDto> getPolicyCursorByDb2CustomernumIntAndDb2PolicynumInt(
            @Param("db2CustomernumInt") int db2CustomernumInt, @Param("db2PolicynumInt") int db2PolicynumInt);
}
