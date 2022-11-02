/*    */ package WEB-INF.classes.com.ams.utility;
/*    */ 
/*    */ import com.lowagie.text.Image;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.w3c.dom.Element;
/*    */ import org.xhtmlrenderer.extend.FSImage;
/*    */ import org.xhtmlrenderer.extend.ReplacedElement;
/*    */ import org.xhtmlrenderer.extend.ReplacedElementFactory;
/*    */ import org.xhtmlrenderer.extend.UserAgentCallback;
/*    */ import org.xhtmlrenderer.layout.LayoutContext;
/*    */ import org.xhtmlrenderer.pdf.ITextFSImage;
/*    */ import org.xhtmlrenderer.pdf.ITextImageElement;
/*    */ import org.xhtmlrenderer.render.BlockBox;
/*    */ import org.xhtmlrenderer.simple.extend.FormSubmissionListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MediaReplacedElementFactory
/*    */   implements ReplacedElementFactory
/*    */ {
/*    */   private final ReplacedElementFactory superFactory;
/*    */   String contextPath;
/*    */   
/*    */   public MediaReplacedElementFactory(ReplacedElementFactory superFactory, String contextPath) {
/* 34 */     this.superFactory = superFactory;
/* 35 */     this.contextPath = contextPath;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
/* 41 */     Element element = blockBox.getElement();
/* 42 */     if (element == null) {
/* 43 */       return null;
/*    */     }
/* 45 */     String nodeName = element.getNodeName();
/* 46 */     String className = element.getAttribute("class");
/* 47 */     if ("div".equals(nodeName) && "media".equals(className)) {
/* 48 */       if (!element.hasAttribute("data-src")) {
/* 49 */         System.out.println("media file missing");
/*    */       }
/* 51 */       InputStream input = null;
/* 52 */       String url1 = "";
/*    */       try {
/* 54 */         url1 = element.getAttribute("data-src");
/* 55 */         if (url1.contains("http") || url1.contains("com.googlecode")) {
/* 56 */           URL url = new URL(url1);
/* 57 */           input = url.openStream();
/*    */         } else {
/* 59 */           String item = element.getAttribute("data-src");
/* 60 */           input = new FileInputStream(this.contextPath + "WEB-INF\\classes\\public\\" + item);
/*    */         } 
/* 62 */         byte[] bytes = IOUtils.toByteArray(input);
/* 63 */         Image image = Image.getInstance(bytes);
/* 64 */         ITextFSImage iTextFSImage = new ITextFSImage(image);
/* 65 */         if (iTextFSImage != null) {
/* 66 */           if (cssWidth != -1 || cssHeight != -1) {
/* 67 */             iTextFSImage.scale(cssWidth, cssHeight);
/*    */           }
/* 69 */           return (ReplacedElement)new ITextImageElement((FSImage)iTextFSImage);
/*    */         } 
/* 71 */       } catch (Exception e) {
/* 72 */         throw new RuntimeException("There was a problem trying to read a template embedded graphic. " + url1, e);
/*    */       } finally {
/* 74 */         IOUtils.closeQuietly(input);
/*    */       } 
/*    */     } 
/* 77 */     return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 82 */     this.superFactory.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(Element e) {
/* 87 */     this.superFactory.remove(e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFormSubmissionListener(FormSubmissionListener listener) {
/* 92 */     this.superFactory.setFormSubmissionListener(listener);
/*    */   }
/*    */ }


/* Location:              D:\Tasks\02-11-2022\webservice.war!\WEB-INF\classes\com\am\\utility\MediaReplacedElementFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */