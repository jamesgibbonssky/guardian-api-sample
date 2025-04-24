package com.gibconsulting.guardianapisample.domain.model

import java.util.Date

data class Article(
    val id: String,
    val thumbnail: String,
    val sectionId: String,
    val sectionName: String,
    val published: Date,
    val title: String,
    val url: String
)