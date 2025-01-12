package com.jatinkabra.crudnotes.dataClasses

import android.os.Parcelable
import androidx.compose.foundation.MutatePriority

data class Note(
    val id : String = "",
    val title : String = "",
    val description : String = "",
    val priority: Int = 0,
    val randomInt: Int = kotlin.random.Random.nextInt(1,8) // generates random number between 1 and 7
)