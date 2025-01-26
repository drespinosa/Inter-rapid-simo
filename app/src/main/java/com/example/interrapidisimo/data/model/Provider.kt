package com.example.interrapidisimo.data.model

object Provider {
    fun saveUser(userId: String?, userName: String?, userIdentification: String?) {
        userSave = listOf(
            User(
                id = userId,
                name = userName,
                identification = userIdentification
            )
        )
    }

    var quotes2: List<Model> = emptyList()
    var userSave: List<User> = emptyList()

}