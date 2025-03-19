package com.drmiaji.prayertimes.repo.remote

import android.util.Log
import com.drmiaji.prayertimes.data.model.Ayah
import com.drmiaji.prayertimes.data.model.Schedule
import com.drmiaji.prayertimes.data.model.toSchedule
import com.drmiaji.prayertimes.repo.States
import com.drmiaji.prayertimes.repo.remote.network.PrayerService
import com.drmiaji.prayertimes.repo.remote.network.QuranService
import com.drmiaji.prayertimes.repo.remote.response.toAyahs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val prayerService: PrayerService,
    private val quranService: QuranService,
) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun getSchedule(lat: Double, long: Double, month: Int, year: Int) =
        flow<States<List<Schedule>>> {
            emit(States.loading())
            val response = prayerService.getSchedule(lat, long, month, year)
            response.data.let {
                if (it?.isNotEmpty() == true) emit(States.success(it.toSchedule()))
                else emit(States.success(listOf()))
            }
        }.catch {
            Log.d(TAG, "getSchedule: failed = ${it.message}")
            emit(States.failed(it.message ?: ""))
        }.flowOn(Dispatchers.IO)

    suspend fun getAyahQuran(noAyah: Int) = flow<States<List<Ayah>>> {
        emit(States.loading())
        val response = quranService.getSurah(noAyah)
        response.let {
            if (it.isNotEmpty()) emit(States.success(it.toAyahs()))
            else emit(States.success(listOf()))
        }
    }.catch {
        Log.d(TAG, "getSchedule: failed = ${it.message}")
        emit(States.failed(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

}
