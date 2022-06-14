package com.android.wazzabysama.data.model.dataRoom

import androidx.room.*

@Entity(
    tableName = "public_message_data_table",
    indices = [
        Index(value = ["public_message_id"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserRoom::class,
            parentColumns = ["user_id"],
            childColumns = ["public_message_user_id"],
        ),
    ]
)
data class PublicMessageRoom (
    @ColumnInfo(name = "public_message_id")
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "anonymous")
    var anonymous: Int,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?,
    @ColumnInfo(name = "published")
    var published: String,
    @ColumnInfo(name = "state")
    var state: Int,
    @ColumnInfo(name = "wording")
    var wording: String,
    @ColumnInfo(name = "problematic")
    var problematicId: String,
    @ColumnInfo(name = "public_message_user_id")
    var userId: Int,
    )