package com.example.testappplication4.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Interface.ItemClickListener
import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteRequest
import com.example.testappplication4.R
import com.example.testappplication4.databinding.ItemDogCardBinding
import com.squareup.picasso.Picasso

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    lateinit var requiredDog: BreedItem
    inner class FavouriteViewHolder(val binding: ItemDogCardBinding): RecyclerView.ViewHolder(binding.root) {
        internal var itemClickListener: ItemClickListener?=null
        fun setItemClickListener(iTemClickListener: ItemClickListener){
            this.itemClickListener = iTemClickListener
        }
        init {
            itemView.setOnClickListener{view -> itemClickListener?.onClick(view, adapterPosition) }
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<FavouriteRequest>(){
        override fun areItemsTheSame(oldItem: FavouriteRequest, newItem: FavouriteRequest, ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouriteRequest, newItem: FavouriteRequest, ): Boolean {
            return oldItem == newItem
        }
    }
    val picasso = Picasso.get()

    private val differ = AsyncListDiffer(this, diffCallBack)
    var favs: List<FavouriteRequest>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(ItemDogCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.binding.apply {
            val fav = favs[position]
            /*
            for (dog in Common.dogList)
                    if (fav.image.id == dog.image.id)
                        requiredDog = dog
             */
            getDog(fav.image_id)
            dogName.text = requiredDog.name
            picasso.load(fav.image.url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(dogImage)
        }
        holder.setItemClickListener(object: ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Common.fav_position = position
                //Snackbar.make(view, "You clicked: "+dogs[position].name, Snackbar.LENGTH_SHORT).show()
                LocalBroadcastManager.getInstance(view.context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_FAV).putExtra("fav_position", position))
            }
        })
    }

    fun getDog(imageId:String){
        for (dog in Common.dogList)
            if (dog.image.id == imageId)
                requiredDog = dog
    }

    override fun getItemCount(): Int {
        return favs.size
    }
}