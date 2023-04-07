package com.example.memechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

ImageView meme;
LottieAnimationView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = " https://meme-api.com/gimme";
        meme = findViewById(R.id.meme);
         anim = findViewById(R.id.progess);
        loadMeme();
        getSupportActionBar().hide();
    }
    public void loadMeme ()
    {
        anim.playAnimation();

        // Instantiate the RequestQueue.

        String url = " https://meme-api.com/gimme";


// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response)  {
                        String url = null;
                        try {
                            url = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Glide.with(MainActivity.this).load(url).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                anim.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                               anim.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(meme);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void nextMeme(View view) {
        loadMeme();
    }

    public void shareMeme(View view) {
        String url = " https://meme-api.com/gimme";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"HEY!! Have A Look At This Great Meme."+url);
        Intent chooser = Intent.createChooser(intent,"Share This Meme Using...");
        startActivity(chooser);
    }
}