package com.eksadsupport.minilab.service;

import com.eksadsupport.minilab.domain.Dealer;
import com.eksadsupport.minilab.domain.Ppn;
import com.eksadsupport.minilab.dto.ppn.PpnList;
import com.eksadsupport.minilab.dto.ppn.ResDataPpn;
import com.eksadsupport.minilab.repository.DealerRepository;
import com.eksadsupport.minilab.repository.PpnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PpnService {
    @Autowired
    PpnRepository ppnRepository;
    @Autowired
    DealerRepository dealerRepository;

    public Ppn savePpn(Ppn ppn) {
        Ppn save = ppnRepository.save(ppn);

        return save;
    }

    public Optional<Ppn> findPpnById(String id){
        Optional<Ppn> findPpnById = ppnRepository.findById(id);

        return findPpnById;
    }

    public List<Ppn> listPpnAll(String dealerId, String ppnStatus, int limit, int offset){
        List<Ppn> listPpnAll = ppnRepository.listPpnAll(dealerId, ppnStatus, limit, offset);

        return listPpnAll;
    }

    public List<PpnList> ppnList(String dealerId, String ppnStatus, int limit, int offset){
        List<Ppn> mPpnList = ppnRepository.listPpnAll(dealerId, ppnStatus, limit, offset);
        List<PpnList> response = new ArrayList<>();

        for(Ppn ppn: mPpnList){
            PpnList ppnList = new PpnList();

            ppnList.setId(ppn.getPpnId());
            ppnList.setDealerId(ppn.getDealer().getDealerId());
            ppnList.setPpnDescription(ppn.getDescription());
            ppnList.setPpnRate(ppn.getPpnRate());
            ppnList.setPpnRatePrevious(ppn.getPpnRatePrevious());
            ppnList.setEffectiveStartDate(ppn.getEffectiveStartDate());
            ppnList.setEffectiveEndDate(ppn.getEffectiveEndDate());
            ppnList.setStatus(ppn.getPpnStatus());

            response.add(ppnList);
        }

        return response;
    }

    public Optional<Ppn> getAllPpnByDealerId(String dealerId){
        Optional<Ppn> getAllPpnByDealerId = ppnRepository.getAllPpnByDealerId(dealerId);

        return getAllPpnByDealerId;
    }

    public List<PpnList> listPpnByDealerID(String dealerId, int limit, int offset){
        List<Ppn> mPpnList = ppnRepository.listPpnByDealerId(dealerId, limit, offset);
        List<PpnList> response = new ArrayList<>();
        for (Ppn ppn : mPpnList){
            PpnList ppnList = new PpnList();

            ppnList.setId(ppn.getPpnId());
            ppnList.setDealerId(ppn.getDealer().getDealerId());
            ppnList.setPpnDescription(ppn.getDescription());
            ppnList.setPpnRate(ppn.getPpnRate());
            ppnList.setPpnRatePrevious(ppn.getPpnRatePrevious());
            ppnList.setEffectiveStartDate(ppn.getEffectiveStartDate());
            ppnList.setEffectiveEndDate(ppn.getEffectiveEndDate());
            ppnList.setStatus(ppn.getPpnStatus());

            response.add(ppnList);
        }

        return response;
    }

    public List<PpnList> listPpnByStatus(String ppnStatus, int limit, int offset){
        List<Ppn> mPpnList = ppnRepository.listPpnByStatus(ppnStatus, limit, offset);
        List<PpnList> response= new ArrayList<>();

        for(Ppn ppn: mPpnList   ){
            PpnList ppnList = new PpnList();

            ppnList.setId(ppn.getPpnId());
            ppnList.setDealerId(ppn.getDealer().getDealerId());
            ppnList.setPpnDescription(ppn.getDescription());
            ppnList.setPpnRate(ppn.getPpnRate());
            ppnList.setPpnRatePrevious(ppn.getPpnRatePrevious());
            ppnList.setEffectiveStartDate(ppn.getEffectiveStartDate());
            ppnList.setEffectiveEndDate(ppn.getEffectiveEndDate());
            ppnList.setStatus(ppn.getPpnStatus());

            response.add(ppnList);
        }

        return response;
    }

    public Optional<Ppn> getActivePpn(String dealerId, Date queryDate){
        Optional<Ppn> getActivePpn = ppnRepository.getActivePpn(dealerId, queryDate);

        return getActivePpn;
    }

    public Dealer getdealer(String id){
        Optional<Dealer> getDealerid = dealerRepository.findById(id);
        Dealer dealer = getDealerid.get();

        return dealer;
    }

    public Optional dataPpn(String id, String dealerId, String ppnDescription, Float ppnRate,
        Float ppnRatePrevious, String effectiveStartDate, String effectiveEndDate, String status){

        ResDataPpn resPpnDto = new ResDataPpn();

        resPpnDto.setPpnId(id);
        resPpnDto.setDealerId(dealerId);
        resPpnDto.setPpnDescription(ppnDescription);
        resPpnDto.setPpnRate(ppnRate);
        resPpnDto.setPpnRatePrevious(ppnRatePrevious);
        resPpnDto.setEffectiveStartDate(effectiveStartDate);
        resPpnDto.setEffectiveEndDate(effectiveEndDate);
        resPpnDto.setPpnStatus(status);

        Optional dataPpn = Optional.of(resPpnDto);

        return dataPpn;
    }

    public Optional<Dealer> findDealerId(String dealerId){
        Optional<Dealer> getDealerid = dealerRepository.findById(dealerId);

        return getDealerid;
    }
}
