package com.example.testappplication4.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testappplication4.Adapter.DogAdapter
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.R
import com.example.testappplication4.Retrofit.RetrofitInstance
import com.example.testappplication4.databinding.FragmentHomeBinding
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var dogAdapter: DogAdapter
    private lateinit var searchAdapter: DogAdapter
    private var _binding: FragmentHomeBinding? = null
    var lastSuggest:MutableList<String> = ArrayList()
    var compositeDisposable = CompositeDisposable()
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchBar.setHint("Enter Dog Name")
        binding.searchBar.setCardViewElevation(10)
        binding.searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                if(!enabled)
                    binding.dogRecyclerView.adapter = dogAdapter
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())

            }

            override fun onButtonClicked(buttonCode: Int) {

            }
        })

        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getDogs()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!= null){
                dogAdapter.dogs = response.body()!!
                Common.dogList = dogAdapter.dogs
            } else{
                Log.e(ContentValues.TAG, "Response not successful")
            }
        }

        return root
    }

    private fun startSearch(text: String) {
        if (Common.dogList.size > 0) {
            val result = ArrayList<BreedItem>()
            for (dog in Common.dogList)
                if (dog.name.toLowerCase().contains(text.toLowerCase()))
                    result.add(dog)
            searchAdapter = DogAdapter()
            searchAdapter.dogs = result
            binding.dogRecyclerView.adapter = searchAdapter
        }
    }


    private fun setupRecyclerView() = binding.dogRecyclerView.apply {
        dogAdapter = DogAdapter()
        adapter = dogAdapter
        layoutManager = GridLayoutManager(context,2)
        lastSuggest.clear()
        for (dog in Common.dogList)
            lastSuggest.add(dog.name)
        binding.searchBar.lastSuggestions = lastSuggest
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}