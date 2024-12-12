package com.osdsales

data class GoodsDept(
    var key:Int = 0,
    var Code:String ="",
    var Name:String=""

)

{    override fun toString(): String {
    return Name
}
}