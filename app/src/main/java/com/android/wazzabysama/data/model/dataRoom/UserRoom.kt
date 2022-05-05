package com.android.wazzabysama.data.model.dataRoom

import androidx.room.*

@Entity(
    tableName = "user_data_table",
    indices = [
        Index(value = ["user_email", "user_user_name"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProblematicRoom::class,
            parentColumns = ["problematic_id"],
            childColumns = ["user_problematic_id"],
        ),
    ]
)
data class UserRoom(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @ColumnInfo(name = "user_online")
    var online: Int?,
    @ColumnInfo(name = "user_anonymous")
    var anonymous: Int?,
    @ColumnInfo(name = "user_problematic_id")
    var problematic_id: Int,
    @ColumnInfo(name = "user_email")
    var email: String?,
    @ColumnInfo(name = "user_first_name")
    var firstName: String?,
    @ColumnInfo(name = "user_last_name")
    var lastName: String?,
    @ColumnInfo(name = "user_image_url")
    var imageUrl: String?,
    @ColumnInfo(name = "user_key_push")
    var keyPush: String?,
    @ColumnInfo(name = "user_role")
    var role: String?,
    @ColumnInfo(name = "user_user_name")
    var userName: String?,
    @ColumnInfo(name = "user_token")
    var userToken: String?
)