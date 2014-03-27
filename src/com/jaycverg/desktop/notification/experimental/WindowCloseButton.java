package com.jaycverg.desktop.notification.experimental;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import javax.swing.JButton;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class WindowCloseButton extends JButton
{

    private Color lineColor = Color.LIGHT_GRAY;
    private Color linePressedColor = Color.GRAY;
    private Color borderColor = Color.LIGHT_GRAY;
    private Color borderPressedColor = Color.GRAY;

    private boolean hovered;
    private boolean pressed;

    public WindowCloseButton()
    {
        setOpaque(false);
        setPreferredSize(new Dimension(24, 24));
        setFocusable(true);
        
        addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseEntered(MouseEvent e)
            {
                hovered = true;
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                hovered = false;
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                pressed = false;
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension cSize = getSize();
        double borderWidth = 1;

        if (hovered) {
            g2.setColor(pressed ? borderPressedColor : borderColor);

            g2.draw(new Arc2D.Double(
                    borderWidth, borderWidth,
                    cSize.width - borderWidth * 2.0,
                    cSize.height - borderWidth * 2.0,
                    0, 360,
                    Arc2D.Double.CHORD));
        }

        g2.setColor(pressed ? linePressedColor : lineColor);

        int lineXOffset = (int) (cSize.width * 0.3);
        int lineYOffset = (int) (cSize.height * 0.3);

        g2.drawLine(lineXOffset, lineYOffset, cSize.width - lineXOffset, cSize.height - lineYOffset);
        g2.drawLine(cSize.width - lineXOffset, lineYOffset, lineXOffset, cSize.height - lineYOffset);
    }

    //<editor-fold defaultstate="collapsed" desc=" Getters/Setters ">
    /**
     * @return the lineColor
     */
    public Color getLineColor()
    {
        return lineColor;
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
    }

    /**
     * @return the linePressedColor
     */
    public Color getLinePressedColor()
    {
        return linePressedColor;
    }

    /**
     * @param linePressedColor the linePressedColor to set
     */
    public void setLinePressedColor(Color linePressedColor)
    {
        this.linePressedColor = linePressedColor;
    }

    /**
     * @return the borderColor
     */
    public Color getBorderColor()
    {
        return borderColor;
    }

    /**
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }

    /**
     * @return the borderPressedColor
     */
    public Color getBorderPressedColor()
    {
        return borderPressedColor;
    }

    /**
     * @param borderPressedColor the borderPressedColor to set
     */
    public void setBorderPressedColor(Color borderPressedColor)
    {
        this.borderPressedColor = borderPressedColor;
    }
    //</editor-fold>

}
