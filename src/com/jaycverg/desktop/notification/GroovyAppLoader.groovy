package com.jaycverg.desktop.notification

import javax.swing.SwingUtilities

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
class GroovyAppLoader implements AppLoader
{
    void load()
    {
        SwingUtilities.invokeLater {
            new NotificationUI()
        }
    }
}

