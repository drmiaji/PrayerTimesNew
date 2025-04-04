package com.drmiaji.prayertimes2.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.drmiaji.prayertimes2.R
import com.drmiaji.prayertimes2.data.model.Prayer
import com.drmiaji.prayertimes2.data.model.TimingSchedule
import com.drmiaji.prayertimes2.data.model.getNearestSchedule
import com.drmiaji.prayertimes2.data.model.getScheduleName
import com.drmiaji.prayertimes2.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val listPrayer: List<Prayer>,
    private val timingSchedule: TimingSchedule,
    private val onSetReminder: (timingSchedule: TimingSchedule, prayerTime: String, isReminded: Boolean, position: Int) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemScheduleBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemScheduleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prayer = listPrayer[position]
        with(binding) {
            tvTime.text = prayer.time
            ivSound.setImageResource(
                if (prayer.isReminded) R.drawable.ic_sound_on else R.drawable.ic_sound_off
            )
            btnSound.setOnClickListener {
                onSetReminder(timingSchedule, prayer.time, !prayer.isReminded, position)
            }
            tvTimeName.text = timingSchedule.getScheduleName(prayer)
            val nearestSchedule = timingSchedule.getNearestSchedule(Timestamp.now())
            root.apply {
                if (nearestSchedule.time == prayer.time) {
                    strokeWidth = 4
                    setStrokeColor(ContextCompat.getColorStateList(this.context, R.color.primary))
                    setCardBackgroundColor(ContextCompat.getColor(this.context, R.color.primary_10))
                    cardElevation = 0f
                    elevation = 0f
                }
            }
        }
    }

    override fun getItemCount(): Int = listPrayer.size
}