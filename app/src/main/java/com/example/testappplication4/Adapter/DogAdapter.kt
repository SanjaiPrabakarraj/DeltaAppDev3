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
import com.example.testappplication4.R
import com.example.testappplication4.databinding.ItemDogCardBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>(){

    inner class DogViewHolder(val binding: ItemDogCardBinding) :  RecyclerView.ViewHolder(binding.root){
        internal var itemClickListener: ItemClickListener?=null
        fun setItemClickListener(iTemClickListener: ItemClickListener){
            this.itemClickListener = iTemClickListener
        }
        init {
            itemView.setOnClickListener{view -> itemClickListener?.onClick(view, adapterPosition) }
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<BreedItem>(){
        override fun areItemsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
            return oldItem == newItem
        }
    }
    val picasso = Picasso.get()

    private val differ = AsyncListDiffer(this, diffCallBack)
    var dogs: List<BreedItem>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount() = dogs.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(ItemDogCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.binding.apply {
            val dog = dogs[position]
            dogName.text =dog.name
            picasso.load(dog.image.url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(dogImage)
        }
        holder.setItemClickListener(object:ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Common.home_position = position
                //Snackbar.make(view, "You clicked: "+dogs[position].name, Snackbar.LENGTH_SHORT).show()
                LocalBroadcastManager.getInstance(view.context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position", position))
            }
        })
    }
}