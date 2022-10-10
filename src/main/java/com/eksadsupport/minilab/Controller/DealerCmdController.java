package com.eksadsupport.minilab.Controller;


import com.eksadsupport.minilab.Common.CheckUtils;
import com.eksadsupport.minilab.Common.GenerateJWT;
import com.eksadsupport.minilab.Common.Util;
import com.eksadsupport.minilab.domain.Dealer;
import com.eksadsupport.minilab.dto.dealer.ResponseSave;
import com.eksadsupport.minilab.service.DealerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("ddms/v1/cmd/master/dealer")
public class DealerCmdController {

    @Autowired
    DealerService ds;

    @PostMapping("save")
    public ResponseEntity<Object> saveAll(
            @RequestBody Map<String,Object> request,
            @RequestHeader(required = false,name = "token") String token
    ){
        ResponseSave responseSave = new ResponseSave();
        try {
            Claims claims = GenerateJWT.validateToken(token);


            String dealerId = request.get("dealerId").toString();
            String dealerName = request.get("dealerName").toString();
            String dealerClass = request.get("dealerClass").toString();
            String telpNumber = request.get("telpNumber").toString();
            String alamat = request.get("alamat").toString();
            String dealerExtCode = request.get("dealerExtCode").toString();
            String dealerStatus = request.get("dealerStatus").toString();

            String class1 = "H23"; String class2 = "H123";
            String status1 = "ACTIVE"; String status2="INACTIVE";
            String blank = "";
            //String cek_id = ds.cekIdDealer(dealerId);
            //FITUR INSERT
            if(CheckUtils.isNullOrEmpty(dealerId)) {
                if (dealerClass.equals(class1) || dealerClass.equals(class2) || dealerClass.equals(blank)) {
                    if (dealerStatus.equalsIgnoreCase(status1) || dealerStatus.equalsIgnoreCase(status2) || dealerStatus.equals(blank)) {
                        if (Util.checkIfValidTelp(telpNumber) || telpNumber.equals(blank)) {
                            int generat = ds.codeGenerate();
                            int insertDealer = ds.getCreate(String.valueOf(generat + 1), dealerName, dealerClass, telpNumber, alamat, dealerExtCode, dealerStatus);
                            //Optional<Dealer> dealerList = ds.findbyID(dealerId);
                            Optional<Dealer> dealerList = ds.findbyID(String.valueOf(generat+1));
                            responseSave.responseSuccess(dealerList);
                            return new ResponseEntity<>(responseSave, HttpStatus.OK);
                        }
                    }
                }
                //FITUR UPDATE
            }String cek_id = ds.cekIdDealer(dealerId);
            if(cek_id.equals(dealerId)){
                if(CheckUtils.isNullOrEmpty(dealerClass)){
                    String deal_class = ds.cekClassDealer(dealerId);
                    dealerClass = deal_class;
                }
                if(CheckUtils.isNullOrEmpty(telpNumber)){
                    String telp = ds.cekTelpDealer(dealerId);
                    telpNumber = telp;

                }if(CheckUtils.isNullOrEmpty(alamat)){
                    String al = ds.cekAlamatDealer(dealerId);
                    alamat = al;

                } if(CheckUtils.isNullOrEmpty(dealerExtCode)){
                    String ext = ds.cekExtCode(dealerId);
                    dealerExtCode = ext;

                } if(CheckUtils.isNullOrEmpty(dealerStatus)){
                    String stat = ds.cekStatusDealer(dealerId);
                    dealerStatus = stat;
                }
                int editDealer = ds.getUpdateAll(dealerId, dealerName, dealerClass, telpNumber, alamat, dealerExtCode, dealerStatus);
                Optional<Dealer> dealerList = ds.findbyID(dealerId);
                responseSave.responseSuccess(dealerList);
                return new ResponseEntity<>(responseSave, HttpStatus.OK);
            }


        }catch (SignatureException sg){
            responseSave.responseBadRequest();
            return new ResponseEntity<>(responseSave, HttpStatus.BAD_REQUEST);
        }catch (ExpiredJwtException ex){
            responseSave.responseBadRequest();
            return new ResponseEntity<>(responseSave, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            responseSave.responseBadRequest();
            return new ResponseEntity<>(responseSave, HttpStatus.BAD_REQUEST);
        }
        responseSave.responseBadRequest();
        return new ResponseEntity<>(responseSave, HttpStatus.BAD_REQUEST);
    }



}
