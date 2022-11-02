/*     */ package WEB-INF.classes.com.ams.model.fmr;
/*     */ 
/*     */ import com.ams.model.BaseModel;
/*     */ import com.ams.model.FundPlanDef;
/*     */ import com.ams.model.Member;
/*     */ import com.ams.model.fmr.FmrEconomyDto;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FundDefinitionReportModel
/*     */   extends BaseModel {
/*     */   public String economy_head1;
/*     */   public String economy_head2;
/*     */   public String economy_head3;
/*     */   public String economy_comm1;
/*     */   public String economy_comm2;
/*     */   public String economy_comm3;
/*     */   public String currMonth;
/*     */   public String prevMonth;
/*     */   public String isl_Economy_head1;
/*     */   public String isl_Economy_head2;
/*     */   public String isl_Economy_head3;
/*     */   public String isl_Economy_comm1;
/*     */   public String isl_Economy_comm2;
/*     */   public String isl_Economy_comm3;
/*     */   public List<FmrEconomyDto> economySummary;
/*  28 */   public FundPlanDef planDef = new FundPlanDef(); public List<FmrEconomyDto> economyTable2; public List<FmrEconomyDto> economyTable3; public List<FmrEconomyDto> islamicEconomyTable3; public String id; public String transDate; public String fundCode; public String fundName; public String commentary; public String objective; public String memberString; public List<String> memberList; public List<Member> members; public String launchDate; public String weighted_avg; public String leverage;
/*     */   
/*     */   public String getIsl_Economy_head1() {
/*  31 */     return this.isl_Economy_head1;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_head1(String isl_Economy_head1) {
/*  35 */     this.isl_Economy_head1 = isl_Economy_head1;
/*     */   }
/*     */   
/*     */   public String getIsl_Economy_head2() {
/*  39 */     return this.isl_Economy_head2;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_head2(String isl_Economy_head2) {
/*  43 */     this.isl_Economy_head2 = isl_Economy_head2;
/*     */   }
/*     */   
/*     */   public String getIsl_Economy_head3() {
/*  47 */     return this.isl_Economy_head3;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_head3(String isl_Economy_head3) {
/*  51 */     this.isl_Economy_head3 = isl_Economy_head3;
/*     */   }
/*     */   
/*     */   public String getIsl_Economy_comm1() {
/*  55 */     return this.isl_Economy_comm1;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_comm1(String isl_Economy_comm1) {
/*  59 */     this.isl_Economy_comm1 = isl_Economy_comm1;
/*     */   }
/*     */   
/*     */   public String getIsl_Economy_comm2() {
/*  63 */     return this.isl_Economy_comm2;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_comm2(String isl_Economy_comm2) {
/*  67 */     this.isl_Economy_comm2 = isl_Economy_comm2;
/*     */   }
/*     */   
/*     */   public String getIsl_Economy_comm3() {
/*  71 */     return this.isl_Economy_comm3;
/*     */   }
/*     */   
/*     */   public void setIsl_Economy_comm3(String isl_Economy_comm3) {
/*  75 */     this.isl_Economy_comm3 = isl_Economy_comm3;
/*     */   }
/*     */   
/*     */   public List<FmrEconomyDto> getIslamicEconomyTable3() {
/*  79 */     return this.islamicEconomyTable3;
/*     */   }
/*     */   
/*     */   public void setIslamicEconomyTable3(List<FmrEconomyDto> islamicEconomyTable3) {
/*  83 */     this.islamicEconomyTable3 = islamicEconomyTable3;
/*     */   }
/*     */   
/*     */   public String getWeighted_avg() {
/*  87 */     return this.weighted_avg;
/*     */   }
/*     */   
/*     */   public void setWeighted_avg(String weighted_avg) {
/*  91 */     this.weighted_avg = weighted_avg;
/*     */   }
/*     */   
/*     */   public String getLeverage() {
/*  95 */     return this.leverage;
/*     */   }
/*     */   
/*     */   public void setLeverage(String leverage) {
/*  99 */     this.leverage = leverage;
/*     */   }
/*     */   
/*     */   public String getEconomy_head1() {
/* 103 */     return this.economy_head1;
/*     */   }
/*     */   
/*     */   public void setEconomy_head1(String economy_head1) {
/* 107 */     this.economy_head1 = economy_head1;
/*     */   }
/*     */   
/*     */   public String getEconomy_head2() {
/* 111 */     return this.economy_head2;
/*     */   }
/*     */   
/*     */   public void setEconomy_head2(String economy_head2) {
/* 115 */     this.economy_head2 = economy_head2;
/*     */   }
/*     */   
/*     */   public String getEconomy_head3() {
/* 119 */     return this.economy_head3;
/*     */   }
/*     */   
/*     */   public void setEconomy_head3(String economy_head3) {
/* 123 */     this.economy_head3 = economy_head3;
/*     */   }
/*     */   
/*     */   public String getCurrMonth() {
/* 127 */     return this.currMonth;
/*     */   }
/*     */   
/*     */   public void setCurrMonth(String currMonth) {
/* 131 */     this.currMonth = currMonth;
/*     */   }
/*     */   
/*     */   public String getPrevMonth() {
/* 135 */     return this.prevMonth;
/*     */   }
/*     */   
/*     */   public void setPrevMonth(String prevMonth) {
/* 139 */     this.prevMonth = prevMonth;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FmrEconomyDto> getEconomyTable2() {
/* 144 */     return this.economyTable2;
/*     */   }
/*     */   
/*     */   public void setEconomyTable2(List<FmrEconomyDto> economyTable2) {
/* 148 */     this.economyTable2 = economyTable2;
/*     */   }
/*     */   
/*     */   public List<FmrEconomyDto> getEconomyTable3() {
/* 152 */     return this.economyTable3;
/*     */   }
/*     */   
/*     */   public void setEconomyTable3(List<FmrEconomyDto> economyTable3) {
/* 156 */     this.economyTable3 = economyTable3;
/*     */   }
/*     */   public List<FmrEconomyDto> getEconomySummary() {
/* 159 */     return this.economySummary;
/*     */   }
/*     */   
/*     */   public void setEconomySummary(List<FmrEconomyDto> economySummary) {
/* 163 */     this.economySummary = economySummary;
/*     */   }
/*     */   public String getEconomy_comm1() {
/* 166 */     return this.economy_comm1;
/*     */   }
/*     */   
/*     */   public void setEconomy_comm1(String economy_comm1) {
/* 170 */     this.economy_comm1 = economy_comm1;
/*     */   }
/*     */   
/*     */   public String getEconomy_comm2() {
/* 174 */     return this.economy_comm2;
/*     */   }
/*     */   
/*     */   public void setEconomy_comm2(String economy_comm2) {
/* 178 */     this.economy_comm2 = economy_comm2;
/*     */   }
/*     */   
/*     */   public String getEconomy_comm3() {
/* 182 */     return this.economy_comm3;
/*     */   }
/*     */   
/*     */   public void setEconomy_comm3(String economy_comm3) {
/* 186 */     this.economy_comm3 = economy_comm3;
/*     */   }
/*     */   public FundPlanDef getPlanDef() {
/* 189 */     return this.planDef;
/*     */   }
/*     */   
/*     */   public void setPlanDef(FundPlanDef planDef) {
/* 193 */     this.planDef = planDef;
/*     */   }
/*     */   
/*     */   public String getLaunchDate() {
/* 197 */     return this.launchDate;
/*     */   }
/*     */   
/*     */   public void setLaunchDate(String launchDate) {
/* 201 */     this.launchDate = launchDate;
/*     */   }
/*     */   
/*     */   public String getFundName() {
/* 205 */     return this.fundName;
/*     */   }
/*     */   
/*     */   public void setFundName(String fundName) {
/* 209 */     this.fundName = fundName;
/*     */   }
/*     */   
/*     */   public String getMemberString() {
/* 213 */     return this.memberString;
/*     */   }
/*     */   
/*     */   public void setMemberString(String memberString) {
/* 217 */     this.memberString = memberString;
/*     */   }
/*     */   
/*     */   public List<Member> getMembers() {
/* 221 */     return this.members;
/*     */   }
/*     */   
/*     */   public void setMembers(List<Member> members) {
/* 225 */     this.members = members;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 229 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 233 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getObjective() {
/* 237 */     return this.objective;
/*     */   }
/*     */   
/*     */   public void setObjective(String objective) {
/* 241 */     this.objective = objective;
/*     */   }
/*     */   
/*     */   public List<String> getMemberList() {
/* 245 */     return this.memberList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMemberList(String memberList) {
/* 250 */     if (memberList != null) {
/* 251 */       boolean b = true;
/* 252 */       this.members = new ArrayList<>();
/* 253 */       String newLine = System.getProperty("line.separator");
/* 254 */       this.memberList = Arrays.asList(memberList.split(newLine));
/* 255 */       for (String s : this.memberList) {
/* 256 */         Member m = new Member();
/* 257 */         m.setMemberName(s);
/* 258 */         if (b) {
/* 259 */           m.setColStyle("col-style-0");
/* 260 */           b = false;
/*     */         } else {
/* 262 */           m.setColStyle("col-style-1");
/* 263 */           b = true;
/*     */         } 
/* 265 */         this.members.add(m);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMemberList(List<String> memberList) {
/* 272 */     boolean b = true;
/* 273 */     this.members = new ArrayList<>();
/* 274 */     this.memberList = memberList;
/* 275 */     for (String s : this.memberList) {
/* 276 */       Member m = new Member();
/* 277 */       m.setMemberName(s);
/* 278 */       if (b) {
/* 279 */         m.setColStyle("col-style-0");
/* 280 */         b = false;
/*     */       } else {
/* 282 */         m.setColStyle("col-style-1");
/* 283 */         b = true;
/*     */       } 
/* 285 */       this.members.add(m);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTransDate() {
/* 290 */     return this.transDate;
/*     */   }
/*     */   
/*     */   public void setTransDate(String transDate) {
/* 294 */     this.transDate = transDate;
/*     */   }
/*     */   
/*     */   public String getCommentary() {
/* 298 */     return this.commentary;
/*     */   }
/*     */   
/*     */   public void setCommentary(String commentary) {
/* 302 */     this.commentary = commentary;
/*     */   }
/*     */   
/*     */   public String getFundCode() {
/* 306 */     return this.fundCode;
/*     */   }
/*     */   
/*     */   public void setFundCode(String fundCode) {
/* 310 */     this.fundCode = fundCode;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\fmr\FundDefinitionReportModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */