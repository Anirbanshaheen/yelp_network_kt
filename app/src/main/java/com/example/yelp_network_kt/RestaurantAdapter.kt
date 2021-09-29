package com.example.yelp_network_kt

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantAdapter(val context: Context, val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {     /** 2. Extend Recyclerview.Adapter class */

    /** 5. New Layout for recycler view */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false)
        )
    }

    /** 6. Get the list position and create the bind function */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    /** 4. List size */
    override fun getItemCount() = restaurants.size

    /** 3. ViewHolder class */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** 7. All view shows here */
        @SuppressLint("SetTextI18n")
        fun bind(restaurant: YelpRestaurant) {
            itemView.tvName.text = restaurant.name
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.tvNumReview.text = "${restaurant.numReview} Reviews"
            itemView.tvAddress.text = restaurant.location.address
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvDistance.text = restaurant.displayDistance()
            itemView.tvPrice.text = restaurant.price

            Glide.with(context).load(restaurant.imageUrl).apply(
                RequestOptions().transform(
                    CenterCrop(), RoundedCorners(20)
                )
            ).into(itemView.imageView)
        }
    }
}
