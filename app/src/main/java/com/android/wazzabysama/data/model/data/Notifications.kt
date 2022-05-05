package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*@Entity(
    tableName = "notifications_data_table"
)*/
data class Notifications (
    /*@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="notifications_id")*/
    @SerializedName("id")
    val id: Int,
    //@ColumnInfo(name="notifications_updated")
    @SerializedName("updated")
    val updated: String,
    //@ColumnInfo(name="notifications_content")
    @SerializedName("content")
    val content: String,
    //@ColumnInfo(name="notifications_state")
    @SerializedName("state")
    val state: Int,
    //@ColumnInfo(name="notifications_id_content")
    @SerializedName("id_content")
    val idContent: Int,
    //@ColumnInfo(name="notifications_id_receiver")
    @SerializedName("id_receiver")
    val idReceiver: Int,
    //@ColumnInfo(name="notifications_id_type")
    @SerializedName("id_type")
    val idType: Int,
    //@ColumnInfo(name="notifications_anonymous")
    @SerializedName("anonymous")
    val anonymous: Int)