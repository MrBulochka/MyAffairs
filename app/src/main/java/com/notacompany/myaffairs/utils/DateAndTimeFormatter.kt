package com.notacompany.myaffairs.utils

import java.text.SimpleDateFormat

object DateAndTimeFormatter {

    fun getDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yy")

        return dateFormat.format(date)
    }
}