package com.now.three_days.service

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.now.three_days.data.model.ChallengeVO
import com.now.three_days.data.model.User


abstract class FireService<VO, PK> {

    protected val db = Firebase.firestore

    fun insert(vo:VO, category:String){

        db.collection(category).add(vo!!)

    }

    abstract fun select(): ArrayList<VO>



}