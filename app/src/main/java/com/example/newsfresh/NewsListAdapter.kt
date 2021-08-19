package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.concurrent.TimeoutException

class NewsListAdapter(private val listener :NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items : ArrayList<News> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        var viewHolder :NewsViewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder;
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items.get(position)
        holder.titleView.text=currentItem.title
        holder.descText.text=currentItem.description
        Glide.with(holder.itemView.context).load(currentItem.imageURL).into(holder.imageView)
          holder.box.setOnClickListener{
              listener.on(holder.box)
          }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update (updatedNews:ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.title)
    val imageView : ImageView = itemView.findViewById(R.id.imageView)
    val descText :TextView = itemView.findViewById(R.id.description)
    val box :EditText = itemView.findViewById(R.id.editTextTextPersonName)

}

interface NewsItemClicked {
    fun onItemClicked(item:News)
    fun on(view:EditText)
}