package com.chnoou.movieapp.ui.home_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chnoou.movieapp.R
import com.chnoou.movieapp.backend.data.Movie
import com.chnoou.movieapp.databinding.HomeFragmentBinding
import com.chnoou.movieapp.ui.home_fragment.movie_recycler.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchMovies()

        initRecycler()
        initSwipeRefresh()

    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchMovies()
        }
        viewModel.fetchingMovies.observe(viewLifecycleOwner) {
            if (binding.swipeRefresh.isRefreshing && !it) {
                binding.swipeRefresh.isRefreshing = false
            } else if (!binding.swipeRefresh.isRefreshing && it) {
                binding.swipeRefresh.isRefreshing = true
            }
        }
    }

    private fun initRecycler() {
        binding.moviesRecycler.apply {
            adapter = MovieAdapter(arrayListOf()).apply {
                onClick = {
                    clickedMovie(it)
                }
            }
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            Log.d(this@HomeFragment::class.java.simpleName, "Updated movies to view")
            (binding.moviesRecycler.adapter as MovieAdapter).submitList(it.toList())
        }
    }

    private fun clickedMovie(movie: Movie) {
        val bundle = bundleOf(
            Movie.ID_KEY to movie.id
        )
        findNavController().navigate(R.id.detailedMovieFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}