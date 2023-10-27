package org.sopt.dosopttemplate

import androidx.annotation.DrawableRes

data class My(
    @DrawableRes
    val profileImage: Int,
    val name: String,
    val self_description: String,
)