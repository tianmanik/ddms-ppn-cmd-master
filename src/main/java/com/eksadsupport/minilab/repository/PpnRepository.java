package com.eksadsupport.minilab.repository;

import com.eksadsupport.minilab.domain.Ppn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PpnRepository extends JpaRepository<Ppn, String> {
    @Query(value = "select * from mst_ppn where dealer_code iLike %:dealerId% and ppn_status=:ppnStatus limit :limit offset :offset  ",nativeQuery = true)
    List<Ppn> listPpnAll(String dealerId, String ppnStatus, int limit, int offset);

    @Query(value = "select * from mst_ppn where ppn_status=:ppnStatus limit :limit offset :offset  ",nativeQuery = true)
    List<Ppn> listPpnByStatus(String ppnStatus, int limit, int offset);

    @Query(value = "select * from mst_ppn where dealer_code =:dealerId limit :limit offset :offset  ",nativeQuery = true)
    List<Ppn> listPpnByDealerId(String dealerId, int limit, int offset);

    @Query(value = "select * from mst_ppn where dealer_code=:dealerId and effective_start_date >=:queryDate and ppn_status='Active' limit 1" , nativeQuery = true)
    Optional<Ppn> getActivePpn(String dealerId, Date queryDate);

    @Query(value="select * from mst_ppn where dealer_code iLike %:dealerId%",nativeQuery = true)
    Optional<Ppn> getAllPpnByDealerId(String dealerId);
}
