package com.example.notes

import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_view.*

class NotesActivity : AppCompatActivity() {

    lateinit var notesNames:ArrayList<String>
    lateinit var notesDescs:ArrayList<String>
    lateinit var database: SQLiteDatabase
    lateinit var notesInfo: ArrayList<NotesInfo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)


        notesInfo = arrayListOf()
        notesNames = arrayListOf()
        notesDescs = arrayListOf()
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        database= this.openOrCreateDatabase("notess", Context.MODE_PRIVATE,null)
        var intent:Intent = intent
        if(intent.getStringExtra("flag")=="Work"){
            toolbarr.title="Work Notes"
            var c: Cursor = database.rawQuery("SELECT * FROM workNotes",null)
            if(c.count!=0){
                c.moveToLast()
                for (i in 0..c.count-1){
                    notesNames.add(c.getString(0))
                    notesDescs.add(c.getString(1))
                    notesInfo.add(NotesInfo(c.getString(0),c.getString(1),"work"))
                    c.moveToPrevious()
                }

                listView.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notesNames)

            }
        }else if(intent.getStringExtra("flag")=="Personal"){

            toolbarr.title="Personal Notes"


            var cc: Cursor = database.rawQuery("SELECT * FROM personalNotes",null)
                if(cc.count!=0){
                    cc.moveToLast()
                    for (i in 0..cc.count-1){
                        notesNames.add(cc.getString(0))
                        notesDescs.add(cc.getString(1))
                        notesInfo.add(NotesInfo(cc.getString(0),cc.getString(1),"personal"))
                        cc.moveToPrevious()
                    }

                    listView.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notesNames)


                }
            }


        listView.setOnItemClickListener { parent, view, position, id ->
            var intent:Intent = Intent(this,ViewActivity::class.java)
            intent.putExtra("name",notesNames[position])
            intent.putExtra("desc",notesDescs[position])
            intent.putExtra("category",notesInfo[position].category)
            startActivity(intent)

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
