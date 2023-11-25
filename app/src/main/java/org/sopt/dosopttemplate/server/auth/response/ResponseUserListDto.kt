package org.sopt.dosopttemplate.server.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserListDto(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<ResponseUserListDataDto>,
    @SerialName("support")
    val support: ResponseUserListSupportDto
)
