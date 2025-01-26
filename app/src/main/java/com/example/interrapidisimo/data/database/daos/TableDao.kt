package com.example.interrapidisimo.data.database.daos

/*@Dao
interface TableDao : GenericDao<TableEntity> {

    @RawQuery(observedEntities = [TableEntity::class])
    suspend fun consultaRaw(query: SupportSQLiteQuery): List<TableEntity>
    suspend fun createTableQuery(name: String, text: String): SupportSQLiteQuery {
        val query = SimpleSQLiteQuery("CREATE TABLE IF NOT EXISTS $name$text")
        consultaRaw(query)
        return query
    }

    @Query("SELECT * FROM " + TableEntity.TABLE_NAME)
    suspend fun getDataTables(): List<TableEntity>

}

 */