package com.eksadsupport.minilab.Controller;

import com.eksadsupport.minilab.Common.GenerateJWT;
import com.eksadsupport.minilab.domain.Dealer;
import com.eksadsupport.minilab.domain.Ppn;
import com.eksadsupport.minilab.dto.ppn.GetActivePpn;
import com.eksadsupport.minilab.dto.ppn.GetPpn;
import com.eksadsupport.minilab.dto.ppn.PpnList;
import com.eksadsupport.minilab.dto.ppn.ResPostPpn;
import com.eksadsupport.minilab.dto.response.*;
import com.eksadsupport.minilab.service.PpnService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.eksadsupport.minilab.Common.Util.*;
import static com.eksadsupport.minilab.Common.Util.valueToStringOrEmpty;

@RestController
@RequestMapping("/ddms/v1/cmd/master/ppn")
public class PpnCmdController {
    @Autowired
    PpnService ppnService;

    @PostMapping("save")
    public ResponseEntity<Object> savePpn(
            @RequestBody final Map<String, Object> request,
            @RequestHeader(name = "token", required = false) String token
    ) throws ParseException {
        try {
            Claims claims = GenerateJWT.validateToken(token);
            //Payload
            String ppnId = request.get("id").toString();
            String dealerId = request.get("dealerId").toString();
            String ppnDescription = request.get("ppnDescription").toString();
            Float ppnRate = Float.parseFloat(request.get("ppnRate").toString());
            Float ppnRatePrevious = Float.parseFloat(request.get("ppnRatePrevious").toString());
            String effectiveStartDate = (request.get("effectiveStartDate").toString());
            String effectiveEndDate = request.get("effectiveEndDate").toString();
            String ppnStatus = request.get("status").toString().toUpperCase(Locale.ROOT);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date startDate = sdf.parse(effectiveStartDate);
            Date endDate = sdf.parse(effectiveEndDate);

            Ppn ppnModel = new Ppn();
            Optional<Dealer> getDealerId = ppnService.findDealerId(dealerId);
            Optional<Ppn> getPpnId = ppnService.findPpnById(ppnId);

            //All
            if(checkStringIfNulllOrEmpty(ppnId) && checkStringIfNulllOrEmpty(ppnDescription) || checkStringIfNulllOrEmpty(dealerId)
                    || checkStringIfNulllOrEmpty(String.valueOf(ppnRate))
                    || checkStringIfNulllOrEmpty(String.valueOf(effectiveStartDate)) || checkStringIfNulllOrEmpty(ppnStatus)){
                return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);
            }

            //Insert DATA
            if(checkStringIfNulllOrEmpty(ppnId)){

                ppnModel.setPpnId(generateId());
                ppnModel.setDealer(getDealerId.get());
                ppnModel.setDescription(ppnDescription);
                ppnModel.setPpnRate(ppnRate);

                //RatePrevious
                if (ppnRatePrevious == 0){
                    ppnModel.setPpnRatePrevious(ppnRate);
                }else {
                    ppnModel.setPpnRatePrevious(ppnRatePrevious);
                }

                ppnModel.setEffectiveStartDate(startDate);
                ppnModel.setEffectiveEndDate(endDate);

                //status
                if (ppnStatus.equalsIgnoreCase("ACTIVE")) {
                    ppnModel.setPpnStatus(ppnStatus);
                } else if (ppnStatus.equalsIgnoreCase("INACTIVE")) {
                    ppnModel.setPpnStatus(ppnStatus);
                } else {
                    return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);
                }

                Ppn savePpn = ppnService.savePpn(ppnModel);

                Optional data = ppnService.dataPpn(ppnId, dealerId, ppnDescription, ppnRate, ppnRatePrevious,
                        effectiveStartDate, effectiveEndDate, ppnStatus);

                ResPostPpn resPostPpn = new ResPostPpn();

                resPostPpn.setPpnId(ppnModel.getPpnId());
                resPostPpn.setDealerId(dealerId);
                resPostPpn.setAhmDealerCode("00999");
                resPostPpn.setPpnDescription(ppnModel.getDescription());
                resPostPpn.setPpnRate(ppnModel.getPpnRate());
                resPostPpn.setPpnRatePrevious(ppnModel.getPpnRatePrevious());
                resPostPpn.setEffectiveStartDate(ppnModel.getEffectiveStartDate());
                resPostPpn.setEffectiveEndDate(ppnModel.getEffectiveEndDate());
                resPostPpn.setPpnStatus(ppnModel.getPpnStatus());

                ResponseSavePpn responseSavePpn = new ResponseSavePpn();
                responseSavePpn.setCode(201);
                responseSavePpn.setData(resPostPpn);
                responseSavePpn.setMessage("Process Successed");
                responseSavePpn.setStatus("S");

                return new ResponseEntity<>(responseSavePpn, HttpStatus.OK);
            }else{
                //Update DATA
                ppnModel.setPpnId(ppnId);

                //Dealer
                if (dealerId.isEmpty()){
                    if (getPpnId.isPresent()){
                        dealerId = getPpnId.get().getDealer().getDealerId();
                        ppnModel.setDealer(getPpnId.get().getDealer());
                    }else {
                        return new ResponseEntity<>(new ResponseNoContent(), HttpStatus.NO_CONTENT);
                    }
                }else {
                    try {
                        Dealer dealer = ppnService.getdealer(dealerId);
                        ppnModel.setDealer(dealer);
                    }catch (NoSuchElementException e){
                        return new ResponseEntity<>(new ResponseNoContent(), HttpStatus.NO_CONTENT);
                    }
                }

                //Description
                if (ppnDescription.isEmpty()) {
                    ppnModel.setDescription(getPpnId.get().getDescription());
                } else {
                    ppnModel.setDescription(ppnDescription);
                }

                //PpnRate
                if (ppnRate == 0 && ppnRatePrevious == 0){
                    ppnModel.setPpnRate(getPpnId.get().getPpnRate());
                    ppnModel.setPpnRatePrevious(getPpnId.get().getPpnRatePrevious());
                }else if (ppnRatePrevious == 0){
                    ppnModel.setPpnRatePrevious(getPpnId.get().getPpnRate());
                }else {
                    ppnModel.setPpnRate(ppnRate);
                    ppnModel.setPpnRatePrevious(ppnRatePrevious);
                }

                //DateStart
                if (effectiveStartDate.isEmpty()){
                    if (getPpnId.isPresent()){
                        effectiveStartDate = getPpnId.get().getEffectiveStartDate().toString();
                        ppnModel.setEffectiveStartDate(getPpnId.get().getEffectiveStartDate());
                    }else{
                        return new ResponseEntity<>(new ResponseNoContent(), HttpStatus.NO_CONTENT);
                    }
                }else {
                    Date dateStart = sdf.parse(effectiveStartDate);
                    ppnModel.setEffectiveStartDate(dateStart);
                }

                //DateEnd
                if (effectiveEndDate.isEmpty()){
                    if (getPpnId.isPresent()){
                        effectiveEndDate = getPpnId.get().getEffectiveEndDate().toString();
                        ppnModel.setEffectiveEndDate(getPpnId.get().getEffectiveEndDate());
                    }else {
                        return new ResponseEntity<>(new ResponseNoContent(), HttpStatus.NO_CONTENT);
                    }
                }else {
                    Date dateEnd = sdf.parse(effectiveEndDate);
                    ppnModel.setEffectiveEndDate(dateEnd);
                }

                //status
                if (ppnStatus.isEmpty()){
                    ppnModel.setPpnStatus(getPpnId.get().getPpnStatus());
                }else {
                    if (ppnStatus.equalsIgnoreCase("ACTIVE")) {
                        ppnModel.setPpnStatus(ppnStatus);
                    } else if (ppnStatus.equalsIgnoreCase("INACTIVE")) {
                        ppnModel.setPpnStatus(ppnStatus);
                    } else {
                        return new ResponseEntity<>(new ResponseBadRequest(), HttpStatus.BAD_REQUEST);
                    }
                }

                Ppn update = ppnService.savePpn(ppnModel);

                Optional data = ppnService.dataPpn(ppnId, dealerId, ppnDescription, ppnRate, ppnRatePrevious,
                        effectiveStartDate, effectiveEndDate, ppnStatus);

                ResPostPpn resPostPpn = new ResPostPpn();

                resPostPpn.setPpnId(ppnModel.getPpnId());
                resPostPpn.setDealerId(dealerId);
                resPostPpn.setAhmDealerCode("00999");
                resPostPpn.setPpnDescription(ppnModel.getDescription());
                resPostPpn.setPpnRate(ppnModel.getPpnRate());
                resPostPpn.setPpnRatePrevious(ppnModel.getPpnRatePrevious());
                resPostPpn.setEffectiveStartDate(ppnModel.getEffectiveStartDate());
                resPostPpn.setEffectiveEndDate(ppnModel.getEffectiveEndDate());
                resPostPpn.setPpnStatus(ppnModel.getPpnStatus());

                ResponseSavePpn responseSavePpn = new ResponseSavePpn();
                responseSavePpn.setCode(201);
                responseSavePpn.setData(resPostPpn);
                responseSavePpn.setMessage("Process Successed");
                responseSavePpn.setStatus("S");

                return new ResponseEntity<>(responseSavePpn, HttpStatus.OK);
            }
        }catch (NullPointerException e) {
            return new ResponseEntity<>("JWT salah", HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("JWT Expired", HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("JWT salah", HttpStatus.BAD_REQUEST);
        } catch (SignatureException e) {
            return new ResponseEntity<>("JWT salah", HttpStatus.BAD_REQUEST);
        }
    }
}
