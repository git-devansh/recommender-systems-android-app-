package com.urbanbunnyapps.movierecommendersystem.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.urbanbunnyapps.movierecommendersystem.R
import com.urbanbunnyapps.movierecommendersystem.recommed.RecommedViewModel
import com.urbanbunnyapps.movierecommendersystem.recommed.RecommendRepository
import com.urbanbunnyapps.movierecommendersystem.recommed.api.RecommendApi
import com.urbanbunnyapps.movierecommendersystem.recommed.api.RetrofitInstance
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urbanbunnyapps.movierecommendersystem.recommed.RecommendRecyclerAdapter


class RecommendMovieFragment : Fragment() {

    lateinit var apiService: RecommendApi
    lateinit var recommendRepository: RecommendRepository
    private lateinit var viewModel: RecommedViewModel

    private lateinit var recyclerView: RecyclerView
    private val myAdapter by lazy { RecommendRecyclerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // code here
        apiService = RetrofitInstance.api
        recommendRepository = RecommendRepository(apiService)
        viewModel = RecommedViewModel(recommendRepository)

        val autoCompleteTextView: AutoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        var selectedItem: String? = null
        viewModel.titles.observe(viewLifecycleOwner, Observer { t ->
            if (t.isSuccessful && t.body() != null) {
                val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_expandable_list_item_1, t.body()!!.title)
                autoCompleteTextView.setAdapter(adapter)
                autoCompleteTextView.onItemClickListener =
                    OnItemClickListener { parent, arg1, pos, id ->
                        selectedItem = autoCompleteTextView.text.toString()
                        //your stuff
                        Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_LONG).show()
                        displayRecommendedMovies(view, selectedItem!!)
                    }
            }


        })

        recyclerView = view.findViewById<RecyclerView>(R.id.recommendedRecyclerView)

    }

    private fun displayRecommendedMovies(view: View, selectedMovie: String) {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.getRecommendMovies(selectedMovie)
        viewModel.recommendMoviesVar.observe(viewLifecycleOwner, Observer { t ->
            if (t != null) {
                myAdapter.setData(t)
            }
        })

    }

    private fun setUpRecyclerView(){
    }
}