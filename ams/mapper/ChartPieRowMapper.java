/*    */ package WEB-INF.classes.com.ams.mapper;
/*    */ 
/*    */ import com.googlecode.charts4j.Color;
/*    */ import com.googlecode.charts4j.Slice;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChartPieRowMapper
/*    */   implements RowMapper<Object>
/*    */ {
/*    */   public Slice[] mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 23 */     List<Slice> list = new ArrayList<>();
/* 24 */     list.add(Slice.newSlice(rs.getInt("VV"), Color.newColor("CACACA"), rs.getString("RATING")));
/* 25 */     return list.<Slice>toArray(new Slice[list.size()]);
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\ams\mapper\ChartPieRowMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */