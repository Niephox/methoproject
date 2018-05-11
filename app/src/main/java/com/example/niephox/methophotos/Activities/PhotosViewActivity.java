package com.example.niephox.methophotos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.niephox.methophotos.Controllers.PhotosGridViewAdapter;
import com.example.niephox.methophotos.Entities.Image;
import com.example.niephox.methophotos.R;

import java.util.ArrayList;

/**
 * Created by IgorSpiridonov
 */


public class PhotosViewActivity extends AppCompatActivity {

    //Layout Items:
    GridView gvImages;

    //Adapters:
    PhotosGridViewAdapter albumsAdapter;

    //Array Lists:
    ArrayList<Image> alImages = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_layout);

        gvImages = findViewById(R.id.gvImages);

        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//
//        if(bundle != null){
//            alImages=(ArrayList<Image>) bundle.getSerializable("alImages");
//        }
        alImages=intent.getParcelableArrayListExtra("alImages");

        albumsAdapter = new PhotosGridViewAdapter(this,alImages);
        gvImages.setAdapter(albumsAdapter);

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(PhotosViewActivity.this,MetadataActivity.class);
                intent.putExtra("image",alImages.get(position));
                startActivity(intent);
            }
        });
    }

}
