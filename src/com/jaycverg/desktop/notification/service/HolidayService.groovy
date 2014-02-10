package com.jaycverg.desktop.notification.service

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class HolidayService
{
    List getHolidays()
    {
        DBService db = DBService.instance

        return db.query('''
                SELECT * FROM holiday WHERE YEAR(date) = YEAR(NOW())
            ''')
    }
}
