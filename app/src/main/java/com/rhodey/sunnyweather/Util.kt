package com.rhodey.sunnyweather

import java.math.RoundingMode
import java.text.DecimalFormat

fun String.formatLocation(): String {
    val value = this.toDouble()
    val format = DecimalFormat("0.##")
    //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return format.format(value)
}