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
@Table(name = "CLAIM")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : CLAIM
public class ClaimEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CLAIMNUMBER")
    private String claimNumber;
    @Column(name = "POLICYNUMBER")
    private String policynumber;
    @Column(name = "CLAIMDATE")
    private String claimdate;
    @Column(name = "PAID")
    private String paid;
    @Column(name = "VALUE")
    private String value;
    @Column(name = "CAUSE")
    private String cause;
    @Column(name = "OBSERVATIONS")
    private String observations;
}
