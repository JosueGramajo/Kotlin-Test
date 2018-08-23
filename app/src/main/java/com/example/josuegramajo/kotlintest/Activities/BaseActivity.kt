package com.example.josuegramajo.kotlintest.Activities

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.example.josuegramajo.kotlintest.Utils.Companions
import com.example.josuegramajo.kotlintest.Utils.FirestoreUtils
import com.google.firebase.auth.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.yesButton

/**
 * Created by josuegramajo on 8/22/18.
 */
open class BaseActivity : AppCompatActivity(){
    lateinit var dialog : ProgressDialog
    val firestore = FirestoreUtils()
    lateinit var mAuth: FirebaseAuth

    fun makeAnkoAlert(content:String,title:String){
        alert(content,title) {
            yesButton {  }
        }.show()
    }

    fun showProgressDialog(title: String, message: String){
        dialog = indeterminateProgressDialog(title, message)
        dialog.show()
    }

    fun hideProgressDialog(){
        dialog.hide()
    }

    fun findException(exception:Throwable){
        try{
            throw exception
        } catch (wpe: FirebaseAuthWeakPasswordException){
            makeAnkoAlert("La contraseña ingresada es muy debil","Error")
        } catch (ice: FirebaseAuthInvalidCredentialsException){
            makeAnkoAlert("Credenciales invalidas","Error")
        } catch (uce: FirebaseAuthUserCollisionException){
            makeAnkoAlert("Este usuario ya existe","Error")
        } catch(iue: FirebaseAuthInvalidUserException){
            makeAnkoAlert("No existen registros de este usuario","Error")
        } catch (e:Exception){
            makeAnkoAlert("Ocurrio un error, porfavor trate nuevamente","Error")
        }
    }
}