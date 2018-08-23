package com.example.josuegramajo.kotlintest.Utils

/**
 * Created by josuegramajo on 8/21/18.
 */

import com.example.josuegramajo.kotlintest.Objects.BaseObject
import com.example.josuegramajo.kotlintest.Objects.User
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreUtils{

    enum class fs_collection(val rawValue: String){
        USER("User")
    }

    private var firestoreDB: FirebaseFirestore? = null

    fun retrieveUser(uid:String, callback: (User) -> Unit){
        var user = User()

        firestoreDB = FirebaseFirestore.getInstance()

        firestoreDB!!.collection("User").whereEqualTo("uid", uid).addSnapshotListener(EventListener { documentSnapshots, e ->
            if(e != null){
                return@EventListener
            }

            if(!documentSnapshots.isEmpty){
                user = documentSnapshots.documents.get(0).toObject(User::class.java)
            }

            callback(user)
        })
    }


    fun writeObjectInFirestore(obj:BaseObject, collection:FirestoreUtils.fs_collection, onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        firestoreDB = FirebaseFirestore.getInstance()

        firestoreDB!!.collection(collection.rawValue).document().set(obj).addOnSuccessListener {
            onSuccess("Usuario registrado exitosamente")
        }.addOnFailureListener {
            onFailure("No se pudo registrar un rol para el usuario seleccionado")
        }
    }
}