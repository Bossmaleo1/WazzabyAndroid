package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*@Entity(
    tableName = "language_data_table"
)*/
data class Language (
    //@PrimaryKey(autoGenerate = false)
    //@ColumnInfo(name="language_id")
    @SerializedName("id")
    val id: String,
    //@ColumnInfo(name="language_locale")
    @SerializedName("locale")
    val locale: String
)