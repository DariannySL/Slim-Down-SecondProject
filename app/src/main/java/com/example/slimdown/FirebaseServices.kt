package com.example.slimdown

import com.example.slimdown.Class.Users
import com.example.slimdown.Class.Weights
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.util.*

class FirebaseServices {

    interface IResult<T> {
        fun onSuccess(user: T?)
        fun onError(ex: Exception)
    }

    companion object {
        private val TAG = "DocSnippets"
    }


    val db = FirebaseFirestore.getInstance()

    val firestoreSettings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

    init {
        db.firestoreSettings = firestoreSettings
    }

    fun addUser( id: String, name: String, lastName: String, actualWeight: Float, lostWeight: Float) {

        val user = Users(
            name,
            lastName,
            actualWeight,
            lostWeight
        )
        db.collection("users").document(id).set(user)
    }

    fun addWeight(idUser: String, weight: Float, date: Date) {

        val weights = Weights(idUser, weight, date)
        db.collection("weights").add(weights)
    }

    fun getUserById(id: String, response: IResult<Users>){
        db.collection("users").document(id).get()
            .addOnSuccessListener { document ->
                run {
                    val user = document.toObject(Users::class.java)
                    response.onSuccess(user)
                }
            }
            .addOnFailureListener { ex ->
                run {
                    response.onError(ex)
                }
            }
    }

    fun getWeightById(id: String, response: IResult<List<Weights>>) {
        db.collection("weights").whereEqualTo("idUser", id).get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Weights>()
                for (document in documents) {
                    val weight = document.toObject(Weights::class.java)
                    list.add(weight)
                }
                response.onSuccess(list)
            }
            .addOnFailureListener { ex ->
                run {
                    response.onError(ex)
                }
            }
    }

    fun updateUser(id: String, name: String, lastName: String, actualWeight: Float, lostWeight: Float) {

        val user = Users(
            name,
            lastName,
            actualWeight,
            lostWeight
        )
        db.collection("users").document(id).update(mapOf(
            "name" to user.lastName,
            "lastname" to user.lastName,
            "actualWeight" to user.actualWeight,
            "lostWeight" to user.lostWeight
        ))
    }
}

//interface IResult<T>{
//    fun onSuccess(items: T?)
//    fun onError(exception: Exception)
//}
