package com.ams.utility;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.plot.DefaultDrawingSupplier;


public class ChartUtility
        extends DefaultDrawingSupplier {
    public Paint[] paintSequence = new Paint[]{new Color(193, 77, 77), new Color(155, 185, 92), new Color(126, 100, 165), new Color(74, 169, 197), new Color(243, 146, 69), new Color(39, 77, 114), new Color(74, 128, 190)};


    public int paintIndex;


    public int fillPaintIndex;


    public Paint getNextPaint() {
        Paint result = this.paintSequence[this.paintIndex % this.paintSequence.length];

        this.paintIndex++;
        return result;

    }


    public Paint getNextFillPaint() {
        Paint result = this.paintSequence[this.fillPaintIndex % this.paintSequence.length];

        this.fillPaintIndex++;
        return result;

    }

}