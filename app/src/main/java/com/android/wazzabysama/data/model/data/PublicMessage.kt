package com.android.wazzabysama.data.model.data

import androidx.room.*
import com.android.wazzabysama.data.model.data.Image
import com.android.wazzabysama.data.model.data.User
import com.google.gson.annotations.SerializedName
import java.util.*

/*@Entity(
    tableName = "public_message_data_table"
)*/
data class PublicMessage(
    //@PrimaryKey(autoGenerate = false)
    //@ColumnInfo(name="public_message_id")
    @SerializedName("id")
    var id: Int,
    //@ColumnInfo(name="public_message_anonymous")
    @SerializedName("anonymous")
    var anonymous: Int,
    //@ColumnInfo(name="public_message_content")
    @SerializedName("content")
    var content: String,
    //@Embedded
    @SerializedName("images")
    var images: List<Image>,
    //@Embedded
    @SerializedName("published")
    var published: Date,
    //@ColumnInfo(name="public_message_state")
    @SerializedName("state")
    var state: Int,
    //@Ignore
    @SerializedName("user")
    val user: User /*= User(0,1,"","",0, listOf(),"", listOf(), listOf(),"",Problematic(0,"","","")),*/,
    //@ColumnInfo(name="public_message_wording")
    @SerializedName("wording")
    var wording: String
)