package com.osdsales

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Connection
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Dictionary
import kotlin.collections.ArrayList

class GetData {
    var connect: Connection? = null
    var ConnectionResult = ""
    var isSuccess = false

    fun chkSales(UName: String, UPassword: String): Dictionary<String,String> {
        //var chk = false
        var Uname =""
        var BPCode=""
        lateinit var Dc:Dictionary<String,String>
        try {

            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQWH()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query =
                    "select * from UseApp where UserName = '$UName' and Password = '$UPassword' and (Department = 'SALES'  or Department = 'ADMIN')"
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()) {
                    Uname=rs.getString("Fname")
                    BPCode=rs.getString("BPlusCode")
                    Dc.put("uName",Uname)
                    Dc.put("BPCode",BPCode)
                    //chk = true
                }
                ConnectionResult = "Successful"
                isSuccess = true
                connect!!.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return Dc
    }


}