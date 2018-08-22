package com.example.josuegramajo.kotlintest.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.example.josuegramajo.kotlintest.Objects.Role
import com.example.josuegramajo.kotlintest.R
import com.example.josuegramajo.kotlintest.Utils.Companions
import com.example.josuegramajo.kotlintest.Utils.FirestoreUtils
import com.google.firebase.auth.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert

/**
 * Created by josuegramajo on 6/9/17.
 */

class LoginActivity : BaseActivity(){
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.et_email) as EditText
        password = findViewById(R.id.et_password) as EditText

        findViewById(R.id.btn_login).setOnClickListener({
            view -> doLogin()
        })

        findViewById(R.id.btn_register).setOnClickListener({
            view -> showRegistration()
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
    }

    override fun onStart() {
        super.onStart()

        mAuth.currentUser?.let {
            this.showProgressDialog("","Autenticando")

            this.retrieveUserRol()
        }
    }

    fun loginUser(email:String,password:String){
        this.showProgressDialog("","Autenticando")

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,{ result ->
            if(result.isSuccessful){
                //startActivity<MainActivity>("email" to email)
                this.retrieveUserRol()
            }else{
                findException(result.exception as Throwable)
            }
        })
    }

    fun retrieveUserRol(){
        mAuth.currentUser?.let { user ->
            FirestoreUtils().retrieveRols(user.uid, {
                Companions.role = it
                Companions.uid = user.uid
                Companions.email = user.email!!

                this.hideProgressDialog()

                startActivity<MainActivity>()
            })
        }
    }

    fun showRegistration(){
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
    }

    fun createUser(email:String,password:String){
        this.showProgressDialog("","En proceso de registro")

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, { result ->
            if(result.isSuccessful){
                val newRole = Role("dev", result.result.user.uid)

                firestore.registerUserRole(newRole, {
                    this.hideProgressDialog()

                    alert (it){ yesButton {

                        Companions.role = newRole
                        Companions.uid = result.result.user.uid
                        Companions.email = result.result.user.email!!
                        startActivity<MainActivity>()

                    } }.show()
                })

            }else{
                this.hideProgressDialog()

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
}