package com.example.myfirstkmmapp.domain.note

import com.example.myfirstkmmapp.presentation.BabyBlueHex
import com.example.myfirstkmmapp.presentation.LightGreenHex
import com.example.myfirstkmmapp.presentation.RedOrangeHex
import com.example.myfirstkmmapp.presentation.RedPinkHex
import com.example.myfirstkmmapp.presentation.VioletHex
import kotlinx.datetime.LocalDateTime
import kotlin.jvm.Volatile
import kotlin.random.Random

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(
            RedOrangeHex, RedPinkHex, BabyBlueHex, VioletHex,
            LightGreenHex,
        )

        fun generateRandomColor() = colors.random();
    }
}
