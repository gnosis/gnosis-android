package io.gnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.gnosis.data.models.Safe.Companion.TABLE_NAME
import pm.gnosis.model.Solidity

@Entity(tableName = TABLE_NAME)
data class Safe(
    @PrimaryKey
    @ColumnInfo(name = COL_ADDRESS)
    val address: Solidity.Address,

    @ColumnInfo(name = COL_LOCAL_NAME)
    val localName: String
) {
    companion object {
        const val TABLE_NAME = "safes"

        const val COL_ADDRESS = "address"
        const val COL_LOCAL_NAME = "local_name"
    }
}
