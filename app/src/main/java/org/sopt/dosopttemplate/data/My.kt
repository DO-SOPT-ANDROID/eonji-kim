package org.sopt.dosopttemplate.data

import androidx.annotation.DrawableRes

data class My(
    @DrawableRes val profileImage: Int,
    val name: String,
    val selfDescription: String,
)