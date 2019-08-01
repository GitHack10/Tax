package ru.dagdelo.business05.domain

interface ResourceManager {
    fun getString(idRes: Int): String
    fun getArrayOfStrings(resourceId: Int): Array<String>
}