package com.ams.model.fmr;


import com.ams.model.BaseModel;
import com.ams.model.FundPlanDef;
import com.ams.model.Member;
import com.ams.model.fmr.FmrEconomyDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FundDefinitionReportModel
        extends BaseModel {
    public String economy_head1;
    public String economy_head2;
    public String economy_head3;
    public String economy_comm1;
    public String economy_comm2;
    public String economy_comm3;
    public String currMonth;
    public String prevMonth;
    public String isl_Economy_head1;
    public String isl_Economy_head2;
    public String isl_Economy_head3;
    public String isl_Economy_comm1;
    public String isl_Economy_comm2;
    public String isl_Economy_comm3;
    public List<FmrEconomyDto> economySummary;
    public FundPlanDef planDef = new FundPlanDef();
    public List<FmrEconomyDto> economyTable2;
    public List<FmrEconomyDto> economyTable3;
    public List<FmrEconomyDto> islamicEconomyTable3;
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
    public String weighted_avg;
    public String leverage;


    public String getIsl_Economy_head1() {
        return this.isl_Economy_head1;

    }


    public void setIsl_Economy_head1(String isl_Economy_head1) {
        this.isl_Economy_head1 = isl_Economy_head1;

    }


    public String getIsl_Economy_head2() {
        return this.isl_Economy_head2;

    }


    public void setIsl_Economy_head2(String isl_Economy_head2) {
        this.isl_Economy_head2 = isl_Economy_head2;

    }


    public String getIsl_Economy_head3() {
        return this.isl_Economy_head3;

    }


    public void setIsl_Economy_head3(String isl_Economy_head3) {
        this.isl_Economy_head3 = isl_Economy_head3;

    }


    public String getIsl_Economy_comm1() {
        return this.isl_Economy_comm1;

    }


    public void setIsl_Economy_comm1(String isl_Economy_comm1) {
        this.isl_Economy_comm1 = isl_Economy_comm1;

    }


    public String getIsl_Economy_comm2() {
        return this.isl_Economy_comm2;

    }


    public void setIsl_Economy_comm2(String isl_Economy_comm2) {
        this.isl_Economy_comm2 = isl_Economy_comm2;

    }


    public String getIsl_Economy_comm3() {
        return this.isl_Economy_comm3;

    }


    public void setIsl_Economy_comm3(String isl_Economy_comm3) {
        this.isl_Economy_comm3 = isl_Economy_comm3;

    }


    public List<FmrEconomyDto> getIslamicEconomyTable3() {
        return this.islamicEconomyTable3;

    }


    public void setIslamicEconomyTable3(List<FmrEconomyDto> islamicEconomyTable3) {
        this.islamicEconomyTable3 = islamicEconomyTable3;

    }


    public String getWeighted_avg() {
        return this.weighted_avg;

    }


    public void setWeighted_avg(String weighted_avg) {
        this.weighted_avg = weighted_avg;

    }


    public String getLeverage() {
        return this.leverage;

    }


    public void setLeverage(String leverage) {
        this.leverage = leverage;

    }


    public String getEconomy_head1() {
        return this.economy_head1;

    }


    public void setEconomy_head1(String economy_head1) {
        this.economy_head1 = economy_head1;

    }


    public String getEconomy_head2() {
        return this.economy_head2;

    }


    public void setEconomy_head2(String economy_head2) {
        this.economy_head2 = economy_head2;

    }


    public String getEconomy_head3() {
        return this.economy_head3;

    }


    public void setEconomy_head3(String economy_head3) {
        this.economy_head3 = economy_head3;

    }


    public String getCurrMonth() {
        return this.currMonth;

    }


    public void setCurrMonth(String currMonth) {
        this.currMonth = currMonth;

    }


    public String getPrevMonth() {
        return this.prevMonth;

    }


    public void setPrevMonth(String prevMonth) {
        this.prevMonth = prevMonth;

    }


    public List<FmrEconomyDto> getEconomyTable2() {
        return this.economyTable2;

    }


    public void setEconomyTable2(List<FmrEconomyDto> economyTable2) {
        this.economyTable2 = economyTable2;

    }


    public List<FmrEconomyDto> getEconomyTable3() {
        return this.economyTable3;

    }


    public void setEconomyTable3(List<FmrEconomyDto> economyTable3) {
        this.economyTable3 = economyTable3;

    }


    public List<FmrEconomyDto> getEconomySummary() {
        return this.economySummary;

    }


    public void setEconomySummary(List<FmrEconomyDto> economySummary) {
        this.economySummary = economySummary;

    }


    public String getEconomy_comm1() {
        return this.economy_comm1;

    }


    public void setEconomy_comm1(String economy_comm1) {
        this.economy_comm1 = economy_comm1;

    }


    public String getEconomy_comm2() {
        return this.economy_comm2;

    }


    public void setEconomy_comm2(String economy_comm2) {
        this.economy_comm2 = economy_comm2;

    }


    public String getEconomy_comm3() {
        return this.economy_comm3;

    }


    public void setEconomy_comm3(String economy_comm3) {
        this.economy_comm3 = economy_comm3;

    }


    public FundPlanDef getPlanDef() {
        return this.planDef;

    }


    public void setPlanDef(FundPlanDef planDef) {
        this.planDef = planDef;

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