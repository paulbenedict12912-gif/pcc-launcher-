package com.kvk.pclauncher

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var desktopAdapter: PcGameAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var clock: TextView
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            clock.text = sdf.format(Date())
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.desktopGrid)
        clock = findViewById(R.id.taskbarClock)
        
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        desktopAdapter = PcGameAdapter { game -> launchPCGame(game) }
        recyclerView.adapter = desktopAdapter

        val pcGames = listOf(
            PcGame("Roblox", "roblox.exe", "com.winlator"),
            PcGame("This PC", "explorer.exe", "com.winlator"),
            PcGame("GTA V", "gta5.exe", "com.winlator")
        )
        desktopAdapter.submitList(pcGames)

        findViewById<ImageButton>(R.id.btnStart).setOnClickListener { showStartMenu() }
        
        handler.post(runnable)
    }

    private fun launchPCGame(game: PcGame) {
        val intent = Intent().apply {
            setClassName(game.emulatorPackage, "com.winlator.XServerDisplayActivity")
            putExtra("container_path", "path/to/your/container")
            putExtra("exe_path", game.exePath)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Winlator not found.", Toast.LENGTH_LONG).show()
        }
    }

    private fun showStartMenu() {
        val options = arrayOf("Roblox", "This PC", "GTA V")
        AlertDialog.Builder(this)
            .setTitle("Start")
            .setItems(options) { _, which ->
                when(which) {
                    0 -> launchPCGame(PcGame("Roblox", "roblox.exe", "com.winlator"))
                    1 -> launchPCGame(PcGame("This PC", "explorer.exe", "com.winlator"))
                    2 -> launchPCGame(PcGame("GTA V", "gta5.exe", "com.winlator"))
                }
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}

data class PcGame(val name: String, val exePath: String, val emulatorPackage: String)
