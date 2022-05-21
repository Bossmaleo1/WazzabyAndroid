package com.android.wazzabysama.data.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*@Entity(
    tableName = "private_message_data_table"
)*/
data class PrivateMessage (
    /*@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="private_message_id")*/
    @SerializedName("id")
    val id: Int,
    //@ColumnInfo(name="private_message__content")
    @SerializedName("content")
    val content: String,
    //@ColumnInfo(name="private_message__published")
    @SerializedName("published")
    val published: String,
    //@ColumnInfo(name="private_message__read_date")
    @SerializedName("readDate")
    val readDate: String,
    //@ColumnInfo(name="private_message__read_mention")
    @SerializedName("readMention")
    val readMention: Int,
    //@ColumnInfo(name="private_message__receiver_anonymous")
    @SerializedName("receiverAnonymous")
    val receiverAnonymous: Int,
    //@ColumnInfo(name="private_message__transmitter_anonymous")
    @SerializedName("transmitterAnonymous")
    val transmitterAnonymous: Int)