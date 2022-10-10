package com.eksadsupport.minilab.dto.ppn;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ResPostPpn {
    private String ppnId;
    private String dealerId;
    private String ahmDealerCode;
    private String ppnDescription;
    private double ppnRate;
    private double ppnRatePrevious;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy HH:mm", timezone = "GMT+7")
    private Date effectiveStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy HH:mm",timezone = "GMT+7")
    private Date effectiveEndDate;
    private String ppnStatus;

    public String getPpnId() {
        return ppnId;
    }

    public void setPpnId(String ppnId) {
        this.ppnId = ppnId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getAhmDealerCode() {
        return ahmDealerCode;
    }

    public void setAhmDealerCode(String ahmDealerCode) {
        this.ahmDealerCode = ahmDealerCode;
    }

    public String getPpnDescription() {
        return ppnDescription;
    }

    public void setPpnDescription(String ppnDescription) {
        this.ppnDescription = ppnDescription;
    }

    public double getPpnRate() {
        return ppnRate;
    }

    public void setPpnRate(double ppnRate) {
        this.ppnRate = ppnRate;
    }

    public double getPpnRatePrevious() {
        return ppnRatePrevious;
    }

    public void setPpnRatePrevious(double ppnRatePrevious) {
        this.ppnRatePrevious = ppnRatePrevious;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getPpnStatus() {
        return ppnStatus;
    }

    public void setPpnStatus(String ppnStatus) {
        this.ppnStatus = ppnStatus;
    }
}
