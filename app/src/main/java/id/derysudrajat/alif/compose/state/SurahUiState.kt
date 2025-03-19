package com.drmiaji.prayertimes.compose.state

import com.drmiaji.prayertimes.data.model.Ayah
import com.drmiaji.prayertimes.data.model.Surah

data class SurahUiState(
    var surah: Surah = Surah.empty,
    var listAyah: List<Ayah> = emptyList(),
    var audioProgress: Float = 0f,
    var currentDurationPosition: Int = 0,
    var isFinish: Boolean? = null,
    var onBack: () -> Unit = {},
    var onStart: (progress: Float, position: Int) -> Unit = { _, _ -> },
    var onPause: () -> Unit = {}
)

data class SurahOnlyUiState(
    var surah: Surah = Surah.empty,
    var listAyah: List<Ayah> = emptyList(),
)
