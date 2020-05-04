package com.example.heros.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.heros.R
import com.example.heros.activity.adapter.HeroListAdapter
import com.example.heros.model.Hero
import com.example.heros.utilities.APInfos
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var gridViewHeros : GridView
    var HerosArray = arrayListOf<Hero>()
    lateinit var HeroAdapter : HeroListAdapter
    lateinit var timeoutLayout : LinearLayout
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        timeoutLayout = findViewById(R.id.timeoutLayout)
        gridViewHeros = findViewById(R.id.gridViewTypes)
        gridViewHeros.setOnItemClickListener(this)
        HeroAdapter = HeroListAdapter(this,HerosArray)
        gridViewHeros.adapter = HeroAdapter

        progressBar.visibility = View.VISIBLE

        //OkHttp
        val httpClient = OkHttpClient.Builder().connectTimeout(5,TimeUnit.SECONDS).build()

        val ts = (System.currentTimeMillis() / 1000).toString()
        val apikey: String = APInfos.PUBLIC_KEY
        val hash: String = APInfos.md5(ts + APInfos.PRIVATE_KEY + APInfos.PUBLIC_KEY)
        val limit = "100"

        val urlBuilder = HttpUrl.parse(APInfos.CHARACTER_BASE_URL)!!.newBuilder()
            .addQueryParameter(APInfos.TIMESTAMP, ts)
            .addQueryParameter(APInfos.API_KEY, apikey)
            .addQueryParameter(APInfos.HASH, hash)
            .addQueryParameter(APInfos.LIMIT, limit)
            .build()

        val request = Request.Builder().url(urlBuilder.toString()).build()

        httpClient.newCall(request).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {
                Log.i("MainActivity",e.message + "TIMEOUT TIMEOUT")
                timeoutLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body()?.string() ?: "{}"

                val jsonResponse = JSONObject(responseString)
                val HerosJsonArray = jsonResponse.getJSONObject("data").getJSONArray("results")

                for(i in 0 until HerosJsonArray.length()){
                    val typeJsonObj = HerosJsonArray.getJSONObject(i)
                    val heroID = typeJsonObj.getString("id")
                    val heroName = typeJsonObj.getString("name")
                    val heroDesc = typeJsonObj.getString("description")
                    val thumbnail = typeJsonObj.getJSONObject("thumbnail")

                    val imageURL = if (!typeJsonObj.isNull("thumbnail")) {
                        """${thumbnail.getString("path")}.${thumbnail.getString("extension")}"""
                    } else {
                        ""
                    }
                    val date = typeJsonObj.getString("modified")
                    val typeObj = Hero(heroID, heroName, heroDesc, imageURL, date)
                    HerosArray.add(typeObj)
                }

                //Atualiza a interface
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    HeroAdapter.notifyDataSetChanged()
                }
            }

        })
    }



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val bundle = Bundle()
        bundle.putSerializable("hero", HerosArray[position])

        val intent = Intent(this, HeroDetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}



