package com.dunyamin.shemsidictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dunyamin.shemsidictionary.databinding.ActivityAddWordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    fun addWord(view: View) {

        val wordInEnglish = binding.englishWord.text.toString()
        val wordInTurkish = binding.turkishWord.text.toString()

        val wordHashMap = hashMapOf<String, String>()
        wordHashMap["myEnglishWord"] = wordInEnglish
        wordHashMap["myTurkishWord"] = wordInTurkish

        database.collection("myWordList").add(wordHashMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

        val intent = Intent(applicationContext, WordsListActivity::class.java)
        intent.putExtra("sendDataEng", wordInEnglish)
        intent.putExtra("sendDataTur", wordInTurkish)
        startActivity(intent)
        finish()
    }
}