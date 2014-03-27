package com.jaycverg.desktop.notification.experimental;

import com.jaycverg.desktop.notification.App;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class TestNotificationWindow
{

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}

        final JFrame window = new JFrame(App.getName());
        
        ImageIcon icon = new ImageIcon(
                TestNotificationWindow.class.getResource("../timesheet4.png"),
                App.getDescription());

        window.setIconImage(icon.getImage());
        window.setUndecorated(true);
        window.add(new NotificationPanel(true));
        window.pack();
        window.setLocationRelativeTo(null);

        window.setVisible(true);
    }
    
}
