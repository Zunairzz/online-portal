package com.ams.model;

import com.ams.model.BaseModel;


public class FmrCategory
        extends BaseModel {
    public String CATID;
    public String CATNAME;
    public String CATSORT;
    public String CATCALCULATION;
    public String ISDELETED;


    public String getCATID() {
        return this.CATID;

    }


    public void setCATID(String CATID) {
        this.CATID = CATID;

    }


    public String getCATNAME() {
        return this.CATNAME;

    }


    public void setCATNAME(String CATNAME) {
        this.CATNAME = CATNAME;

    }


    public String getCATSORT() {
        return this.CATSORT;

    }


    public void setCATSORT(String CATSORT) {
        this.CATSORT = CATSORT;

    }


    public String getCATCALCULATION() {
        return this.CATCALCULATION;

    }


    public void setCATCALCULATION(String CATCALCULATION) {
        this.CATCALCULATION = CATCALCULATION;

    }


    public String getISDELETED() {
        return this.ISDELETED;

    }


    public void setISDELETED(String ISDELETED) {
        this.ISDELETED = ISDELETED;

    }

}