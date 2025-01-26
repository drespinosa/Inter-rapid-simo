package com.example.interrapidisimo.data.mapper

interface Mapper<Entity, DTO, VO> {
    fun toEntity(dto: DTO): Entity
    fun fromEntity(entity: Entity): DTO
    fun toVO(dto: DTO): VO
    fun fromVO(vo: VO): DTO
}
