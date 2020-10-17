package vn.com.libertime.util

import java.util.*

object Number {
    fun generateUniqueNumber(): Long {
        val calendar: Calendar = Calendar.getInstance()
        val randomNum = (0..1000).random()
        return String.format(
            "%d%d%d%d%d%d%d",
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND),
            randomNum
        ).toLong()
    }
}