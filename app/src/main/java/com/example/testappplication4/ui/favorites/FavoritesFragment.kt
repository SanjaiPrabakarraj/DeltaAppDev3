package com.example.testappplication4.ui.favorites

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testappplication4.Adapter.DogAdapter
import com.example.testappplication4.Adapter.FavouriteAdapter
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Retrofit.RetrofitInstance
import com.example.testappplication4.databinding.FragmentFavouritesBinding
import retrofit2.HttpException
import java.io.IOException

class FavoritesFragment : Fragment() {

    private lateinit var dogAdapter: DogAdapter
    private lateinit var favouriteAdapter: FavouriteAdapter
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getFavourites(Common.SUB_ID)
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!= null){
                favouriteAdapter.favs = response.body()!!
                Common.favList = favouriteAdapter.favs
            } else{
                Log.e(ContentValues.TAG, "Response not successful")
            }
        }


        return root
    }

    private fun setupRecyclerView() = binding.favouritesRecyclerView.apply {
        favouriteAdapter = FavouriteAdapter()
        adapter = favouriteAdapter
        layoutManager = GridLayoutManager(context,2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}