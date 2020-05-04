package com.example.heros.model

import java.io.Serializable

class Hero(var heroId : String = "", var heroName : String = "",
           var heroDesc : String = "", var imageURL : String = "", var date : String = "") : Serializable