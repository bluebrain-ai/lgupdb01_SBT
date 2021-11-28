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
@Table(name = "HOUSE")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : HOUSE
public class HouseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POLICYNUMBER")
    private String policyNumber;
    @Column(name = "PROPERTYTYPE")
    private String propertytype;
    @Column(name = "BEDROOMS")
    private String bedrooms;
    @Column(name = "VALUE")
    private String value;
    @Column(name = "HOUSENAME")
    private String housename;
    @Column(name = "HOUSENUMBER")
    private String housenumber;
    @Column(name = "POSTCODE")
    private String postcode;
}
