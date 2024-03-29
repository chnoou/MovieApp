package com.chnoou.movieapp.ui.detailed_movie_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chnoou.movieapp.backend.data.Movie
import com.chnoou.movieapp.backend.data.MovieDetails
import com.chnoou.movieapp.databinding.DetailedMovieFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedMovieFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedMovieFragment()
    }

    private val viewModel: DetailedMovieViewModel by viewModel()
    private var _binding: DetailedMovieFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailedMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.getInt(Movie.ID_KEY)

        if (id == null) {
            Toast.makeText(requireContext(), "Something went wrong when getting movie id", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        viewModel.getMovieFromId(id)

        initUI()

    }

    fun initUI() {
        viewModel.movie.observe(viewLifecycleOwner) {
            it?.let {
                updateMovie(it)
            }
        }
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            it?.let {
                updateMovieDetails(it)
            }
        }
    }

    fun updateMovie(movie: Movie) {
        binding.title.text = movie.title
        binding.rating.rating = movie.getRatingPercentage()
        Glide.with(binding.movieImage)
            .load(movie.getImageUrl())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.movieImage)
    }

    fun updateMovieDetails(movieDetails: MovieDetails) {
        binding.info.text = movieDetails.printInfo()
        binding.description.text = movieDetails.overview
        binding.languages.text = movieDetails.printLanguages()
        binding.production.text = movieDetails.printProductionCountries()
        binding.companies.text = movieDetails.printProductionCompanies()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}