package com.example.testappplication4.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteRequest
import com.example.testappplication4.R
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [DogDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogDetails : Fragment() {

    internal lateinit var dog_img:ImageView
    internal lateinit var dog_name:TextView
    internal lateinit var dog_temperament:TextView
    internal lateinit var dog_height:TextView
    internal lateinit var dog_weight:TextView
    internal lateinit var dog_lifespan:TextView
    internal lateinit var dog:BreedItem
    lateinit var fav:FavouriteRequest
    val picasso = Picasso.get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemView = inflater.inflate(R.layout.fragment_dog_details, container, false)
        val type = arguments?.getInt("type")
        var button = itemView.findViewById(R.id.favourite_button) as Button
        if(type == 1){
            if (arguments?.getString("num") == null)
                dog = Common.dogList[arguments?.getInt("position")!!]
            else
                dog = Common.findDogByNum(arguments?.getInt("position")!!)!!
        }
        else if (type == 2){
            if (arguments?.getString("num") == null)
                fav = Common.favList[arguments?.getInt("position")!!]
            else
                fav = Common.findFavByNum(arguments?.getInt("position")!!)!!
            for (requiredDog in Common.dogList)
                if (requiredDog.image.id == fav.image_id)
                    dog = requiredDog
            button.isVisible = false
        }

        dog_img = itemView.findViewById(R.id.image) as ImageView
        dog_name = itemView.findViewById(R.id.name) as TextView
        dog_temperament = itemView.findViewById(R.id.temperament) as TextView
        dog_height = itemView.findViewById(R.id.height) as TextView
        dog_weight = itemView.findViewById(R.id.weight) as TextView
        dog_lifespan = itemView.findViewById(R.id.lifespan) as TextView

        setDetailDog()

        return itemView
    }

    private fun setDetailDog() {
        dog_name.text = dog.name
        dog_temperament.text = dog.temperament
        dog_height.text = "Height: " + dog.height.metric + " cms"
        dog_weight.text = "Weight: " + dog.weight.metric + " kgs"
        dog_lifespan.text = "Lifespan: " + dog.life_span
        picasso.load(dog.image.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(dog_img)
    }



    companion object {
        internal var instance:DogDetails?=null

        fun getInstance():DogDetails{
            if (instance == null)
                instance = DogDetails()
            return instance!!

        }
    }

}