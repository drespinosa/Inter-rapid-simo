package com.example.interrapidisimo.data.mapper

import com.example.interrapidisimo.data.database.entities.SchemaEntity
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.vo.SchemeVO

object SchemaMapper : Mapper<SchemaEntity, ResponseDataSchemeDTO, SchemeVO> {
    override fun toEntity(dto: ResponseDataSchemeDTO): SchemaEntity {
        return SchemaEntity(
            table = dto.name,
            content = dto.content,
        )
    }

    override fun fromEntity(entity: SchemaEntity): ResponseDataSchemeDTO {
        return ResponseDataSchemeDTO(
            name = entity.table ?: "",
            content = entity.content ?: ""
        )
    }

    override fun toVO(dto: ResponseDataSchemeDTO): SchemeVO {
        return SchemeVO(
            name = dto.name,
            description = dto.content
        )
    }

    override fun fromVO(vo: SchemeVO): ResponseDataSchemeDTO {
        return ResponseDataSchemeDTO(
            name = vo.name,
            content = vo.description
        )
    }

    fun toEntityList(dtoList: List<ResponseDataSchemeDTO>): List<SchemaEntity> {
        return dtoList.map { dto ->
            SchemaEntity(
                table = dto.name,
                content = dto.content
            )
        }
    }

}


