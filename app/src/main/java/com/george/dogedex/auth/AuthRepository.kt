package com.george.dogedex.auth

import com.george.dogedex.User
import com.george.dogedex.api.ApiResponseStatus
import com.george.dogedex.api.DogsApi
import com.george.dogedex.api.dto.LoginDTO
import com.george.dogedex.api.dto.SignUpDTO
import com.george.dogedex.api.dto.UserDTOMapper
import com.george.dogedex.api.makeNetworkCall
import java.lang.Exception

class AuthRepository {

    suspend fun login(
        email: String,
        password: String,
    ): ApiResponseStatus<User>  = makeNetworkCall {

        val loginDTO = LoginDTO(email, password)
        val loginResponse = DogsApi.retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess){
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToDomain(userDTO)
    }

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResponseStatus<User>  = makeNetworkCall {

        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess){
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToDomain(userDTO)
    }


}