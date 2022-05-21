package com.android.wazzabysama.data.model.dataRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "problematic_data_table"
)
data class ProblematicRoom(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="problematic_id")
    var  id: Int,
    var wording: String,
    @ColumnInfo(name="problematic_language")
    var language: String,
    @ColumnInfo(name="problematic_icon")
    var icon: String
)
