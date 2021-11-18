package com.example.mimusica_ds3

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.mimusica_ds3.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.PermissionToken
import android.widget.ArrayAdapter
import android.widget.ListView
import com.karumi.dexter.listener.PermissionRequest
import java.io.File
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    //declarar variables
    var listView: ListView? = null
    lateinit var items: Array<String?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listamusica)
        runtimePermission()
    }
    /*ejecutar permiso*/
    fun runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    displaySongs()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
    /*definir formatos a leer*/
    fun findSongs(file: File): ArrayList<File> {
        val arrayList = ArrayList<File>()
        val files = file.listFiles()!!
        for (singlefile in files) {
            if (singlefile.isDirectory && !singlefile.isHidden) {
                arrayList.addAll(findSongs(singlefile))
            } else {
                if (singlefile.name.endsWith(".mp3") || singlefile.name.endsWith(".wav")) {
                    arrayList.add(singlefile)
                }
            }
        }
        return arrayList
    }
    /*ejecutar musica y cambiar de pantalla*/
    fun displaySongs() {
        val mySong = findSongs(File("/storage/DF32-1CF7/My Music"))
        items = arrayOfNulls(mySong.size)
        for (i in mySong.indices) {
            items[i] = mySong[i].name.replace(".mp3", "").replace(".wav", "")
        }
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView!!.adapter = myAdapter
        /*----------------------*/listView!!.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val songName = listView!!.getItemAtPosition(i) as String
                startActivity(
                    Intent(applicationContext, Actividad2::class.java)
                        .putExtra("song", mySong)
                        .putExtra("songname", songName)
                        .putExtra("pos", i)
                )
            }
    }
}