package com.george.dogedex.doglist

import com.george.dogedex.Dog
import com.george.dogedex.R
import com.george.dogedex.api.ApiResponseStatus
import com.george.dogedex.api.DogsApi.retrofitService
import com.george.dogedex.api.dto.AddDogToUserDTO
import com.george.dogedex.api.dto.DogDTOMapper
import com.george.dogedex.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> {
                    allDogsListResponse
                }
                userDogsListResponse is ApiResponseStatus.Error -> {
                    userDogsListResponse
                }
                allDogsListResponse is ApiResponseStatus.Success && userDogsListResponse is ApiResponseStatus.Success -> {
                    ApiResponseStatus.Success(getCollectionList(allDogsListResponse.data,
                        userDogsListResponse.data))
                }
                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> =
        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(0, it.index, "", "", "", "", "", "", "", "", "",
                    inCollection = false)
            }
        }.sorted()


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

}