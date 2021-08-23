package com.vimal.storyview.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.vimal.moments.Momentz;
import com.vimal.moments.MomentzCallback;
import com.vimal.moments.MomentzView;
import com.vimal.storyview.R;
import com.vimal.storyview.model.DataModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MomentzCallback {

    ConstraintLayout container;
    ArrayList<DataModel> resourcesnew = new ArrayList<>();
    ArrayList<MomentzView> arraymomentz = new ArrayList<>();
    ImageView imgswipe;
    int index = 0;
    Momentz momentzsel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);

        resourcesnew.add(new DataModel("Image", "Vimal", "Personal Ad", "https://lyricallyresource.b-cdn.net/res/FX201902141353/banner.jpg"));
        resourcesnew.add(new DataModel("Image", "Independence Day", "Celebration", "https://lyricallyresource.b-cdn.net/res/FX201905201691/banner.jpg"));
        resourcesnew.add(new DataModel("Video", "Lyrical.ly", "sponsor", "http://behindlogicinc.com/snacky/data/FX202102189219/sample.mp4"));
        resourcesnew.add(new DataModel("Image", "Rakshabandhan", "Festival", "https://lyricallyresource.b-cdn.net/res/FX201902141353/banner.jpg"));
        resourcesnew.add(new DataModel("Image", "Janmashtami", "Festival", "https://lyricallyresource.b-cdn.net/res/FX201905201691/sample.webp"));

        for (int i = 0; i < resourcesnew.size(); i++) {
            if (resourcesnew.get(i).getType().equals("Image")) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.image, null);
                arraymomentz.add(new MomentzView(view, 5));
            } else {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.video, null);
                arraymomentz.add(new MomentzView(view, 30));
            }
        }

        new Momentz(this, arraymomentz, container, this, R.drawable.green_lightgrey_drawable).start();

        imgswipe = findViewById(R.id.imgswipe);
        imgswipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (momentzsel != null) {
                    momentzsel.pause(true);
                    Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/amitshah"));
                    startActivity(browse);
                }

            }
        });

        Glide.with(this).load(R.raw.swipeupnew).into(imgswipe);


//        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
//        aniSlide.setRepeatMode(Animation.REVERSE);
//        aniSlide.setRepeatCount(Animation.INFINITE);
//        adclick.startAnimation(aniSlide);


    }

    @Override
    public void done() {
        Toast.makeText(MainActivity.this, "Finished!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNextCalled(View view, Momentz momentz, int index) {

        this.index = index;
        momentzsel = momentz;

        if (resourcesnew.get(index).getType().equals("Image")) {

            momentz.pause(true);

            ImageView image = view.findViewById(R.id.image);
            TextView title = view.findViewById(R.id.title);
            TextView adcontent = view.findViewById(R.id.adcontent);


            title.setText(resourcesnew.get(index).getTitle());
            adcontent.setText(resourcesnew.get(index).getContent());


            Glide.with(this).load(resourcesnew.get(index).getImgUrl()).addListener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    momentzsel.resume();
                    return false;
                }

            }).placeholder(R.mipmap.ic_launcher).into(image);


        } else {
            momentz.pause(true);

            VideoView videoView = view.findViewById(R.id.video);
            TextView title = view.findViewById(R.id.title);
            TextView adcontent = view.findViewById(R.id.adcontent);

            title.setText(resourcesnew.get(index).getTitle());
            adcontent.setText(resourcesnew.get(index).getContent());

            Uri uri = Uri.parse(resourcesnew.get(index).getImgUrl());
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
            videoView.setOnInfoListener((MediaPlayer.OnInfoListener) (new MediaPlayer.OnInfoListener() {
                public boolean onInfo(@Nullable MediaPlayer mp, int what, int extra) {
                    if (what == 3) {
                        momentzsel.editDurationAndResume(index, videoView.getDuration() / 1000);
                        return true;
                    } else {
                        return false;
                    }
                }
            }));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (momentzsel != null) {
            momentzsel.resume();
        }
    }
}