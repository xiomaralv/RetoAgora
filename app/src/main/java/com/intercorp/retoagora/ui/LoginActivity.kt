package com.intercorp.retoagora.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.intercorp.retoagora.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        btnFb.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{

                override fun onSuccess(result: LoginResult?) {
                    result?.let {
                        val token = it.accessToken
                        val credential = FacebookAuthProvider.getCredential(token.token)
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { it ->
                            if(it.isSuccessful){
                                val email = it.result?.user?.email?:""
                                val urlPhoto = it.result?.user?.photoUrl?:""
                                goHome(email, urlPhoto.toString())
                            }
                        }
                    }
                }

                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException?) {
                    showError()
                }

            })
        }
        val prefs = getSharedPreferences(getString(R.string.preferences_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)
        val urlImage = prefs.getString("urlImage",null)
        if(email!=null && urlImage!=null){
            goHome(email, urlImage)
        }
    }

    private fun showError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ocurriò un error")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goHome(email: String, urlImage: String){
        val intentLogin = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("urlImage",urlImage)
        }
        startActivity(intentLogin)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }
}