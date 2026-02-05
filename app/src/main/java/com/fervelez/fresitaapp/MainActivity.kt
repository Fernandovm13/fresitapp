package com.fervelez.fresitaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.ui.main.adapter.FruitAdapter
import com.fervelez.fresitaapp.util.PreferenceHelper
import com.fervelez.fresitaapp.viewmodel.FruitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val viewModel: FruitViewModel by viewModels()
    private lateinit var prefs: PreferenceHelper
    private lateinit var adapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceHelper(this)

        val rv = findViewById<RecyclerView>(R.id.rvFruits)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)
        val progress = findViewById<ProgressBar>(R.id.progress)

        adapter = FruitAdapter(this, mutableListOf())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        viewModel.loading.observe(this) { progress.visibility = if (it) View.VISIBLE else View.GONE }

        viewModel.fruits.observe(this) { list ->
            adapter.update(list)
            tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { if (it != null) { tvEmpty.text = it; tvEmpty.visibility = View.VISIBLE } }

        viewModel.loadFruits()

        fab.setOnClickListener {
            startActivity(Intent(this, AddFruitActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFruits()
    }
}
