package com.ams.model;

import com.ams.model.fmr_asset_allocation_model;

import java.util.List;


public class FmrAssetAllocMain {
    String description;
    String totalPrevAlloc;
    String totalCurrAlloc;
    List<fmr_asset_allocation_model> fif_prev_asset_alloc;


    public String getDescription() {
        return this.description;

    }


    public void setDescription(String description) {
        this.description = description;

    }


    public String getTotalPrevAlloc() {
        return this.totalPrevAlloc;

    }


    public void setTotalPrevAlloc(String totalPrevAlloc) {
        this.totalPrevAlloc = totalPrevAlloc;

    }


    public String getTotalCurrAlloc() {
        return this.totalCurrAlloc;

    }


    public void setTotalCurrAlloc(String totalCurrAlloc) {
        this.totalCurrAlloc = totalCurrAlloc;

    }


    public List<fmr_asset_allocation_model> getFif_prev_asset_alloc() {
        return this.fif_prev_asset_alloc;

    }


    public void setFif_prev_asset_alloc(List<fmr_asset_allocation_model> fif_prev_asset_alloc) {
        setTotalPrevAlloc(String.valueOf(Math.round(fif_prev_asset_alloc.stream().mapToDouble(o -> Double.valueOf(o.getPrev_month()).doubleValue()).sum())));
        setTotalCurrAlloc(String.valueOf(Math.round(fif_prev_asset_alloc.stream().mapToDouble(o -> o.getCurr_monthDouble()).sum())));
        this.fif_prev_asset_alloc = fif_prev_asset_alloc;

    }

}