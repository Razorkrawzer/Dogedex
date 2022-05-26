package com.george.dogedex.api.dto

import com.george.dogedex.Dog

class DogDTOMapper {

    private fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id, dogDTO.index, dogDTO.name, dogDTO.type, dogDTO.heightFemale,
            dogDTO.heightMale, dogDTO.imageUrl, dogDTO.lifeExpectancy, dogDTO.temperament,
            dogDTO.weightFemale, dogDTO.weightMale)
    }

    fun fromDogDTOListToDogDomainList(dogDtoList: List<DogDTO>): List<Dog>{
        return dogDtoList.map { fromDogDTOToDogDomain(it) }
    }
}