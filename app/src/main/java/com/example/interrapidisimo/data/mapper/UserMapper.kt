package com.example.interrapidisimo.data.mapper

import com.example.interrapidisimo.data.database.entities.UserEntity
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.model.vo.UserVO

object UserMapper : Mapper<UserEntity, ResponseDataUserDTO, UserVO> {
    override fun toEntity(dto: ResponseDataUserDTO): UserEntity {
        return UserEntity(
            user = dto.user,
            identification = dto.identification,
            name = dto.name
        )
    }

    override fun fromEntity(entity: UserEntity): ResponseDataUserDTO {
        return ResponseDataUserDTO(
            user = entity.user,
            name = entity.name,
            identification = entity.identification
        )
    }

    override fun toVO(dto: ResponseDataUserDTO): UserVO {
        return UserVO(
            user = dto.user,
            identification = dto.identification,
            name = dto.name
        )
    }

    override fun fromVO(vo: UserVO): ResponseDataUserDTO {
        return ResponseDataUserDTO(
            user = vo.user,
            name = vo.name,
            identification = vo.identification
        )
    }
}


