package com.osdsales

import java.sql.Connection
import java.sql.SQLException
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
        var SalesKey = -1
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
                    var Branch = ""
                    if (!rs.getString("ADDB_BRANCH").isNullOrBlank())
                    {
                        Branch = "สาขา "+rs.getString("ADDB_BRANCH") +" "
                    }

                    customerList.add(CustomerModel(rs.getString("AR_CODE"),rs.getString("AR_NAME"),rs.getString("ADDB_COMPANY"),Branch+rs.getString("ADDB_PROVINCE")))
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }

        return customerList
    }

    fun GetGoodsGroup():ArrayList<GoodsDept>{
        val GoodsGroupList = ArrayList<GoodsDept>()

        try {
            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQ()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query = "select icdept_key, icdept_code, icdept_thaidesc from icdept order by icdept_code"
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()){
                    var goodsDept = GoodsDept()
                    goodsDept.key = rs.getInt("icdept_key")
                    goodsDept.Code = rs.getString("icdept_code")
                    goodsDept.Name = rs.getString("icdept_thaidesc")
                    GoodsGroupList.add(goodsDept)
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }
        return GoodsGroupList
    }

    fun GetUTQ(SKUKey:Int):ArrayList<String>{
        val UTQList = ArrayList<String>()
        try {
            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQ()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query = "select utq_name from goodsmaster g join uofqty u on  g.GOODS_UTQ =u.UTQ_KEY where g.goods_sku = $SKUKey  group by utq_name "
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()){
                    UTQList.add(rs.getString("utq_name"))
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }


        return UTQList
    }

    fun GetGoodsByDept(DeptKey :Int):ArrayList<GoodsOrderModel>{
        val GoodsOrderList = ArrayList<GoodsOrderModel>()

        try {
            val connectionHelper = ConnectionHelper()
            connect = connectionHelper.connectionOSQ()
            if (connect == null) {
                ConnectionResult = "Check your internet Access!"
            } else {
                val query = "select s.sku_key,s.SKU_CODE ,s.SKU_NAME " +
                        " from SKUMASTER s  " +
                        " where s.SKU_ICDEPT = $DeptKey  and not(s.sku_code  like '%*%')" +
                        " order by sku_code"
                val stmt = connect!!.createStatement()
                val rs = stmt.executeQuery(query)
                while (rs.next()){
                    var goodsOrder= GoodsOrderModel()
                    goodsOrder.GoodsCode= rs.getString("SKU_CODE")
                    goodsOrder.GoodsName= rs.getString("SKU_NAME")
                    goodsOrder.GoodsUnit=GetUTQ(rs.getInt("sku_key"))
                    goodsOrder.GoodsUnitFree=goodsOrder.GoodsUnit
                    goodsOrder.GoodsQTY=0.0
                    goodsOrder.GoodsQTYFree=0.0


                    GoodsOrderList.add(goodsOrder)
                }
            }
        } catch (e :SQLException){
            e.printStackTrace()
        }


        return  GoodsOrderList
    }

}

