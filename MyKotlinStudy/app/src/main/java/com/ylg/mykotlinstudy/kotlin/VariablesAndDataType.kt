package com.ylg.mykotlinstudy.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.ylg.mykotlinstudy.R

class VariablesAndDataType : AppCompatActivity() {

    var title1 = "Kotlin变量和基本类型"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview)
        title = title1
        Log.i(this.javaClass.name, "onCreate");
        var webView = findViewById<WebView>(R.id.webview)
        webView.loadUrl("file:///android_asset/variables_and_data_type.html")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.javaClass.name, "onDestroy");
    }
}