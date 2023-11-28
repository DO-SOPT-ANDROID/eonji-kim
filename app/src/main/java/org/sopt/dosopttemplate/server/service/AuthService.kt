package org.sopt.dosopttemplate.server.service

import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members")
    suspend fun signUp(
        @Body requestSignUp: RequestSignUpDto,
    ): Response<Unit>

    @POST("api/v1/members/sign-in")
    suspend fun login(
        @Body requestLogin: RequestLoginDto,
    ): Response<ResponseLoginDto>
}