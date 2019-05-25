package com.example.notes

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)



        var categories:ArrayList<String> = arrayListOf("Work","Personal")
        categorySelector.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories)

        save.setOnClickListener {
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
            var intent:Intent = Intent()
            intent.putExtra("name",notesName.text.toString())
            intent.putExtra("desc",notesDesc.text.toString())
            intent.putExtra("category",categories[categorySelector.selectedItemPosition].toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }

}
