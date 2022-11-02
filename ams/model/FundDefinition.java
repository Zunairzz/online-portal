/*     */ package WEB-INF.classes.com.ams.model;
/*     */ 
/*     */ import com.ams.model.BaseModel;
/*     */ import com.ams.model.Member;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FundDefinition
/*     */   extends BaseModel
/*     */ {
/*     */   public String id;
/*     */   public String transDate;
/*     */   public String fundCode;
/*     */   public String fundName;
/*     */   public String commentary;
/*     */   public String objective;
/*     */   public String memberString;
/*     */   public List<String> memberList;
/*     */   public List<Member> members;
/*     */   public String launchDate;
/*     */   public String weightAvg;
/*     */   public String leverage;
/*     */   
/*     */   public String getWeightAvg() {
/*  30 */     return this.weightAvg;
/*     */   }
/*     */   
/*     */   public void setWeightAvg(String weightAvg) {
/*  34 */     this.weightAvg = weightAvg;
/*     */   }
/*     */   
/*     */   public String getLeverage() {
/*  38 */     return this.leverage;
/*     */   }
/*     */   
/*     */   public void setLeverage(String leverage) {
/*  42 */     this.leverage = leverage;
/*     */   }
/*     */   public String getLaunchDate() {
/*  45 */     return this.launchDate;
/*     */   }
/*     */   
/*     */   public void setLaunchDate(String launchDate) {
/*  49 */     this.launchDate = launchDate;
/*     */   }
/*     */   
/*     */   public String getFundName() {
/*  53 */     return this.fundName;
/*     */   }
/*     */   
/*     */   public void setFundName(String fundName) {
/*  57 */     this.fundName = fundName;
/*     */   }
/*     */   
/*     */   public String getMemberString() {
/*  61 */     return this.memberString;
/*     */   }
/*     */   
/*     */   public void setMemberString(String memberString) {
/*  65 */     this.memberString = memberString;
/*     */   }
/*     */   
/*     */   public List<Member> getMembers() {
/*  69 */     return this.members;
/*     */   }
/*     */   
/*     */   public void setMembers(List<Member> members) {
/*  73 */     this.members = members;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  77 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  81 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getObjective() {
/*  85 */     return this.objective;
/*     */   }
/*     */   
/*     */   public void setObjective(String objective) {
/*  89 */     this.objective = objective;
/*     */   }
/*     */   
/*     */   public List<String> getMemberList() {
/*  93 */     return this.memberList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMemberList(String memberList) {
/*  98 */     if (memberList != null) {
/*  99 */       boolean b = true;
/* 100 */       this.members = new ArrayList<>();
/* 101 */       String newLine = System.getProperty("line.separator");
/* 102 */       this.memberList = Arrays.asList(memberList.split(newLine));
/* 103 */       for (String s : this.memberList) {
/* 104 */         Member m = new Member();
/* 105 */         m.setMemberName(s);
/* 106 */         if (b) {
/* 107 */           m.setColStyle("col-style-0");
/* 108 */           b = false;
/*     */         } else {
/* 110 */           m.setColStyle("col-style-1");
/* 111 */           b = true;
/*     */         } 
/* 113 */         this.members.add(m);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMemberList(List<String> memberList) {
/* 120 */     boolean b = true;
/* 121 */     this.members = new ArrayList<>();
/* 122 */     this.memberList = memberList;
/* 123 */     for (String s : this.memberList) {
/* 124 */       Member m = new Member();
/* 125 */       m.setMemberName(s);
/* 126 */       if (b) {
/* 127 */         m.setColStyle("col-style-0");
/* 128 */         b = false;
/*     */       } else {
/* 130 */         m.setColStyle("col-style-1");
/* 131 */         b = true;
/*     */       } 
/* 133 */       this.members.add(m);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTransDate() {
/* 138 */     return this.transDate;
/*     */   }
/*     */   
/*     */   public void setTransDate(String transDate) {
/* 142 */     this.transDate = transDate;
/*     */   }
/*     */   
/*     */   public String getCommentary() {
/* 146 */     return this.commentary;
/*     */   }
/*     */   
/*     */   public void setCommentary(String commentary) {
/* 150 */     this.commentary = commentary;
/*     */   }
/*     */   
/*     */   public String getFundCode() {
/* 154 */     return this.fundCode;
/*     */   }
/*     */   
/*     */   public void setFundCode(String fundCode) {
/* 158 */     this.fundCode = fundCode;
/*     */   }
/*     */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\model\FundDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */