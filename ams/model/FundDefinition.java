package com.ams.model;


import com.ams.model.BaseModel;
import com.ams.model.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FundDefinition
        extends BaseModel {
    public String id;
    public String transDate;
    public String fundCode;
    public String fundName;
    public String commentary;
    public String objective;
    public String memberString;
    public List<String> memberList;
    public List<Member> members;
    public String launchDate;
    public String weightAvg;
    public String leverage;


    public String getWeightAvg() {
        return this.weightAvg;

    }


    public void setWeightAvg(String weightAvg) {
        this.weightAvg = weightAvg;

    }


    public String getLeverage() {
        return this.leverage;

    }


    public void setLeverage(String leverage) {
        this.leverage = leverage;

    }


    public String getLaunchDate() {
        return this.launchDate;

    }


    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;

    }


    public String getFundName() {
        return this.fundName;

    }


    public void setFundName(String fundName) {
        this.fundName = fundName;

    }


    public String getMemberString() {
        return this.memberString;

    }


    public void setMemberString(String memberString) {
        this.memberString = memberString;

    }


    public List<Member> getMembers() {
        return this.members;

    }


    public void setMembers(List<Member> members) {
        this.members = members;

    }


    public String getId() {
        return this.id;

    }


    public void setId(String id) {
        this.id = id;

    }


    public String getObjective() {
        return this.objective;

    }


    public void setObjective(String objective) {
        this.objective = objective;

    }


    public List<String> getMemberList() {
        return this.memberList;

    }


    public void setMemberList(String memberList) {
        if (memberList != null) {
            boolean b = true;
            this.members = new ArrayList<>();
            String newLine = System.getProperty("line.separator");
            this.memberList = Arrays.asList(memberList.split(newLine));
            for (String s : this.memberList) {
                Member m = new Member();
                m.setMemberName(s);
                if (b) {
                    m.setColStyle("col-style-0");
                    b = false;

                } else {
                    m.setColStyle("col-style-1");
                    b = true;

                }
                this.members.add(m);

            }

        }

    }


    public void setMemberList(List<String> memberList) {
        boolean b = true;
        this.members = new ArrayList<>();
        this.memberList = memberList;
        for (String s : this.memberList) {
            Member m = new Member();
            m.setMemberName(s);
            if (b) {
                m.setColStyle("col-style-0");
                b = false;

            } else {
                m.setColStyle("col-style-1");
                b = true;

            }
            this.members.add(m);

        }

    }


    public String getTransDate() {
        return this.transDate;

    }


    public void setTransDate(String transDate) {
        this.transDate = transDate;

    }


    public String getCommentary() {
        return this.commentary;

    }


    public void setCommentary(String commentary) {
        this.commentary = commentary;

    }


    public String getFundCode() {
        return this.fundCode;

    }


    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;

    }

}