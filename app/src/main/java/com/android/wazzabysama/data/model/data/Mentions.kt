package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*@Entity(
    tableName = "mentions_data_table"
)*/
data class Mentions (
    /*@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="mentions_id")*/
    val id: Int,
    //@ColumnInfo(name="mentions_state")
    val state: Int,
    //@ColumnInfo(name="mentions_state_id")
    val stateId: Int,
    //@ColumnInfo(name="mentions_wording_id")
    val wordingId: Int,
    //@ColumnInfo(name="mentions_published")
    val published: String)