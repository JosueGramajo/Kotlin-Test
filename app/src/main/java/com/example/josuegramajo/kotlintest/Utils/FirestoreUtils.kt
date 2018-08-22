package com.example.josuegramajo.kotlintest.Utils

/**
 * Created by josuegramajo on 8/21/18.
 */

import com.example.josuegramajo.kotlintest.Objects.Rol
import com.example.josuegramajo.kotlintest.Objects.Role
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreUtils{

    private var firestoreDB: FirebaseFirestore? = null

    fun retrieveRols(uid:String, callback: (Role) -> Unit){
        var role = Role()

        firestoreDB = FirebaseFirestore.getInstance()

        firestoreDB!!.collection("Roles").whereEqualTo("uid", uid).addSnapshotListener(EventListener { documentSnapshots, e ->
            if(e != null){
                return@EventListener
            }

            if(!documentSnapshots.isEmpty){
                role = documentSnapshots.documents.get(0).toObject(Role::class.java)
            }

            callback(role)
        })
    }

    fun registerUserRole(role:Role, callback: (String) -> Unit){
        firestoreDB = FirebaseFirestore.getInstance()

        firestoreDB!!.collection("Roles").document().set(role).addOnSuccessListener {
            callback("Usuario registrado exitosamente")
        }.addOnFailureListener {
            callback("No se pudo registrar un rol para el usuario seleccionado")
        }
    }
}