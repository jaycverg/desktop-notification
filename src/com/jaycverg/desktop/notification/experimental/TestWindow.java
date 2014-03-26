package com.jaycverg.desktop.notification.experimental;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class TestWindow
{

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}

        final JWindow window = new JWindow();
        window.setOpacity(0.9f);
        window.add(new NotificationPanel());
        window.pack();
        
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension wSize = window.getSize();

        window.setLocation(scrSize.width - wSize.width, scrSize.height);

        Timer t = new Timer(5, e -> {
            if (!animate(window, scrSize)) {
                Timer source = (Timer) e.getSource();
                source.stop();
            }
        });

        window.setVisible(true);
        t.start();
    }

    private static boolean animate(JWindow window, Dimension scrSize)
    {
        Point wLocation = window.getLocation();
        if (wLocation.y <= scrSize.height - window.getHeight()) {
            return false;
        }

        wLocation.y--;
        window.setLocation(wLocation);
        
        return true;
    }
    
}
