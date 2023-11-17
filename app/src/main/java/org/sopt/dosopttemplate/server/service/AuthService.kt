package org.sopt.dosopttemplate.server.service

import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members")
    fun signUp(
        @Body requestSignUp: RequestSignUpDto,
    ): Call<Unit>

    @POST("api/v1/members/sign-in")
    fun login(
        @Body requestLogin: RequestLoginDto,
    ): Call<ResponseLoginDto>
}