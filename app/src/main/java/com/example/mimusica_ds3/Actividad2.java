package com.example.mimusica_ds3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Actividad2 extends AppCompatActivity {
    Button btnplay,btnsig,btnatras;
    TextView txtmusica,txtinicio,txtalto;
    SeekBar seekprogreso;

    String name;
    public static final  String EXTRA_NAME="song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_musica);

        btnplay=findViewById(R.id.btnplay);
        btnsig=findViewById(R.id.btnesxt);
        btnatras=findViewById(R.id.btnback);
        txtinicio=findViewById(R.id.txtinicio);
        txtalto=findViewById(R.id.txtalto);
        txtmusica=findViewById(R.id.txtmusica);
        seekprogreso=findViewById(R.id.progreso);

        if (mediaPlayer !=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySong= (ArrayList)bundle.getParcelableArrayList("song");
        String songname= i.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txtmusica.setSelected(true);
        Uri uri = Uri.parse(mySong.get(position).toString());
        name=mySong.get(position).getName();
        txtmusica.setText(name);

        mediaPlayer=mediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    btnplay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else {
                    btnplay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                }
            }
        });
    }
}