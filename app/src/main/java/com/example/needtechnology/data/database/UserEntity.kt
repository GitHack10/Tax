package com.example.needtechnology.data.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users_list")
class UserEntity : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = null

    @ColumnInfo(name = "f_name")
    var firstName: String = ""

    @ColumnInfo(name = "l_name")
    var lastName: String = ""

    @ColumnInfo(name = "m_name")
    var middleName: String = ""

    @ColumnInfo(name = "phone")
    var phone: String = ""

    @ColumnInfo(name = "gender")
    var gender: String = ""

    @ColumnInfo(name = "birth")
    var birth: String = ""

    override fun equals(other: Any?): Boolean {
        return (other as UserEntity).id == id
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + middleName.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + birth.hashCode()
        return result
    }
}