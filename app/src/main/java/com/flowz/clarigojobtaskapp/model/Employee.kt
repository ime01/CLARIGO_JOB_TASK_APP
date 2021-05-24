package com.flowz.clarigojobtaskapp.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
//import kotlinx.parcelize

@Parcelize
@Entity(tableName = "clarigoemployee_table")
data class ClarigoEmployee (
    val name : String,
    val email: String,
    val dateOfBirth: String,
    val profilePicture: Uri? = null
    ): Parcelable{
        @PrimaryKey(autoGenerate = true)
        var personId: Int = 0
    }

class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }

}