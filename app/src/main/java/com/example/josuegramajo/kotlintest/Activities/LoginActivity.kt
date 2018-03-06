package com.example.josuegramajo.kotlintest.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.example.josuegramajo.kotlintest.R
import com.google.firebase.auth.*
import org.jetbrains.anko.*

/**
 * Created by josuegramajo on 6/9/17.
 */

class LoginActivity : AppCompatActivity(){

    //@BindView(R.id.et_email)
    lateinit var email : EditText

    //@BindView(R.id.et_password)
    lateinit var password : EditText

    lateinit var mAuth:FirebaseAuth

    init {
        //ButterKnife.bind(this, view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.et_email) as EditText
        password = findViewById(R.id.et_password) as EditText

        findViewById(R.id.btn_login).setOnClickListener({
            view -> doLogin()
        })

        mAuth = FirebaseAuth.getInstance()
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
        //startActivity<MainActivity>("email" to "jgramajo@digitalgeko.com")
        //toast("Email: ${email.text.toString()} Password: ${password.text.toString()}")
    }

    override fun onStart() {
        super.onStart()
        var currentUser = mAuth.getCurrentUser()
        if(currentUser != null){
            startActivity<MainActivity>("email" to currentUser.email.toString())
        }
    }

    fun createUser(email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                this, { result ->
                if(result.isSuccessful){
                    makeAnkoAlert("El usuario fue creado exitosamente","Exito")
                }else{
                    findException(result.exception as Throwable)
                }
        })
    }

    fun loginUser(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,{ result ->
            if(result.isSuccessful){
                startActivity<MainActivity>("email" to email)
            }else{
                findException(result.exception as Throwable)
            }
        })
    }

    fun findException(exception:Throwable){
        try{
            throw exception
        } catch (wpe:FirebaseAuthWeakPasswordException){
            makeAnkoAlert("La contraseña ingresada es muy debil","Error")
        } catch (ice:FirebaseAuthInvalidCredentialsException){
            makeAnkoAlert("Credenciales invalidas","Error")
        } catch (uce:FirebaseAuthUserCollisionException){
            makeAnkoAlert("Este usuario ya existe","Error")
        } catch(iue:FirebaseAuthInvalidUserException){
            makeAnkoAlert("No existen registros de este usuario","Error")
        } catch (e:Exception){
            makeAnkoAlert("Ocurrio un error, porfavor trate nuevamente","Error")
        }
    }

    fun makeAnkoAlert(content:String,title:String){
        alert(content,title) {
            yesButton {  }
        }.show()
    }

}