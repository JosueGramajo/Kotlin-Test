package com.example.josuegramajo.kotlintest.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.josuegramajo.kotlintest.Objects.User
import com.example.josuegramajo.kotlintest.R
import com.example.josuegramajo.kotlintest.Utils.FirestoreUtils
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.alert
import org.jetbrains.anko.find
import org.jetbrains.anko.yesButton

/**
 * Created by josuegramajo on 8/23/18.
 */
class RegistrationActivity : BaseActivity(){
    lateinit var emailEditText : EditText
    lateinit var passwordEditText: EditText
    lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        emailEditText = find<EditText>(R.id.et_registration_email)
        passwordEditText = find<EditText>(R.id.et_registration_password)
        nameEditText = find<EditText>(R.id.et_registration_name)

        find<Button>(R.id.btn_registration).setOnClickListener {
            view -> createUser(emailEditText.text.toString(), passwordEditText.text.toString(), nameEditText.text.toString())
        }

        mAuth = FirebaseAuth.getInstance()
    }

    fun createUser(email:String, password:String, name:String){
        this.showProgressDialog("","En proceso de registro")

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, { result ->
            if(result.isSuccessful){
                val newUser = User(name, email, "dev", result.result.user.uid)

                firestore.writeObjectInFirestore(newUser, FirestoreUtils.fs_collection.USER, { successMessage ->
                    this.hideProgressDialog()

                    alert (successMessage){ yesButton {

                        finish()

                    } }.show()
                },{ errorMessage ->
                    this.hideProgressDialog()

                    alert { errorMessage }
                })
            }else{
                this.hideProgressDialog()

                findException(result.exception as Throwable)
            }
        })
    }
}