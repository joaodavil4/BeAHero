package com.example.heros.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.heros.R
import com.example.heros.model.Hero
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class HeroDetailsActivity : AppCompatActivity() {

    lateinit var ivHero : ImageView
    lateinit var tvHeroTitle: TextView
    lateinit var tvHeroDescription : TextView
    lateinit var tvHeroDate : TextView
    lateinit var hero : Hero
    lateinit var timeoutLayout : RelativeLayout
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hero = intent.extras?.getSerializable("hero") as Hero

        setContentView(R.layout.activity_hero_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ivHero = findViewById(R.id.ivHero)
        tvHeroTitle = findViewById(R.id.tvHeroTitle)
        tvHeroDescription = findViewById(R.id.tvHeroDescription)
        tvHeroDate = findViewById(R.id.tvHeroDate)

        timeoutLayout = findViewById(R.id.timeoutLayout)
        progressBar = findViewById(R.id.progressBar)

        Picasso.get().load(hero.imageURL).resize(300,300).into(ivHero)
        tvHeroTitle.text = hero.heroName.capitalize()
        tvHeroDescription.text = hero.heroDesc
        tvHeroDate.text = hero.date

        progressBar.visibility = View.GONE

    }

    fun shareContent(){
        var content = hero.heroName

        content += "\nDescription: ${hero.heroDesc}"
        content += "\nDate: ${hero.date}"

        Log.i("Details",content)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,content)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share"))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hero_details, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when(id){
            R.id.action_share -> shareContent()
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

}