package org.sopt.dosopttemplate.server.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserListSupportDto(
    @SerialName("url")
    val url: String,
    @SerialName("text")
    val text: String
)
