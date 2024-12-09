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
    //111

    fun chkSales(UName: String, UPassword: String): MutableMap<String,String> {
        //var chk = false
        var Uname =""
        var BPCode=""
        val Dc:MutableMap<String,String> = mutableMapOf()
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
                    Dc["uName"]=Uname
                    Dc["SLCode"]=BPCode
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

    fun GetSalesKey(SalesCode:String ):Int
    {
        var SalesKey = 0
        try {
            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQ()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query = "select slmn_key from salesman where slmn_code = '$SalesCode' "
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()){
                    SalesKey = rs.getInt("slmn_key")
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }

        return SalesKey
    }

    fun GetCustomer(SlCode:String ,CusttomerSearch:String ): ArrayList<CustomerModel> {
        val customerList = ArrayList<CustomerModel>()
        val Cust : CustomerModel
        val SlKey =GetSalesKey(SlCode)
        try {
            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQ()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query = "select a2.ADDB_COMPANY ,a3.AR_CODE ,a3.AR_NAME ,a2.ADDB_PROVINCE  ,a2.ADDB_BRANCH " +
                        " from SLDETAIL s join TRANSTKH t on t.TRH_DI = s.SLD_DI join ARADDRESS a on a.ARA_ADDB = t.TRH_SHIP_ADDB  " +
                        " join ADDRBOOK a2 on a2.ADDB_KEY=  t.TRH_SHIP_ADDB join ARFILE a3 on a3.AR_KEY =a.ARA_AR " +
                        "where s.SLD_SLMN =$SlKey and (a3.AR_Name like '%$CusttomerSearch%' or a2.ADDB_COMPANY like '%$CusttomerSearch%' )" +
                        "group by a2.ADDB_COMPANY ,a3.AR_CODE ,a3.AR_NAME ,a2.ADDB_PROVINCE ,a2.ADDB_BRANCH  "
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()){
                    customerList.add(CustomerModel(rs.getString("AR_CODE"),rs.getString("AR_NAME"),rs.getString("ADDB_COMPANY"),rs.getString("ADDB_PROVINCE")))
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }

        return customerList
    }

}