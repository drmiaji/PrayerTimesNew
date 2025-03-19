package com.drmiaji.prayertimes.ui.quran

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import com.drmiaji.prayertimes.compose.page.QuranAndSurah
import com.drmiaji.prayertimes.compose.page.QuranOnly
import com.drmiaji.prayertimes.compose.ui.navigation.QuranNavType
import com.drmiaji.prayertimes.compose.ui.theme.AlifTheme
import com.drmiaji.prayertimes.data.model.Surah

@AndroidEntryPoint
class QuranActivity : BaseAudioSurahActivity() {

    private val model: QuranViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.setBack { finish() }
        model.getListSurah(this)
        model.getListJuz(this)
        surahViewModel.setCallBack(this::onBack, this::onMediaStart, this::onMediaPause)
        initMediaPlayer(model.uiState.value.listSurah[0])

        setContent {
            AlifTheme {
                val windowSize = calculateWindowSizeClass(this@QuranActivity)
                val uiState = model.uiState.collectAsState().value
                val surahUiState = surahViewModel.uiState.collectAsState().value

                if (surahUiState.surah == Surah.empty) {
                    surahViewModel.getAyahFromSurah(uiState.listSurah[0].index)
                    surahViewModel.setSurah(uiState.listSurah[0])
                }

                when (windowSize.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> QuranOnly(uiState)
                    WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> QuranAndSurah(
                        uiState,
                        surahUiState,
                        if (windowSize.widthSizeClass == WindowWidthSizeClass.Medium) QuranNavType.NavRail
                        else QuranNavType.NavDrawer,
                        surahViewModel::getAyahFromSurah,
                        surahViewModel::setSurah,
                        this::initMediaPlayer
                    )
                }
            }
        }
    }

    private fun onBack() = finish()
}
