package com.jaycverg.desktop.notification;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class App
{

    public static void main(String[] args) throws Exception
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        NotificationUI notificationUI = new NotificationUI();
        notificationUI.init();
    }

    public static String getName()
    {
        return "Desktop Notification v1.0";
    }

    public static String getDescription()
    {
        return "Desktop notification client.";
    }

}
