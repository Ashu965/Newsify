package com.example.newsapi

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class NewsDetailActivity : AppCompatActivity(),MyBroadcastListener {

    lateinit var myWebView: WebView
    lateinit var btn: Button
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var localBroadcastManager : LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_article)
        myWebView = findViewById<WebView>(R.id.articleWebView)
        val url = intent.getStringExtra("URL")!!

        broadcastReceiver = MyBroadcastReceiver(this)
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        myWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }

     /*   btn = findViewById<Button>(R.id.indicator)
        btn.setTextColor(Color.RED)
        btn.setOnClickListener {
            btn.setTextColor(Color.RED)
        }}*/
       /* setContent {
            MaterialTheme {
                display()
            }
        }

    }
    @Preview
    @Composable
    fun display(){
        displayThisUrl(intent.getStringExtra("URL"))
    }
    @Composable
    fun displayThisUrl(url : String?) {
         WebView(Application.context).loadUrl(url);
    }*/



    override fun updateDetailActivity() {
        Toast.makeText(this, "working", Toast.LENGTH_LONG).show()
        if(::btn.isInitialized)
            btn.setTextColor(Color.GREEN)
    }
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction("ashu")
        filter.addCategory(Intent.CATEGORY_APP_FILES)
        localBroadcastManager.registerReceiver(broadcastReceiver,filter)
    }

    override fun onStop() {
        super.onStop()
        localBroadcastManager.unregisterReceiver(broadcastReceiver)
    }
    /*val MyBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "working", Toast.LENGTH_LONG).show()
                if(::btn.isInitialized)
                btn.setTextColor(Color.GREEN)
            }
        }*/
    }
