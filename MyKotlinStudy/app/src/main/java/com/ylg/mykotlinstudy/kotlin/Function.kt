package com.ylg.mykotlinstudy.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.ylg.mykotlinstudy.R

class Function : AppCompatActivity() {
    var title1 = "Kotlin函数"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview)
        title = title1;

        var webView = findViewById<WebView>(R.id.webview)
        webView.loadUrl("file:///android_asset/function.html")
    }
}