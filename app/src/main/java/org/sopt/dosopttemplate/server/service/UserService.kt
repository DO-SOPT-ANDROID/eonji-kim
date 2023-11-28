package org.sopt.dosopttemplate.server.service

import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): Response<ResponseUserListDto>
}