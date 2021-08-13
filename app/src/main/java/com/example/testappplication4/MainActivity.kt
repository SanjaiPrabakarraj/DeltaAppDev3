package com.example.testappplication4

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testappplication4.Adapter.DogAdapter
import com.example.testappplication4.Common.Common
import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteRequest
import com.example.testappplication4.databinding.ActivityMainBinding
import com.example.testappplication4.ui.DogDetails
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    internal lateinit var dog:BreedItem
    lateinit var fav: FavouriteRequest
    val picasso = Picasso.get()
    var image:Bitmap? = null
    var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var detailsType = 0
    var position = 0

    private val showDetails = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.toString() == Common.KEY_ENABLE_HOME){
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

                val detailsFragment = DogDetails.getInstance()
                position = intent!!.getIntExtra("position", -1)
                detailsType = 1
                val bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putInt("type", detailsType)

                detailsFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, detailsFragment)
                fragmentTransaction.addToBackStack("details")
                fragmentTransaction.commit()
                binding.navView.isGone = true
                binding.shareButton.isGone = false
            }
            else if (intent?.action.toString() == Common.KEY_ENABLE_FAV){
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

                val detailsFragment = DogDetails.getInstance()
                position = intent!!.getIntExtra("fav_position", -1)
                detailsType = 2
                val bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putInt("type", detailsType)

                detailsFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, detailsFragment)
                fragmentTransaction.addToBackStack("details")
                fragmentTransaction.commit()

                binding.navView.isGone = true
                binding.shareButton.isGone = false
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_voting ,R.id.navigation_home, R.id.navigation_favorites, R.id.navigation_upload
            )
        )
        binding.shareButton.isGone = true

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetails, IntentFilter(Common.KEY_ENABLE_HOME))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetails, IntentFilter(Common.KEY_ENABLE_FAV))



        binding.shareButton.setOnClickListener(){
            if(detailsType == 1){
                dog = Common.dogList[position]
            }
            else if (detailsType == 2){
                fav = Common.favList[position]
                for (requiredDog in Common.dogList)
                    if (requiredDog.image.id == fav.image_id)
                        dog = requiredDog
            }
            picasso.load(dog.image.url)
                .into(binding.tempDogImg)
            image = getBitmapFromView(binding.tempDogImg)
            checkPermissions()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> {
                supportFragmentManager.popBackStack("details", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setDisplayShowHomeEnabled(false)
                detailsType = 0
                binding.navView.isGone = false
                binding.shareButton.isGone = true
            }
        }


        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack("details", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        detailsType = 0
        binding.navView.isGone = false
        binding.shareButton.isGone = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            when {
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ->{
                    val share = Intent(Intent.ACTION_SEND)
                    share.type = "image/*"
                    share.putExtra(Intent.EXTRA_STREAM, getImageURI(this, image!!))
                    startActivity(share)
                }
            }
            return
        }
    }

    private fun checkPermissions(){
        var result:Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions){
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(p)
            }
        }
        if(listPermissionsNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 100)
        }
        else{
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/*"
            share.putExtra(Intent.EXTRA_STREAM, getImageURI(this, image!!))
            startActivity(share)
        }

    }

    private fun getImageURI(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, dog.name, null)
        return Uri.parse(path)
    }


    private fun getBitmapFromView(view: ImageView): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}