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
@Table(name = "ENDOWMENT")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : ENDOWMENT
public class EndowmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POLICYNUMBER")
    private String policyNumber;
    @Column(name = "WITHPROFITS")
    private String withprofits;
    @Column(name = "EQUITIES")
    private String equities;
    @Column(name = "MANAGEDFUND")
    private String managedfund;
    @Column(name = "FUNDNAME")
    private String fundname;
    @Column(name = "TERM")
    private String term;
    @Column(name = "SUMASSURED")
    private String sumassured;
    @Column(name = "LIFEASSURED")
    private String lifeassured;
    @Column(name = "PADDINGDATA")
    private String paddingdata;
}
