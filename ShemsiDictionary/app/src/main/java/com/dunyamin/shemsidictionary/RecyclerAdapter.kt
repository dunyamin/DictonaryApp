package com.dunyamin.shemsidictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dunyamin.shemsidictionary.databinding.RecyclerRowBinding

class RecyclerAdapter(private val wordsList: ArrayList<WordsData>) : RecyclerView.Adapter<RecyclerAdapter.WordsListVH>() {
    class WordsListVH(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListVH {
        return WordsListVH(RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: WordsListVH, position: Int) {
        holder.binding.englishWord.text = wordsList[position].myEnglishWord
        holder.binding.turkishWord.text = wordsList[position].myTurkishWord
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }
}