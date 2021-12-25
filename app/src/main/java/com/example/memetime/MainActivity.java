package com.example.memetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String url ="https://meme-api.herokuapp.com/gimme";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadmeme();
    }
    private void loadmeme()
    {
        ProgressBar p=findViewById(R.id.pb);
        p.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String url= null;
                        try {
                            url = response.getString("url");
                        } catch (JSONException e) {
                            Toast t=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG);

                        }
                        ImageView image = (ImageView) findViewById(R.id.imageView);
                        Glide.with(MainActivity.this).load(url).into(image);
                        p.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went Wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void onnext(View view) {
        loadmeme();
    }

    public void onshare(View view) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this amazing meme using MemeTime..."+url);
        Intent chooser=Intent.createChooser(intent,"Share this using...");
        try {
            startActivity(intent);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "INTENT FAILED", Toast.LENGTH_LONG).show();
        }
    }

    public void change(View view) {
        ImageButton ib=findViewById(R.id.like);
        ib.setImageResource(R.drawable.dislike);
    }
}