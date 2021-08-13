package com.example.testappplication4.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import com.example.testappplication4.Common.Common
import com.example.testappplication4.R
import com.example.testappplication4.Retrofit.RetrofitInstance
import com.example.testappplication4.URIPathHelper
import com.example.testappplication4.databinding.FragmentHomeBinding
import com.example.testappplication4.databinding.FragmentUploadImageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URI

class UploadImage : Fragment() {

    private var _binding: FragmentUploadImageBinding? = null
    private val binding get() = _binding!!

    var fileUri:Uri? = null
    var mediaPath:String? = null
    var mImageFileLocation = ""
    var postPath: String? = null

    lateinit var intent:Intent
    lateinit var file:File
    lateinit var filePath:String
    val uriPathHelper = URIPathHelper()
    lateinit var bitmap:Bitmap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUploadImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val fields: HashMap<String?, RequestBody?> = HashMap()
        fields["sub_id"] = (Common.SUB_ID).toRequestBody()

        binding.galleryButton.setOnClickListener(){
            intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 123)
        }
        binding.uploadButton.setOnClickListener(){
            /*filePath = File(URI(intent.data.toString()))
            //file = MultipartBody.Part.createFormData("file", filePath.name)
            //lifecycleScope.launchWhenStarted { RetrofitInstance.api.uploadImage(Common.SUB_ID, filePath) }
            println("location2")
            //filePath = uriPathHelper.getPath(requireContext(), intent.data!!)!!
            //file = File(filePath)
            println("location3")
            fields["file"] = (file).asRequestBody()
            println("location4")
            CoroutineScope(Dispatchers.IO).launch {
                println("Location1")
                val response = RetrofitInstance.api.uploadImage(fields)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        Log.d("Pretty Printed JSON :", response.code().toString())
                    }
                    else
                        Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }

             */
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            binding.uploadImage.setImageURI(data?.data)
        }
        println("Hello")
    }

    companion object {

    }
}
