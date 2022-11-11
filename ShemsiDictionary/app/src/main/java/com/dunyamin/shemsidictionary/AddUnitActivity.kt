package com.dunyamin.shemsidictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddUnitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unit)
    }

    fun addNewUnit(view: View) {

        val intent = Intent(applicationContext, WordsListActivity::class.java)
        startActivity(intent)
        finish()

    }
}