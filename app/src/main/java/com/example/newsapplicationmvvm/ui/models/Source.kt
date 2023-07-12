package com.example.newsapplicationmvvm.ui.models

data class Source(
    val id: String,
    val name: String
) {
    override fun hashCode(): Int {
        return name?.hashCode() ?: super.hashCode()
    }
}