package com.ams.utility;

import com.lowagie.text.Image;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;


public class MediaReplacedElementFactory
        implements ReplacedElementFactory {
    private final ReplacedElementFactory superFactory;
    String contextPath;


    public MediaReplacedElementFactory(ReplacedElementFactory superFactory, String contextPath) {
        this.superFactory = superFactory;
        this.contextPath = contextPath;

    }


    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;

        }
        String nodeName = element.getNodeName();
        String className = element.getAttribute("class");
        if ("div".equals(nodeName) && "media".equals(className)) {
            if (!element.hasAttribute("data-src")) {
                System.out.println("media file missing");

            }
            InputStream input = null;
            String url1 = "";

            try {
                url1 = element.getAttribute("data-src");
                if (url1.contains("http") || url1.contains("com.googlecode")) {
                    URL url = new URL(url1);
                    input = url.openStream();

                } else {
                    String item = element.getAttribute("data-src");
                    input = new FileInputStream(this.contextPath + "WEB-INF\\classes\\public\\" + item);

                }
                byte[] bytes = IOUtils.toByteArray(input);
                Image image = Image.getInstance(bytes);
                ITextFSImage iTextFSImage = new ITextFSImage(image);
                if (iTextFSImage != null) {
                    if (cssWidth != -1 || cssHeight != -1) {
                        iTextFSImage.scale(cssWidth, cssHeight);

                    }
                    return (ReplacedElement) new ITextImageElement((FSImage) iTextFSImage);

                }
            } catch (Exception e) {
                throw new RuntimeException("There was a problem trying to read a template embedded graphic. " + url1, e);

            } finally {
                IOUtils.closeQuietly(input);

            }

        }
        return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);

    }


    public void reset() {
        this.superFactory.reset();

    }


    public void remove(Element e) {
        this.superFactory.remove(e);

    }


    public void setFormSubmissionListener(FormSubmissionListener listener) {
        this.superFactory.setFormSubmissionListener(listener);

    }

}