package com.jaycverg.desktop.notification.service

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class NotificationService
{
    List getList()
    {
        DBService db = DBService.instance

        def result = [:]

        db.query('''
            SELECT n.id, n.message, nd.time, nd.minutes
            FROM notification n
              LEFT JOIN notification_detail nd ON nd.parent_id = n.id
        ''').each { item ->
            def data = result[item.id]
            if (!data) {
                data = [message: item.message, items: []]
                result[item.id] = data
            }

            data.items << [time: splitIntString(item.time), minutes: splitIntString(item.minutes)]
        }

        return result.values() as List
    }

    private def splitIntString(def string)
    {
        return string.split(/\s*,\s*/).collect { Integer.parseInt(it) }
    }
}

