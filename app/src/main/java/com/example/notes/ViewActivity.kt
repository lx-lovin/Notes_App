package com.example.notes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view.*
import android.support.v4.content.ContextCompat.startActivity
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.Settings
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ViewActivity : AppCompatActivity() {
    lateinit var database: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        setSupportActionBar(toolbarrr)
        database= this.openOrCreateDatabase("notess",Context.MODE_PRIVATE,null)
        database.execSQL("CREATE TABLE IF NOT EXISTS counter(count INT)")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var intent:Intent = intent
        noteHeading.text = intent.getStringExtra("name")
        viewDesc.text = intent.getStringExtra("desc")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_operations,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.share){
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("desc"))
            try {
                startActivity(whatsappIntent)
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(this,"Whatsapp is not installed",Toast.LENGTH_SHORT).show()
            }

        }
        if (item!!.itemId == R.id.upload){

            //Checking  the Connectivity

            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI
                ).state == NetworkInfo.State.CONNECTED
            ) {
                var android_id:String = Settings.Secure.getString(this.contentResolver,
                    Settings.Secure.ANDROID_ID);
                var cursor:Cursor = database.rawQuery("SELECT * FROM counter",null)
                if(cursor.count!=0){
                    cursor.moveToLast()
                    var count = cursor.getInt(0)
                    count = count+1
                    Toast.makeText(this,"Successfully Uploaded",Toast.LENGTH_SHORT).show()
                    database.execSQL("INSERT INTO counter(count) VALUES ("+ count +")")
                    var databaseReference:DatabaseReference = FirebaseDatabase.getInstance().reference
                    var f:NotesInfo = NotesInfo(intent.getStringExtra("name"),intent.getStringExtra("desc"),intent.getStringExtra("category"))
                    databaseReference.child(android_id).child(count.toString()).setValue(f)
                }else{
                    database.execSQL("INSERT INTO counter(count) VALUES ("+ 1 +")")
                    Toast.makeText(this,"Successfully Uploaded",Toast.LENGTH_SHORT).show()
                    var databaseReference:DatabaseReference = FirebaseDatabase.getInstance().reference
                    var f:NotesInfo = NotesInfo(intent.getStringExtra("name"),intent.getStringExtra("desc"),intent.getStringExtra("category"))
                    databaseReference.child(android_id).child(0.toString()).setValue(f)
                }
            } else{
                Toast.makeText(this,"Not Connected to Internet",Toast.LENGTH_SHORT).show()
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
