package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*@Entity(
    tableName = "token_data_table"
)*/
data class Token (
    //@PrimaryKey(autoGenerate = false)
    //@ColumnInfo(name="token_id")
    val id: Int,
    //@ColumnInfo(name="token_token")
    val token: String
    )