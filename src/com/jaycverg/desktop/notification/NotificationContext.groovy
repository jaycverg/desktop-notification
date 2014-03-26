package com.jaycverg.desktop.notification

import com.jaycverg.desktop.notification.service.*

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
class NotificationContext 
{
    HolidayService holidaySvc = new HolidayService()
    NotificationService notificationSvc = new NotificationService()

    List holidays
    Map holidayIndex

    List notifications


    NotificationContext()
    {
        init();
    }

    void init()
    {
        holidays = holidaySvc.list.asImmutable()
        holidayIndex = [:]

        holidays.each {
            holidayIndex[it.date] = it
        }

        holidayIndex = holidayIndex.asImmutable()
        notifications = notificationSvc.list.asImmutable()
    }

    boolean isHoliday(Date date)
    {
        return holidayIndex[date] != null
    }
}

