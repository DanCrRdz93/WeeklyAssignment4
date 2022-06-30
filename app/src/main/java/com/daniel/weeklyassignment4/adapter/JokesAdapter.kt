package com.daniel.weeklyassignment4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.weeklyassignment4.R
import com.daniel.weeklyassignment4.databinding.FragmentJokesBinding
import com.daniel.weeklyassignment4.databinding.JokeItemBinding
import com.daniel.weeklyassignment4.domain.DomainJoke
import com.daniel.weeklyassignment4.model.Joke
import com.daniel.weeklyassignment4.model.RandomJoke
import com.daniel.weeklyassignment4.model.RandomJokes

class JokesAdapter (
    private val jokes: MutableList<DomainJoke> = mutableListOf(),
    private val onJokeClicked: (String?) -> Unit
        ) : RecyclerView.Adapter<JokesViewHolder>(){

    fun uploadList(newJokes: List<DomainJoke>) {
        jokes.addAll(newJokes)
        notifyDataSetChanged()

    }

    fun upload(newJoke: DomainJoke) {
        jokes.removeAll(jokes)
        jokes.add(newJoke)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder =
        JokesViewHolder(
            JokeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) =
        holder.bind(jokes[position], onJokeClicked)

    override fun getItemCount(): Int = jokes.size
}

class JokesViewHolder(
    private val binding: JokeItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: DomainJoke, onJokeClicked: (String?) -> Unit) {
        binding.jokeId.text = joke.id.toString()
        binding.jokeTxt.text = joke.joke

        itemView.setOnClickListener{
            onJokeClicked(joke.id.toString())
        }
    }

}