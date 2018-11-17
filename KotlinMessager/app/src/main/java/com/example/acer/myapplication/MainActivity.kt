package com.example.acer.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button_register.setOnClickListener(){
            performRegister()
        }
        already_have_a_count_textview_register.setOnClickListener(){
            Log.d("MainActivity","try to show Login activity")

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        
    }
    private fun performRegister(){
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"请输入用户名密码",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity","Email is:"+email)
        Log.d("MainActivity","Password is:$password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                //else if successful
                Log.d("Main","成功创建一个用户，该用户的uid是：${it.result.user.uid}")
            }
            .addOnFailureListener{
                Log.d("Main","创建用户失败：${it.message}")
                Toast.makeText(this,"创建用户失败：${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
