package com.eksadsupport.minilab.Controller;



import com.eksadsupport.minilab.Common.GenerateJWT;
import com.eksadsupport.minilab.domain.Customer;
import com.eksadsupport.minilab.dto.response.ResponseBadRequest;
import com.eksadsupport.minilab.dto.response.ResponseNoContent;
import com.eksadsupport.minilab.dto.response.ResponseSuccess;
import com.eksadsupport.minilab.dto.response.ResponseUnauthorized;
import com.eksadsupport.minilab.service.CustomerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import static com.eksadsupport.minilab.Common.Util.*;
import static com.eksadsupport.minilab.Common.Util.checkValidKkDanKtp;


@RestController
@RequestMapping("/ddms/v1/cmd/master/customer")
public class CustomerCmdController {
    @Autowired
    CustomerService cs;

    @PostMapping("/save")
    public  ResponseEntity<Object> save(
            @RequestBody final Map<String, Object> inputPayload,
            @RequestHeader(name = "token", required = false) String token
    ) {
        try {
            Claims claims = GenerateJWT.validateToken(token);
            String customerId = valueToStringOrEmpty(inputPayload, "customerId");
            String customerName = valueToStringOrEmpty(inputPayload, "customerName");
            String dealerId = valueToStringOrEmpty(inputPayload, "dealerId");
            String customerGender = valueToStringOrEmpty(inputPayload, "customerGender");
            String customerNik = valueToStringOrEmpty(inputPayload, "customerNik");
            String customerKk = valueToStringOrEmpty(inputPayload, "customerKk");
            String customerEmail = valueToStringOrEmpty(inputPayload, "customerEmail");
            String customerAddress = valueToStringOrEmpty(inputPayload, "customerAddress");
            String customerTelp = valueToStringOrEmpty(inputPayload, "customerTelp");
            String customerHp = valueToStringOrEmpty(inputPayload, "customerHp");
            String customerStatus = valueToStringOrEmpty(inputPayload, "customerStatus");
            String salesId = valueToStringOrEmpty(inputPayload, "salesId");


            if (checkStringIfNulllOrEmpty(customerId)) {
                if (!checkStringIfNulllOrEmpty(customerEmail)) {
                    if (!checkIfValidEmail(customerEmail)){
                        return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);}
                }if (!checkStringIfNulllOrEmpty(customerTelp)) {
                    if (!checkIfValidTelp2(customerTelp)){
                        return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);}
                }if (!checkStringIfNulllOrEmpty(customerHp)) {
                    if (!checkIfValidTHp(customerHp)){
                        return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);}
                }if (!checkValidKkDanKtp(customerKk) || !checkValidKkDanKtp(customerNik)) {
                    return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);}
                String genId=generateId();
                int customer = cs.addCus(genId, customerName, dealerId, customerGender, customerNik, customerKk,
                        customerEmail, customerAddress, customerTelp, customerHp, customerStatus, salesId);
                return new ResponseEntity<>(new ResponseSuccess(cs.getCustomerDTO(genId)), HttpStatus.OK);
            }

            //kondisi jika kosong pada fungsi update akan mengambil data sebelumnya
            if (checkStringIfNulllOrEmpty(customerName)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerName = opt.get().getCustomerName();
            }  if (checkStringIfNulllOrEmpty(dealerId)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                dealerId = opt.get().getDealer().getDealerId();
            }  if (checkStringIfNulllOrEmpty(customerGender)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerGender = opt.get().getCustomerGender();
            }  if (checkStringIfNulllOrEmpty(customerNik)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerNik = opt.get().getCustomerNik();
            }  if (checkStringIfNulllOrEmpty(customerKk)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerKk = opt.get().getCustomerKk();
            }  if (checkStringIfNulllOrEmpty(customerEmail)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerEmail = opt.get().getCustomerEmail();
            }  if (checkStringIfNulllOrEmpty(customerAddress)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerAddress = opt.get().getCustomerAddress();
            }  if (checkStringIfNulllOrEmpty(customerTelp)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerTelp = opt.get().getCustomerTelp();
            }  if (checkStringIfNulllOrEmpty(customerHp)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerHp = opt.get().getCustomerHp();
            }  if (checkStringIfNulllOrEmpty(customerStatus)) {
                Optional<Customer>opt = cs.findByCustomerId(customerId);
                customerStatus = opt.get().getCustomerStatus();
            }
            if (checkStringIfNulllOrEmpty(salesId)) {
                Optional<Customer> opt = cs.findByCustomerId(customerId);
                salesId = opt.get().getSales().getSalesId();
            }
            if (!checkIfValidEmail(customerEmail) || !checkIfValidTelp2(customerTelp) || !checkIfValidTHp(customerHp)||
                    !checkValidKkDanKtp(customerKk) || !checkValidKkDanKtp(customerNik)) {
                return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);
            }
            try {
                int customer = cs.updateCus(customerName, dealerId, customerGender, customerNik, customerKk,
                        customerEmail, customerAddress, customerTelp, customerHp, customerStatus, salesId, customerId);
                return new ResponseEntity<>(new ResponseSuccess(cs.getCustomerDTO(customerId)), HttpStatus.OK);
            }catch (Exception es){
                return new ResponseEntity<>(new ResponseNoContent(), HttpStatus.NO_CONTENT);
            }
        }  catch (SignatureException ex) {
        return new ResponseEntity<>(new ResponseUnauthorized(), HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException expired) {
        return new ResponseEntity<>(new ResponseUnauthorized(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);
        }
    }
}
