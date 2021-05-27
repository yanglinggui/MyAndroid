package com.ylg.mykotlinstudy.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.ylg.mykotlinstudy.R

class ClassAndObject : AppCompatActivity() {

    var title1 = "Kotlin类和对象"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview)
        title = title1
        findViewById<WebView>(R.id.webview).loadUrl("file:///android_asset/class_and_object.html")
    }
}