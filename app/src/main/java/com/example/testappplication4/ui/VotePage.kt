package com.example.testappplication4.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.testappplication4.Adapter.DogAdapter
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteResponse
import com.example.testappplication4.R
import com.example.testappplication4.Retrofit.RetrofitInstance.api
import com.example.testappplication4.databinding.FragmentVotePageBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class VotePage : Fragment() {

    private lateinit var dogAdapter: DogAdapter
    private var _binding: FragmentVotePageBinding? = null
    private val binding get() = _binding!!
    val picasso = Picasso.get()
    lateinit var dog: BreedItem
    lateinit var favouriteResponse: FavouriteResponse
    lateinit var call: Call<FavouriteResponse>
    var flag = 0
    var position = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVotePageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupVotePage()
        binding.upvoteButton.setOnClickListener() {
            setupVotePage()
        }
        binding.downvoteButton.setOnClickListener(){
            setupVotePage()
        }

        return root
    }

    fun setupVotePage(){
        val random = (0..Common.dogList.size).random()
        Common.vote_position = random
        dog = Common.dogList[random]
        binding.dogName.text = dog.name
        picasso.load(dog.image.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.dogImage)
    }
}