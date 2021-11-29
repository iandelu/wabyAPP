package com.racoon.waby.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.racoon.waby.data.model.Spot
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SpotRepository @Inject constructor(private val spotList: CollectionReference) {
    class SpotRepository @Inject constructor(private val spotList: CollectionReference) {

        fun addNewSpot(spot: Spot) {
            try {
                spotList.document(spot.idSpot).set(spot)
            } catch (e: Exception) {
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getAllSpots():LiveData<MutableList<Spot>>{
            val mutableList = MutableLiveData<MutableList<Spot>>()
            spotList.get().addOnSuccessListener {
                val spotDataList = mutableListOf<Spot>()
                for (document in it){
                    val spot = document.toObject(Spot::class.java)
                    spotDataList.add(spot)
                }
                mutableList.value = spotDataList
            }
            return mutableList
        }

        fun getSingleSpot(idSpot: String): Spot {

            var spot= Spot("not_found")
            spotList
            val docRef = spotList.document(idSpot)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {

                        spot = document.toObject(Spot::class.java)!!
                        /*val adminUser = document.getString("adminUser")
                        val badges = document.get("badges") to ArrayList<String>()
                        val capacity = document.getLong("capacity")?.toInt()
                        val description = document.get("description").toString()
                        val images = document.get("images") to ArrayList<String>()
                        val location = document.getString("location")
                        val name = document.get("name").toString()
                        val phoneNumber = document.get("phoneNumber").toString()
                        val spotType = document.getString("spotType")
                        val website = document.getString("website")
                        val ratings = document.get("ratings") to ArrayList<Int>()

                        val spot = Spot(
                            idSpot,
                            name,
                            adminUser,
                            phoneNumber,
                            capacity,
                            location,
                            ratings,
                            website,
                            spotType,
                            badges,
                            images,
                            description
                        )*/

                    }
                }
            return spot
        }


    }
}
