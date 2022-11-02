/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Paint;
/*    */ import org.jfree.chart.plot.DefaultDrawingSupplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChartUtility
/*    */   extends DefaultDrawingSupplier
/*    */ {
/* 23 */   public Paint[] paintSequence = new Paint[] { new Color(193, 77, 77), new Color(155, 185, 92), new Color(126, 100, 165), new Color(74, 169, 197), new Color(243, 146, 69), new Color(39, 77, 114), new Color(74, 128, 190) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int paintIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int fillPaintIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Paint getNextPaint() {
/* 44 */     Paint result = this.paintSequence[this.paintIndex % this.paintSequence.length];
/*    */     
/* 46 */     this.paintIndex++;
/* 47 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Paint getNextFillPaint() {
/* 52 */     Paint result = this.paintSequence[this.fillPaintIndex % this.paintSequence.length];
/*    */     
/* 54 */     this.fillPaintIndex++;
/* 55 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\ChartUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */