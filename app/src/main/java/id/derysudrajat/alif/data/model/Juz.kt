package com.drmiaji.prayertimes.data.model

data class JuzContainer(
    val name: String,
    val index: Int,
    val verse: Int
)

data class Juz(
    val index: Int,
    val start: JuzContainer,
    val end: JuzContainer
)