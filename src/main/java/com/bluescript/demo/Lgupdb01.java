package com.bluescript.demo;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import reactor.core.publisher.Mono;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.bluescript.demo.jpa.IfetchDb2PolicyCursorJpa;
import com.bluescript.demo.model.ErrorMsg;
import com.bluescript.demo.model.EmVariable;
import com.bluescript.demo.model.Db2InIntegers;
import com.bluescript.demo.model.Dfhcommarea;
import com.bluescript.demo.jpa.ISelectPolicyLastChangedJpa;
import com.bluescript.demo.jpa.IUpdatePolicy4Jpa;
import com.bluescript.demo.model.CaCommercial;
import com.bluescript.demo.model.CaCustomerRequest;
import com.bluescript.demo.model.CaCustsecrRequest;
import com.bluescript.demo.model.CaEndowment;
import com.bluescript.demo.model.CaHouse;
import com.bluescript.demo.model.CaMotor;
import com.bluescript.demo.model.CaPolicyCommon;
import com.bluescript.demo.model.CaPolicyRequest;
import com.bluescript.demo.model.Db2InIntegers;
import com.bluescript.demo.model.CaClaim;

import com.bluescript.demo.dto.IfetchDb2PolicyCursorJpaDto;
import com.bluescript.demo.jpa.IUpdateEndowment2Jpa;
import com.bluescript.demo.jpa.IUpdateHouse2Jpa;
import com.bluescript.demo.jpa.IUpdateMotor2Jpa;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server/Application is down. Please contact support team.") })

public class Lgupdb01 {

    // @Autowired
    // private WsHeader wsHeader;

    @Autowired
    private Db2InIntegers db2InIntegers;

    @Autowired
    private ErrorMsg errorMsg;
    @Autowired
    private EmVariable emVariable;
    @Autowired
    private Dfhcommarea dfhcommarea;
    @Autowired
    private CaCustomerRequest caCustomerRequest;
    @Autowired
    private CaCustsecrRequest caCustsecrRequest;
    @Autowired
    private CaPolicyRequest caPolicyRequest;
    @Autowired
    private CaPolicyCommon caPolicyCommon;
    @Autowired
    private CaEndowment caEndowment;
    @Autowired
    private CaHouse caHouse;
    @Autowired
    private CaMotor caMotor;
    @Autowired
    private CaCommercial caCommercial;
    @Autowired
    private CaClaim caClaim;

    @Autowired
    private IfetchDb2PolicyCursorJpa fetchDb2PolicyCursorJpa;

    @Value("${api.LGSTSQ.host}")
    private String LGSTSQ_HOST;
    @Value("${api.LGSTSQ.uri}")
    private String LGSTSQ_URI;
    @Value("${api.lgupvs01.uri}")
    private URI lgupvs01_URI;
    @Value("${api.lgupvs01.host}")
    private String lgupvs01_HOST;

    private int eibcalen;
    private String caErrorMsg;

    @Autowired
    private IUpdatePolicy4Jpa updatePolicy4Jpa;
    @Autowired
    private ISelectPolicyLastChangedJpa selecLastChanged;
    @Autowired
    private IUpdateEndowment2Jpa updateEndowment2Jpa;
    @Autowired
    private IUpdateHouse2Jpa updateHouse2Jpa;
    @Autowired
    private IUpdateMotor2Jpa updateMotor2Jpa;

