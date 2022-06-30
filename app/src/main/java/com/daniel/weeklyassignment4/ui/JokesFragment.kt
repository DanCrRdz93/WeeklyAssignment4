package com.daniel.weeklyassignment4.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.weeklyassignment4.R
import com.daniel.weeklyassignment4.adapter.JokesAdapter
import com.daniel.weeklyassignment4.databinding.FragmentJokesBinding
import com.daniel.weeklyassignment4.domain.DomainJoke
import com.daniel.weeklyassignment4.model.RandomJoke
import com.daniel.weeklyassignment4.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesFragment : BaseFragment() {

    private val binding by lazy {
        FragmentJokesBinding.inflate(layoutInflater)
    }

    private val jokesAdapter by lazy {
        JokesAdapter{
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.jokesRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = jokesAdapter
        }

        binding.randomBtn.setOnClickListener {
            randomJokeViewModel.getRandomJoke()
            randomJokeViewModel.joke.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ResponseState.LOADING -> {

                    }
                    is ResponseState.SUCCESS<*> -> {
                        (state as ResponseState.SUCCESS<DomainJoke>).response
                        binding.jokesRecycler.bringToFront()
                        jokesAdapter.upload(state.response)

                    }
                    is ResponseState.ERROR -> {
                        showError(state.error) {
                            randomJokeViewModel.getRandomJoke()
                        }
                    }
                }
            }
        }

        binding.randomListBtn.setOnClickListener {
            randomJokeViewModel.getRandomJokes()
            randomJokeViewModel.jokes.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ResponseState.LOADING -> {

                    }
                    is ResponseState.SUCCESS<*> -> {
                        (state as ResponseState.SUCCESS<List<DomainJoke>>).response
                        binding.jokesRecycler.bringToFront()
//                        jokesAdapter.restore(false)
                        jokesAdapter.uploadList(state.response)
                    }
                    is ResponseState.ERROR -> {
                        showError(state.error) {
                            randomJokeViewModel.getRandomJoke()
                        }
                    }
                }
            }
        }

        val linearLayout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var isLoading = false
        binding.jokesRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = binding.jokesRecycler.layoutManager!!.childCount
                val lastVisibleItem: Int = linearLayout.findLastVisibleItemPosition()
                val total = linearLayout.itemCount

                if(!isLoading){
                    if(visibleItemCount + lastVisibleItem >= total){
                        randomJokeViewModel.getRandomJokes()
                    }
                }
            }
        })

        binding.inputNameBtn.setOnClickListener {
            randomJokeViewModel.setName(binding.editTextFirstName.text.toString(),binding.editTextLastName.text.toString())
            randomJokeViewModel.getCustomJoke()
            randomJokeViewModel.customJoke.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ResponseState.LOADING -> {

                    }
                    is ResponseState.SUCCESS<*> -> {
                        (state as ResponseState.SUCCESS<DomainJoke>).response
                        binding.jokesRecycler.bringToFront()
                        jokesAdapter.upload(state.response)

                    }
                    is ResponseState.ERROR -> {
                        showError(state.error) {
                            randomJokeViewModel.getRandomJoke()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        randomJokeViewModel.joke.removeObservers(viewLifecycleOwner)
        randomJokeViewModel.resetState()
    }
}