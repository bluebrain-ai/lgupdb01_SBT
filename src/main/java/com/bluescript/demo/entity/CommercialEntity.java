package com.bluescript.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Table(name = "COMMERCIAL")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : COMMERCIAL
public class CommercialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POLICYNUMBER")
    private String policyNumber;
    @Column(name = "REQUESTDATE")
    private String make;
    @Column(name = "STARTDATE")
    private String model;
    @Column(name = "RENEWALDATE")
    private String renewaldate;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ZIPCODE")
    private String zipcode;
    @Column(name = "LATITUDEN")
    private String latitiden;
    @Column(name = "LONGITUDEW")
    private String longitudew;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "PROPERTYTYPE")
    private String propertytype;
    @Column(name = "FIREPERIL")
    private String fireperil;
    @Column(name = "FIREPREMIUM")
    private String firepremium;
    @Column(name = "CRIMEPERIL")
    private String crimerperil;
    @Column(name = "CRIMEPREMIUM")
    private String crimepremium;
    @Column(name = "FLOODPERIL")
    private String floodperil;
    @Column(name = "WEATHERPERIL")
    private String weatherperil;
    @Column(name = "WEATHERPREMIUM")
    private String weatherpremium;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "REJECTIONREASON")
    private String rejectionreason;

}
