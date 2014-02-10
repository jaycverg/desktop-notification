package com.jaycverg.desktop.notification

import com.jaycverg.desktop.notification.service.HolidayService

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
class NotificationContext 
{
    HolidayService service = new HolidayService()
    List holidays = []
    Map holidayIndex = [:]

    void init()
    {
        holidays = service.getHolidays()
        holidayIndex.clear()

        holidays.each {
            holidayIndex[it.date] = it
        }
    }

    boolean isHoliday(Date date)
    {
        return holidayIndex[date] != null
    }
}

