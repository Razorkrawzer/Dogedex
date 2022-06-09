package com.george.dogedex.api.dto

import com.george.dogedex.User

class UserDTOMapper {

    fun fromUserDTOToDomain(userDTO: UserDTO) = User(
        userDTO.id, userDTO.email, userDTO.authenticationToken
    )

}