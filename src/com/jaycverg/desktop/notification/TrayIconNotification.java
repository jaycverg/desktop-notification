package com.jaycverg.desktop.notification;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class TrayIconNotification
{

    private TrayIcon trayIcon;

    public TrayIconNotification()
    {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage("bulb.gif", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Timesheet Notification");

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        }
        catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "This dialog box is run from System Tray");
        });

        aboutItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Timesheet Notification v1.0 by jaycverg");
        });

        exitItem.addActionListener((ActionEvent e) -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        // run the background process every 1 minute
        Timer timer = new Timer(1000 * 60, this::checkForUpdates);
        timer.setInitialDelay(0);
        timer.start();
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description)
    {
        URL imageURL = TrayIconNotification.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        }
        else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    private void checkForUpdates(ActionEvent e)
    {
        Calendar cal = Calendar.getInstance();

        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int time = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);

        StringBuilder msg = new StringBuilder();

        // time checker
        if (isValueIn(time, 8, 12) && isValueIn(minutes, 15, 30, 45, 50, 55, 58, 59)) {
            msg.append("Don't forget to timein.\n");
        }
        else if (time == 21 && isValueIn(minutes, 45, 50, 58, 59)) {
            msg.append("Don't forget to timeout.\n");
        }

        // weekend checker
        if (isValueIn(dayOfWeek, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY)
                && isValueIn(time, 21) && isValueIn(minutes, 15, 30, 45, 50, 55, 58, 59))
        {
            msg.append("Don't forget to prepare your weekly task report.\n");
        }

        // cutof checker
        int cutOffDay = getCutOffDay(dayOfMonth);
        if (dayOfMonth == cutOffDay && isValueIn(time, 21)
                && isValueIn(minutes, 15, 30, 45, 50, 55, 58, 59))
        {
            msg.append("Don't forget to submit your timesheet.\n");
        }

        if (msg.length() > 0) {
            trayIcon.displayMessage("Reminder", msg.toString(), TrayIcon.MessageType.INFO);
        }
    }

    private int getCutOffDay(int dayOfMonth)
    {
        Calendar cal = Calendar.getInstance();
        if (dayOfMonth == 15) {
            return dayOfMonth;
        }

        if (dayOfMonth < 15) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
        }
        else {
            int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, maxDays);
        }

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int cutOffDay = cal.get(Calendar.DAY_OF_MONTH);

        if (dayOfWeek == Calendar.SATURDAY) {
            cutOffDay--;
        }
        else if (dayOfWeek == Calendar.SUNDAY) {
            cutOffDay -= 2;
        }

        return cutOffDay;
    }

    private boolean isValueIn(int value, int ... items)
    {
        for (int item : items) {
            if (value == item) {
                return true;
            }
        }
        return false;
    }
}
