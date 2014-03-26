package com.jaycverg.desktop.notification

import java.awt.AWTException
import java.awt.Image
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.event.ActionEvent
import java.net.URL
import java.util.Calendar
import javax.swing.ImageIcon
import javax.swing.JOptionPane
import javax.swing.Timer
import javax.swing.SwingUtilities;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
class NotificationUI 
{
    private TrayIcon trayIcon
    private NotificationContext context = new NotificationContext()

    void init()
    {
        SwingUtilities.invokeLater {
            doInit()
        }
    }

    private void doInit() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            throw new RuntimeException("SystemTray is not supported")
        }

        final PopupMenu popup = new PopupMenu()
        trayIcon = new TrayIcon(createImage("timesheet4.png", "tray icon"))
        final SystemTray tray = SystemTray.getSystemTray()

        trayIcon.setImageAutoSize(true)
        trayIcon.setToolTip(App.getName())

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About")
        MenuItem exitItem = new MenuItem("Exit")

        //Add components to popup menu
        popup.add(aboutItem)
        popup.addSeparator()
        popup.add(exitItem)

        trayIcon.setPopupMenu(popup)

        try {
            tray.add(trayIcon)
        }
        catch (AWTException e) {
            throw new RuntimeException("TrayIcon could not be added.")
        }

        trayIcon.addActionListener { e ->
            // TODO: display a settings window
        }

        aboutItem.addActionListener { e ->
            JOptionPane.showMessageDialog(null,
                    "Timesheet Notification v1.1 by jaycverg")
        }

        exitItem.addActionListener { e ->
            tray.remove(trayIcon)
            System.exit(0)
        }

        // run the background process every 1 minute
        Timer timer = new Timer(1000 * 60, { checkForUpdates() })
        timer.setInitialDelay(0)
        timer.start()
    }

    //Obtain the image URL
    private Image createImage(String path, String description)
    {
        URL imageURL = NotificationUI.class.getResource(path)

        if (!imageURL) {
            System.err.println("Resource not found: " + path)
            return null
        }
        else {
            return (new ImageIcon(imageURL, description)).getImage()
        }
    }

    private void checkForUpdates()
    {
        Calendar cal = Calendar.getInstance()

        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        int time = cal.get(Calendar.HOUR_OF_DAY)
        int minutes = cal.get(Calendar.MINUTE)

        def msgs = []

        context.notifications.each { notification ->
            println "checking $notification"
            for (def item : notification.items) {
                if (isValueIn(time, item.time) && isValueIn(minutes, item.minutes)) {
                    msgs << notification.message
                    break;
                }
            }
        }

        // weekend checker
//        if (isValueIn(dayOfWeek, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY)
//                && isValueIn(time, 21) && isValueIn(minutes, 15, 30, 45, 50, 55, 58, 59))
//        {
//            msgs << "Don't forget to prepare your weekly task report.\n"
//        }

        // cutof checker
//        int cutOffDay = getCutOffDay(dayOfMonth)
//        if (dayOfMonth == cutOffDay && isValueIn(time, 21)
//                && isValueIn(minutes, 15, 30, 45, 50, 55, 58, 59))
//        {
//            msgs << "Don't forget to submit your timesheet.\n"
//        }

        if (!msgs.empty) {
            trayIcon.displayMessage("Reminder", msgs.join('\n'), TrayIcon.MessageType.INFO)
        }
    }

    private int getCutOffDay(int dayOfMonth)
    {
        Calendar cal = Calendar.getInstance()
        if (dayOfMonth == 15) {
            return dayOfMonth
        }

        if (dayOfMonth < 15) {
            cal.set(Calendar.DAY_OF_MONTH, 15)
        }
        else {
            int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            cal.set(Calendar.DAY_OF_MONTH, maxDays)
        }

        while (isNonWorkingDay(cal)) {
            cal.add(Calendar.DAY_OF_MONTH, -1)
        }

        return cal.get(Calendar.DAY_OF_MONTH)
    }

    private boolean isNonWorkingDay(Calendar cal)
    {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

        return dayOfWeek == Calendar.SATURDAY ||
                dayOfWeek == Calendar.SUNDAY ||
                context.isHoliday(cal.getTime())
    }

    private boolean isValueIn(int value, List items)
    {
        for (int item : items) {
            if (value == item) {
                return true
            }
        }
        return false
    }
}

