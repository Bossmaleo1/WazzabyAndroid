package com.android.wazzabysama.data.model.data

import androidx.room.*
import com.android.wazzabysama.data.model.data.User
import com.google.gson.annotations.SerializedName
import java.util.*

/*@Entity(
    tableName = "comment_data_table"
)*/
data class Comment(
    //@ColumnInfo(name = "comment_anonymous")
    @SerializedName("anonymous")
    var anonymous: Int,
    //@ColumnInfo(name = "comment_content")
    @SerializedName("content")
    var content: String,
    //@PrimaryKey(autoGenerate = false)
    //@ColumnInfo(name = "comment_id")
    @SerializedName("id")
    var id: Int,
    //@Embedded
    @SerializedName("published")
    var published: Date,
    //@Ignore
    @SerializedName("user")
    val user: User /*= User(
        0,
        1,
        "",
        "",
        0,
        listOf(),
        "",
        listOf(),
        listOf(),
        "",
        Problematic(0, "", "", "")
    )*/
) /*{
    constructor() : this(
        0,
        "",
        0,
        Date(),
        User(0, 1, "", "", 0, listOf(), "", listOf(), listOf(), "", Problematic(0, "", "", ""))
    )
}*/