    @PostMapping("/lgupdb01")
    @Transactional
    public ResponseEntity<Dfhcommarea> mainline(@RequestBody Dfhcommarea payload) {
        log.warn("Methodmainlinestarted..", payload);
        BeanUtils.copyProperties(payload, dfhcommarea);

        db2InIntegers.setDb2CustomernumInt((int) dfhcommarea.getCaCustomerNum());
        // db2InIntegers.setDb2PolicynumInt((int) dfhcommarea.getCaPolicyRequest().getCaPolicyNum());

        IfetchDb2PolicyCursorJpaDto db2Data = fetchDb2PolicyRow();
        updatePolicyDb2Info(db2Data);
        try {
            WebClient webclientBuilder = WebClient.create(lgupvs01_HOST);
            Mono<Dfhcommarea> lgupvs01Resp = webclientBuilder.post().uri(lgupvs01_URI)
                    .body(Mono.just(dfhcommarea), Dfhcommarea.class).retrieve().bodyToMono(Dfhcommarea.class);// .timeout(Duration.ofMillis(10_000));
            dfhcommarea = lgupvs01Resp.block();
        } catch (Exception e) {
            log.error(e);
        }

        log.debug("Method mainline completed..");
        return new ResponseEntity<>(dfhcommarea, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public IfetchDb2PolicyCursorJpaDto fetchDb2PolicyRow() {
        log.debug("MethodfetchDb2PolicyRowstarted..");
        // emVariable.setEmSqlreq(" FETCHROW");
        IfetchDb2PolicyCursorJpaDto policyData = null;
        try {
            Stream<IfetchDb2PolicyCursorJpaDto> policyCursorData = fetchDb2PolicyCursorJpa
                    .getPolicyCursorByDb2CustomernumIntAndDb2PolicynumInt(db2InIntegers.getDb2CustomernumInt(),
                            db2InIntegers.getDb2PolicynumInt());
            policyData = policyCursorData.findFirst().get();
            log.warn("policyData" + policyData);
        } catch (NoSuchElementException ne) {
            dfhcommarea.setCaReturnCode(01);
            log.error("No Record Found in policyCursorData ");
        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(90);
            log.error(e);
            writeErrorMessage();

        }
        log.debug("Method fetchDb2PolicyRow completed..");
        return policyData; // Short circuited since only one row is expected.
    }

    @Transactional
    public void updatePolicyDb2Info(IfetchDb2PolicyCursorJpaDto db2Data) {

        log.debug("MethodupdatePolicyDb2Infostarted..");

        if (db2Data != null) {
            db2Data.getDb2Lastchanged();
            if (dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaLastchanged() == db2Data
                    .getDb2Lastchanged()) {
                switch (dfhcommarea.getCaRequestId()) {
                case "01UEND":
                    updateEndowDb2Info();
                    break;
                case "01UHOU":
                    updateHouseDb2Info();
                    break;
                case "01UMOT":
                    updateMotorDb2Info();
                    break;
                }
                // emVariable.setEmSqlreq(" UPDATE POLICY");
                db2InIntegers
                        .setDb2BrokeridInt((int) dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaBrokerid());
                db2InIntegers.setDb2PaymentInt(dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaPayment());
                try {
                    updatePolicy4Jpa.updatePolicyByCaIssueDateAndCaExpiryDateAndDb2BrokeridInt(
                            dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaIssueDate(),
                            dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaExpiryDate(),
                            db2InIntegers.getDb2BrokeridInt(),
                            dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaBrokersref(), db2Data.getRowId());

                    String caLastchanged = selecLastChanged
                            .getPolicyByDb2PolicynumInt(db2InIntegers.getDb2PolicynumInt());
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().setCaLastchanged(caLastchanged);

                } catch (Exception e) {
                    dfhcommarea.setCaReturnCode(90);
                    log.error(e);
                    writeErrorMessage();

                }

            } else {
                dfhcommarea.setCaReturnCode(02);
            }
        }
        log.debug("Method updatePolicyDb2Info completed..");
    }

    @Transactional
    public void updateEndowDb2Info() {
        log.debug("MethodupdateEndowDb2Infostarted..");
        // emVariable.setEmSqlreq(" UPDATE ENDOW ");
        // wsVaryLen = wsRequiredCaLen - eibcalen;

        db2InIntegers.setDb2ETermSint(dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaETerm());
        db2InIntegers.setDb2ESumassuredInt(dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaESumAssured());

        try {
            // if (wsVaryLen > 0) { // this logic is not required Testing time fix
            updateEndowment2Jpa.updateEndowmentByCaEWithProfitsAndCaEEquitiesAndCaEManagedFund(
                    dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEWithProfits(),
                    dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEEquities(),
                    dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEManagedFund(),
                    dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEFundName(), db2InIntegers.getDb2ETermSint(),
                    db2InIntegers.getDb2ESumassuredInt(),
                    dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaELifeAssured(),
                    db2InIntegers.getDb2PolicynumInt());

            /*
             * } else { updateEndowment2Jpa.updateEndowmentByCaEWithProfitsAndCaEEquitiesAndCaEManagedFund(
             * dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEWithProfits(),
             * dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEEquities(),
             * dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEManagedFund(),
             * dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEFundName(), db2InIntegers.getDb2ETermSint(),
             * db2InIntegers.getDb2ESumassuredInt(),
             * dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaELifeAssured(),
             * db2InIntegers.getDb2PolicynumInt());
             * 
             * }
             */
        } catch (Exception e) {

            dfhcommarea.setCaReturnCode(90);
            log.error(e);
        }
        log.debug("Method updateEndowDb2Info completed..");
    }

    @Transactional
    public void updateHouseDb2Info() {
        log.debug("MethodupdateHouseDb2Infostarted..");

        db2InIntegers.setDb2HBedroomsSint(dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHBedrooms());
        db2InIntegers.setDb2HValueInt(dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHValue());

        // emVariable.setEmSqlreq(" UPDATE HOUSE ");
        try {

            updateHouse2Jpa.updateHouseByCaHPropertyTypeAndDb2HBedroomsSintAndDb2HValueInt(
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHPropertyType(),
                    db2InIntegers.getDb2HBedroomsSint(), db2InIntegers.getDb2HValueInt(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHHouseName(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHHouseNumber(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHPostcode(), db2InIntegers.getDb2PolicynumInt());

        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(01);
            log.error(e);
        }

        log.debug("Method updateHouseDb2Info completed..");
    }

    @Transactional
    public void updateMotorDb2Info() {
        log.debug("MethodupdateMotorDb2Infostarted..");

        db2InIntegers.setDb2MCcSint(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMCc());
        db2InIntegers.setDb2MValueInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMValue());
        db2InIntegers.setDb2MPremiumInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMPremium());
        db2InIntegers.setDb2MAccidentsInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMAccidents());

        // emVariable.setEmSqlreq(" UPDATE MOTOR ");
        try {
            updateMotor2Jpa.updateMotorByCaMMakeAndCaMModelAndDb2MValueInt(
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMMake(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMModel(), db2InIntegers.getDb2MValueInt(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMRegnumber(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMColour(), db2InIntegers.getDb2MCcSint(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMManufactured(),
                    db2InIntegers.getDb2MPremiumInt(), db2InIntegers.getDb2MAccidentsInt(),
                    db2InIntegers.getDb2PolicynumInt());

        } catch (Exception e) {
            log.error(e);
        }

        log.debug("Method updateMotorDb2Info completed..");
    }

    public void writeErrorMessage() {
        log.debug("MethodwriteErrorMessagestarted..");
        String wsAbstime = LocalTime.now().toString();
        String wsDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // String wsDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        // //yyyyMMdd
        String wsTime = LocalTime.now().toString();
        errorMsg.setEmDate(wsDate.substring(0, 8));
        errorMsg.setEmTime(wsTime.substring(0, 6));
        WebClient webclientBuilder = WebClient.create(LGSTSQ_HOST);
        try {
            Mono<ErrorMsg> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                    .body(Mono.just(errorMsg), ErrorMsg.class).retrieve().bodyToMono(ErrorMsg.class);// .timeout(Duration.ofMillis(10_000));
            errorMsg = lgstsqResp.block();
        } catch (Exception e) {
            log.error(e);
        }
        if (eibcalen > 0) {
            if (eibcalen < 91) {
                try {
                    Mono<ErrorMsg> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                            .body(Mono.just(errorMsg), ErrorMsg.class).retrieve().bodyToMono(ErrorMsg.class);// .timeout(Duration.ofMillis(10_000));
                    errorMsg = lgstsqResp.block();
                } catch (Exception e) {
                    log.error(e);
                }

            } else {
                try {
                    Mono<String> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                            .body(Mono.just(caErrorMsg), String.class).retrieve().bodyToMono(String.class);// .timeout(Duration.ofMillis(10_000));
                    caErrorMsg = lgstsqResp.block();
                } catch (Exception e) {
                    log.error(e);
                }

            }

        }

        log.debug("Method writeErrorMessage completed..");
    }

    /* End of program */
}