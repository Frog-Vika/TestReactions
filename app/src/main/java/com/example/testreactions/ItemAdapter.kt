package com.example.testreactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testreactions.data.PlayerEntity

class RecordAdapter(private val records: List<PlayerEntity>) :
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val ivIcon: ImageView = itemView.findViewById(R.id.Icon)
        val tvPerson: TextView = itemView.findViewById(R.id.tvPerson)
        val tvInfo: TextView = itemView.findViewById(R.id.tvInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = records[position]
//        holder.ivIcon.setImageResource(item.imageResId)
        holder.tvPerson.text = record.playerName
        //holder.tvInfo.text = "${record.timeMs} мс"
        if (record.timeMs != 0L) {
            //holder.tvPerson.text = record.playerName
            holder.tvInfo.text = "${record.timeMs} мс"
        }
        //else { holder.tvInfo.text = "${record.timeMs} мс"}
    }

    override fun getItemCount(): Int = records.size
}