package com.jaycverg.desktop.notification.experimental;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class TestNotificationPanel
{

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}

        final JWindow window = new JWindow();
        window.setOpacity(0.95f);
        window.add(new NotificationPanel());
        window.pack();

        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Dimension scrSize = toolKit.getScreenSize();
        Dimension wSize = window.getSize();

        GraphicsEnvironment gfx = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = gfx.getDefaultScreenDevice();
        Insets scrInsets = toolKit.getScreenInsets(screen.getDefaultConfiguration());

        window.setLocation(
                scrSize.width - wSize.width - 5,
                scrSize.height - scrInsets.bottom);

        Timer t = new Timer(1, e -> {
            if (!animate(window, scrSize, scrInsets)) {
                Timer source = (Timer) e.getSource();
                source.stop();
            }
        });

        window.setVisible(true);
        t.start();
    }

    private static boolean animate(JWindow window, Dimension scrSize, Insets scrInsets)
    {
        Point wLocation = window.getLocation();
        if (wLocation.y <= scrSize.height - window.getHeight() - scrInsets.bottom) {
            return false;
        }

        wLocation.y--;
        window.setLocation(wLocation);
        
        return true;
    }
    
}
