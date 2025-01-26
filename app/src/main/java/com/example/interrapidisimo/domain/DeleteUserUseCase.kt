package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.repository.RoomRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: RoomRepository
) {
    suspend fun delete() {
        repository.deleteUser()
    }
}