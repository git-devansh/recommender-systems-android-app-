package com.urbanbunnyapps.movierecommendersystem.recommed

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.urbanbunnyapps.movierecommendersystem.R
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import com.urbanbunnyapps.movierecommendersystem.movie_details.MovieDetailsActivity

class RecommendRecyclerAdapter: RecyclerView.Adapter<RecommendRecyclerAdapter.MyViewHolder>() {

    private var myList = emptyList<MovieDetails>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recommend_movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(myList[position].posterPath)
            .into(holder.itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster))

        holder.itemView.setOnClickListener {
            val intent =  Intent(holder.itemView.context, MovieDetailsActivity::class.java)
            intent.putExtra("movie_id", myList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }


    fun setData(newList: ArrayList<MovieDetails>){
        myList = newList
        notifyDataSetChanged()
    }

}