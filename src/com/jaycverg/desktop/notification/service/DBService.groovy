/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaycverg.desktop.notification.service

import java.io.InputStream
import java.sql.*

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
class DBService
{

    static DBService getInstance()
    {
        return DBServiceHolder.INSTANCE
    }

    private Properties config

    private DBService()
    {
        config = new Properties()
        def is = getClass().getResourceAsStream("/META-INF/app.properties")
        
        try {
            config.load(is)
        }
        catch (Exception ign) {
        }
        finally {
            try { is.close() }catch(Exception e){}
        }
    }

    Connection getConnection() throws Exception
    {
        Class.forName(config."app.db.driver")
        return DriverManager.getConnection(
                config."app.db.url",
                config."app.db.user",
                config."app.db.password")
    }

    void execute(String sql, Object ... params) throws Exception
    {
        def con = getConnection()
        try {
            def stm = con.prepareStatement(sql)
            params.eachWithIndex { p, i ->
                stm.setObject(i+1, p)
            }

            stm.executeUpdate()
        }
        finally {
            try { con.close() } catch(Exception e) {}
        }
    }

    List query(String sql, Object ... params) throws Exception
    {
        Connection con = getConnection()
        try {
            PreparedStatement stm = con.prepareStatement(sql)
            params.eachWithIndex { p, i ->
                stm.setObject(i+1, p)
            }

            ResultSet rs = stm.executeQuery()

            def resultList = []
            if (rs.next()) {
                def meta = rs.metaData
                def colNames = []
                for (int i = 1; i <= meta.getColumnCount(); ++i) {
                    colNames << meta.getColumnLabel(i)
                }

                // used empty for-loop since
                // Groovy doesn't have do-while-loop
                for (;;) {
                    def data = [:]
                    colNames.each { colName ->
                        data[colName] = rs.getObject(colName)
                    }
                    resultList << data

                    if (!rs.next()) break;
                }
            }

            return resultList
        }
        finally {
            try { con.close() } catch(Exception e){}
        }
    }


    // helper class
    private static class DBServiceHolder
    {

        private static final DBService INSTANCE = new DBService()
        
    }
}
