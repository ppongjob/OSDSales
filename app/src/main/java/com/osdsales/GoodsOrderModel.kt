package com.osdsales

data class GoodsOrderModel (
    var GoodsCode:String="",
    var GoodsName:String="",
    var GoodsUnit:ArrayList<String> = arrayListOf(),
    var GoodsQTY:Double=0.0,
    var GoodsUnitFree:ArrayList<String> = arrayListOf(),
    var GoodsQTYFree:Double=0.0
)