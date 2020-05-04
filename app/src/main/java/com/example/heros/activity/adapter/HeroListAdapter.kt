package com.example.heros.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.heros.R
import com.example.heros.model.Hero
import com.squareup.picasso.Picasso

class HeroListAdapter(context: Context, val objects: ArrayList<Hero>) : ArrayAdapter<Hero>(context, 0, objects) {

    var inflater : LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val holder : ViewHolder

        val HeroObj = this.objects[position]

        if (convertView == null){
            view = inflater.inflate(R.layout.hero_adapter,parent,false)
            holder = ViewHolder(view = view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        holder.tvHeroTitle.text = HeroObj.heroName
        Picasso.get().load(HeroObj.imageURL).resize(150,150).into(holder.ivHeroImage)

        return view
    }


    class ViewHolder(view: View){
        var tvHeroTitle : TextView = view.findViewById(R.id.tvType)
        var ivHeroImage : ImageView = view.findViewById(R.id.heroBackground)
    }

}