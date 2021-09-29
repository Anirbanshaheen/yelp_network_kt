package com.example.yelp_network_kt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY =
    "3p4C1WEWaJO2SJQ9ugO6bFkVNhEDVA5t5E6IfQ608AzbeywiVwM90t5WFPX-1LPTLacbZcoGTPdK-9u3_HMZBDS-U9N2-lFtxwYNcj84_GtPRH8jDnwVbGdEGb5RYXYx"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * RecyclerView Process
         */

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantAdapter(this, restaurants)  /** 1. Adapter for recyclerview */

        /** 8. Declare recyclerview and layout manager */
        rv_restaurant.adapter = adapter
        rv_restaurant.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /** For Interface instance */
        val yelpService = retrofit.create(YelpService::class.java)

        yelpService.searchRestaurants("Bearer $API_KEY", "Avocado Toast", "New York")
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    //Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) "No data"
                    else restaurants.addAll(body.allRestaurants)
                    adapter.notifyDataSetChanged()

                    /** 9. Check response and add all lists */
                    if (body != null) {
                        restaurants.addAll(body.allRestaurants)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}