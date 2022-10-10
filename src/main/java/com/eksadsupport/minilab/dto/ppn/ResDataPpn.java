package com.eksadsupport.minilab.dto.ppn;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResDataPpn {
    private String ppnId;
    private String dealerId;
    private String ppnDescription;
    private double ppnRate;
    private double ppnRatePrevious;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy HH:mm", timezone="GMT+7")
    private String effectiveStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy HH:mm", timezone="GMT+7")
    private String effectiveEndDate;
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

    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public String getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(String effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getPpnStatus() {
        return ppnStatus;
    }

    public void setPpnStatus(String ppnStatus) {
        this.ppnStatus = ppnStatus;
    }
}
