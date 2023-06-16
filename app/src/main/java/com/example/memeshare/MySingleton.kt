package com.example.memeshare

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
// one isnatance is made for the complete life cycle of the app and this instance  will be used mutiple of time

    class MySingleton constructor(context: Context) {
        companion object {
            @Volatile
            private var instance: MySingleton? = null
            fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: MySingleton(context).also {
                        instance = it
                    }
                }
        }



        private val requestQueue: RequestQueue by lazy {// so that this class can only access this request
            // applicationContext is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            Volley.newRequestQueue(context.applicationContext)
        }
        fun <T> addToRequestQueue(req: Request<T>) {
            requestQueue.add(req)
        }
    }



