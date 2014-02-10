package com.jaycverg.desktop.notification;

import groovy.lang.GroovyClassLoader;
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

        ClassLoader cl = new GroovyClassLoader(App.class.getClassLoader());
        Class<AppLoader> loaderClass = (Class<AppLoader>) cl
                .loadClass("com.jaycverg.desktop.notification.GroovyAppLoader");

        loaderClass.newInstance().load();
    }

}
