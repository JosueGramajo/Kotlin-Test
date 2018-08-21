package com.example.josuegramajo.kotlintest.Utils

/**
 * Created by josuegramajo on 8/21/18.
 */
import android.content.Context
import android.widget.Toast
import com.example.josuegramajo.kotlintest.Objects.Rol
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class FirestoreUtils{

    private var firestoreDB: FirebaseFirestore? = null

    fun retrieveRols(context: Context, uid:String){
        var rol = Rol()

        firestoreDB = FirebaseFirestore.getInstance()

        firestoreDB!!.collection("Rols").whereEqualTo("uid", uid).addSnapshotListener(EventListener { documentSnapshots, e ->
            if(e != null){
                return@EventListener
            }

            if(!documentSnapshots.isEmpty){
                rol = documentSnapshots.documents.get(0).toObject(Rol::class.java)
            }

            Toast.makeText(context, rol.rol, Toast.LENGTH_LONG).show()
        })
    }

}