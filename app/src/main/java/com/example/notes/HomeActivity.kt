package com.example.notes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        go.setOnClickListener {
            if(passcode.text.toString()=="1234"){
                startActivity(Intent(this,MainActivity::class.java))
            }else if(passcode.text.toString()==""){
                passcode.setError("Required Passcode")
            }else if(passcode.text.toString()!="1234"){
                Toast.makeText(this,"Wrong Passcode",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
