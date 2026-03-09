package com.cipecma.trainup.ui.exercice

import org.w3c.dom.Text
import java.sql.Time

data class ExerciceItem(
    val id: Int,
    val name: String,
    val description: Text,
    val rest_time: Int,
    val reps: Int,
    val nber_series: Int,
    val time_series: Time,
    val id_cat: Int,
    val id_muscle: Int,
)