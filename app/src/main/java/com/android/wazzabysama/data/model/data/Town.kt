package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*@Entity(
    tableName = "town_data_table"
)*/
data class Town (
    /*@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="town_id")*/
    @SerializedName("id")
    val id: Int,
    //@ColumnInfo(name="town_name")
    @SerializedName("name")
    val name: String,
    //@ColumnInfo(name="town_iso_code")
    @SerializedName("isoCode")
    val isoCode: String)