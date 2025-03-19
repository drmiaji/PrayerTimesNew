package com.drmiaji.prayertimes.compose.state

import com.drmiaji.prayertimes.data.model.Juz
import com.drmiaji.prayertimes.data.model.Surah

data class QuranUiState(
    var listSurah: List<Surah> = emptyList(),
    var listJuz: List<Juz> = emptyList(),
    var onBack: () -> Unit = {}
)