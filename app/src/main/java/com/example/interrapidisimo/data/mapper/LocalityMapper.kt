package com.example.interrapidisimo.data.mapper

import com.example.interrapidisimo.data.database.entities.LocalityEntity
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.vo.LocalityVO

object LocalityMapper : Mapper<LocalityEntity, ResponseDataLocalityDTO, LocalityVO> {
    override fun toEntity(dto: ResponseDataLocalityDTO): LocalityEntity {
        return LocalityEntity(
            abbreviation = dto.abbreviation,
            fullName = dto.fullName,
        )
    }

    override fun fromEntity(entity: LocalityEntity): ResponseDataLocalityDTO {
        return ResponseDataLocalityDTO(
            abbreviation = entity.abbreviation ?: "",
            fullName = entity.fullName ?: ""
        )
    }

    override fun toVO(dto: ResponseDataLocalityDTO): LocalityVO {
        return LocalityVO(
            abbreviation = dto.abbreviation,
            fullName = dto.fullName
        )
    }

    override fun fromVO(vo: LocalityVO): ResponseDataLocalityDTO {
        return ResponseDataLocalityDTO(
            abbreviation = vo.abbreviation,
            fullName = vo.fullName
        )
    }

    fun toEntityList(dtoList: List<ResponseDataLocalityDTO>): List<LocalityEntity> {
        return dtoList.map { dto ->
            LocalityEntity(
                abbreviation = dto.abbreviation,
                fullName = dto.fullName
            )
        }
    }

}


