package com.dunyamin.shemsidictionary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dunyamin.shemsidictionary.databinding.ActivityWordsListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class WordsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordsListBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var recyclerViewAdapter : RecyclerAdapter
    private lateinit var database : FirebaseFirestore

    private var translationList = ArrayList<WordsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        database = FirebaseFirestore.getInstance()

        getDatas()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = RecyclerAdapter(translationList)
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun getDatas() {

        database.collection("myWordList").addSnapshotListener {snapshot, exception ->
            if (exception != null) {
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        val documents = snapshot.documents

                        translationList.clear()

                        for (document in documents) {
                            val myEnglishWord = document.get("myEnglishWord") as String
                            val myTurkishWord = document.get("myTurkishWord") as String

                            val downloadedList = WordsData(myEnglishWord, myTurkishWord)
                            translationList.add(downloadedList)
                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_word) {
            //it will go to AddWordActivity
            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.exit) {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun addUnit(view: View) {
        val intent = Intent(applicationContext, AddUnitActivity::class.java)
        startActivity(intent)
        finish()
    }
}