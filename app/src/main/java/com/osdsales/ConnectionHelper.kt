package com.osdsales

import android.annotation.SuppressLint
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionHelper {
    var IP: String? =
        null
    var DB:kotlin.String? = null
    var DBUserName:kotlin.String? = null
    var DBPassword:kotlin.String? = null

    @SuppressLint("NewApi")
    fun connectionOSQWH(): Connection? {
        //From
        IP = "osq.ers.cloud"
        DB = "os-wh"
        DBUserName = "OSQ"
        DBPassword = "0125543004850"
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var connection: Connection? = null
        var ConnectionURL: String? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            ConnectionURL =
                "jdbc:jtds:sqlserver://$IP;databaseName=$DB;user=$DBUserName;password=$DBPassword;"
            connection = DriverManager.getConnection(ConnectionURL)
        } catch (se: SQLException) {
            Log.e("Error from SQL", se.message!!)
        } catch (e: ClassNotFoundException) {
            Log.e("Error from Class ", e.message!!)
        } catch (ex: Exception) {
            Log.e("Error from Exception", ex.message!!)
        }
        return connection
    }

    fun connectionOSQ(): Connection? {
        //From
        IP = "osq.ers.cloud"
        DB = "osqback2020"
        DBUserName = "OSQ"
        DBPassword = "0125543004850"
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var connection: Connection? = null
        var ConnectionURL: String? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            ConnectionURL =
                "jdbc:jtds:sqlserver://$IP;databaseName=$DB;user=$DBUserName;password=$DBPassword;"
            connection = DriverManager.getConnection(ConnectionURL)
        } catch (se: SQLException) {
            Log.e("Error from SQL", se.message!!)
        } catch (e: ClassNotFoundException) {
            Log.e("Error from Class ", e.message!!)
        } catch (ex: Exception) {
            Log.e("Error from Exception", ex.message!!)
        }
        return connection
    }

}