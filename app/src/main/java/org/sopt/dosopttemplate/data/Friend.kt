package org.sopt.dosopttemplate.data

import androidx.annotation.DrawableRes

data class Friend(
    @DrawableRes val profileImage: Int,
    val name: String,
    val selfDescription: String,
)