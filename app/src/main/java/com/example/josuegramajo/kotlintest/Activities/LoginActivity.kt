package com.example.josuegramajo.kotlintest.Activities

import android.os.Bundle
import android.widget.EditText
import com.example.josuegramajo.kotlintest.R
import com.example.josuegramajo.kotlintest.Utils.Companions
import com.google.firebase.auth.*
import org.jetbrains.anko.*

/**
 * Created by josuegramajo on 6/9/17.
 */

class LoginActivity : BaseActivity(){
    lateinit var email : EditText
    lateinit var password : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.et_email) as EditText
        password = findViewById(R.id.et_password) as EditText

        findViewById(R.id.btn_login).setOnClickListener({
            view -> doLogin()
        })

        findViewById(R.id.btn_register).setOnClickListener({
            view -> startActivity<RegistrationActivity>()
        })

        mAuth = FirebaseAuth.getInstance()

        mAuth.currentUser?.let {
            this.showProgressDialog("","Autenticando")

            this.retrieveUser()
        }
    }

    fun doLogin(){
        if(email.text.toString().equals("")){
            toast("Ingrese usuario")
            return
        }
        if(password.text.toString().equals("")){
            toast("Ingrese contraseña")
            return
        }

        loginUser(email.text.toString(),password.text.toString())
    }

    fun loginUser(email:String,password:String){
        this.showProgressDialog("","Autenticando")

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,{ result ->
            if(result.isSuccessful){
                //startActivity<MainActivity>("email" to email)
                this.retrieveUser()
            }else{
                findException(result.exception as Throwable)
            }
        })
    }

    fun retrieveUser(){
        mAuth.currentUser?.let { user ->
            firestore.retrieveUser(user.uid, {
                Companions.user = it

                this.hideProgressDialog()

                startActivity<MainActivity>()
            })
        }
    }

    /*fun showRegistration(){
        var emailEditText = EditText(this)
        var passwordEditText = EditText(this)

        alert("Favor de ingresar sus credenciales","Registro") {
            customView{ linearLayout {
                orientation = LinearLayout.VERTICAL
                lparams(width = matchParent)
                padding = dip(10)
                emailEditText = editText{ hint = "Email"; setSingleLine(true) }
                passwordEditText = editText{ hint = "Password"; setSingleLine(true); topPadding = dip(10); inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD }
            } }
            yesButton { createUser(emailEditText.text.toString(), passwordEditText.text.toString()) }
            noButton { }
        }.show()
    }*/


}