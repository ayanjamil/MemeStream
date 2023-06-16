package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
//import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
//import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var currentimageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    private fun loadmeme (){
        val progressBar: ProgressBar = findViewById(R.id.progressbar)
        progressBar.visibility=View.VISIBLE// whenever the load meme function is called the prograss bar will appear

        //val queue = Volley.newRequestQueue(this) //  this queue stores the requests in sequence  this is for specifing this activity (this tab)
        val url = "https://meme-api.com/gimme" // this website we coppied from github and this is called for to recive and send data


        // Request a string response from the provided URL.
        // the memes we are having are in jayson object
        val jsonojectrequest = JsonObjectRequest(
            Request.Method.GET, url,null,//GET is a type of requsest which recives data
            Response.Listener { response ->
                currentimageurl = response.getString("url")
                // to laod image we need to put url in image as in API image was in url
                // as soon as we get an image we have to set progress bar visibility to 0 we get responce very fast but it is glide which is needing time so we have to adjust progressbar in it
                // glide also downlaod and puts the url content in image
                Glide.with(this).load(currentimageurl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(  // we put a listner in glide which will set visibility of progressbar to 0 when it happens
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }


                    override fun onResourceReady( // when resource is ready we have to set visibility to 0
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(findViewById(R.id.imageview))// GLID library for putting url in image

            }, // if the request was successfull then above will be followed if not then below
            Response.ErrorListener {

            })

        // Add the request to the RequestQueue.
      // val queue = MySingleton.getInstance(this.applicationContext).requestQueue
        //MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonojectrequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonojectrequest)// calling the function in Mysinglton and giving the context of this
        // addTORequestQueue is for adding the request in the queue which is a jason object request defined above


    }
    fun nextmeme(view: View) {
        loadmeme()

    }
    fun sharememe(view: View) {
        val intent=Intent(Intent.ACTION_SEND) // intent helps to use the fetures of device like send, airplane mode etc
        intent.type="text/plain"// defines what we are sharing like for image jpeg for audio mp3 etc
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme $currentimageurl")// there will be this messege displayed
        val chooser=Intent.createChooser(intent,"Shared this meme using ....")
        startActivity(chooser)


    }
}