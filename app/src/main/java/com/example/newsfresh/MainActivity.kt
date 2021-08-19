package com.example.newsfresh

import android.content.Intent
import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter : NewsListAdapter
    var url = "https://gnews.io/api/v4/search?q=business&token=ab8dc0f68667c7e9148dfc37ca27f08e&lang=en"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      var  recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData();

        mAdapter= NewsListAdapter(this)
        recyclerView.adapter=mAdapter

    }

    private fun fetchData()  {

        val jSonObjectRequest =JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener {
            val newsJsonArray = it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val newsObject = News(newsJsonObject.getString("title")
                    ,newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("image")
                    )
                    newsArray.add(newsObject)
                }
                    mAdapter.update(newsArray)
        },
            Response.ErrorListener {

        })




        MySingleTon.getInstance(this).addToRequestQueue(jSonObjectRequest)

    }

    override fun onItemClicked(item: News) {
        //Using intents
       val Uri = Uri.parse(item.url)
//        val intent = Intent(Intent.ACTION_VIEW ,Uri)
//        startActivity(intent)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri)

    }

    override fun on(view: EditText) {
        val st = url.indexOf('=')
        val end=url.indexOf('&')
        val ge = view.text
        url=url.replaceRange(st+1,end,ge)
        //Toast.makeText(applicationContext,url,Toast.LENGTH_SHORT).show()
        fetchData()
    }

}