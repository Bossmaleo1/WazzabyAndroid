package com.android.wazzabysama.data.model.dataRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "token_data_table"
)
data class TokenRoom(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "token_id")
    var id: Int?,
    @ColumnInfo(name = "token_token")
    var token: String
)
