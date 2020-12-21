package com.claudiuluca.ingtest.util

import android.content.Context
import android.widget.Toast
import com.claudiuluca.ingtest.data.api.Campaign

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

val campaignComparator = Comparator { campaign1: Campaign, campaign2: Campaign ->
    val split1 = campaign1.price.split(" ")
    val split2 = campaign2.price.split(" ")
    val number1 = split1[0].toIntOrNull() ?: 0
    val number2 = split2[0].toIntOrNull() ?: 0

    number1 - number2
}