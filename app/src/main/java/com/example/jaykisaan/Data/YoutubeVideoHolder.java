package com.example.jaykisaan.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaykisaan.Model.VideoLinksData;
import com.example.jaykisaan.R;
import com.example.jaykisaan.activity.VideoFullScreen;

import java.util.ArrayList;

public class YoutubeVideoHolder extends RecyclerView.Adapter<YoutubeVideoHolder.ViewHolder> {

    ArrayList<VideoLinksData> videolink;
    Context context;

    public YoutubeVideoHolder(ArrayList<VideoLinksData> videolink, Context context) {
        this.videolink = videolink;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_setter,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoLinksData current = videolink.get(position);

        holder.webView.loadUrl(current.getLink());
        /*holder.bar.setVisibility(View.VISIBLE);*/
        holder.fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoFullScreen.class);
                i.putExtra("link",current.getLink());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        /*holder.bar.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public int getItemCount() {
        return videolink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        ImageView fullscreen;
        /*ProgressBar bar;*/

        @SuppressLint("SetJavaScriptEnabled")
        public ViewHolder(@NonNull View itemView, Context cn) {
            super(itemView);

            context = cn;
            webView = itemView.findViewById(R.id.webview);
            fullscreen = itemView.findViewById(R.id.fullscreenbutton);
            /*bar = itemView.findViewById(R.id.progress);*/
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }
}
