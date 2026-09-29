package com.kvk.pclauncher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PcGameAdapter(private val onGameClick: (PcGame) -> Unit) : RecyclerView.Adapter<PcGameAdapter.ViewHolder>() {

    private var games: List<PcGame> = emptyList()

    fun submitList(newGames: List<PcGame>) {
        games = newGames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.appName.text = game.name
        val iconRes = when {
            game.name.contains("Roblox") -> android.R.drawable.ic_menu_compass
            game.name.contains("This PC") -> android.R.drawable.ic_menu_manage
            else -> android.R.drawable.ic_menu_gallery
        }
        holder.appIcon.setImageResource(iconRes)
        holder.itemView.setOnClickListener { onGameClick(game) }
    }

    override fun getItemCount(): Int = games.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.appName)
        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
    }
}
