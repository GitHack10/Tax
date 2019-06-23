package com.example.needtechnology.domain

interface ResourceManager {
    fun getString(idRes: Int): String
    fun getArrayOfStrings(resourceId: Int): Array<String>
}