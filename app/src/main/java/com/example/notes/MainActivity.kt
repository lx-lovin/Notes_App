package com.example.notes

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {


    lateinit var database:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database= this.openOrCreateDatabase("notess",Context.MODE_PRIVATE,null)
        database.execSQL("CREATE TABLE IF NOT EXISTS workNotes(Name VARCHAR,Description VARCHAR)")
        database.execSQL("CREATE TABLE IF NOT EXISTS personalNotes(Name VARCHAR,Description VARCHAR)")

        addNote.setOnClickListener {
            var intent:Intent = Intent(this,AddNote::class.java)
            startActivityForResult(intent,2)
        }

        workNotes.setOnClickListener {
            var intent:Intent = Intent(this,NotesActivity::class.java)
            intent.putExtra("flag","Work")
            startActivity(intent)
        }


        personalNotes.setOnClickListener {
            var intent:Intent = Intent(this,NotesActivity::class.java)
            intent.putExtra("flag","Personal")
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2){
            var noteName:String = data!!.getStringExtra("name").toString()
            var noteDesc:String = data!!.getStringExtra("desc").toString()
            var noteCategory:String = data!!.getStringExtra("category").toString()
            if(noteCategory=="Work"){
                database.execSQL("INSERT INTO workNotes (Name,Description) VALUES ('" + noteName + "','" + noteDesc + "')")
            }else if(noteCategory=="Personal"){
                database.execSQL("INSERT INTO personalNotes (Name,Description) VALUES ('" + noteName + "','" + noteDesc + "')")
                Log.i("Saved","Personallllll")
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }


}
