package biz.riverone.shohinge.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import biz.riverone.shohinge.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * MemoActivity.kt: 正信念仏偈 意訳 表示画面
 * Copyright (C) 2017 J.Kawahara
 * 2017.12.16 J.Kawahara
 */
class MemoActivity : AppCompatActivity() {

    companion object {
        private const val CURRENT_PAGE_NUMBER_KEY = "currentPageNumber"
    }

    private var currentPageNumber: Int = 0
    private val webViewMemo by lazy { findViewById<WebView>(R.id.webViewMemo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        // AdMob
        MobileAds.initialize(applicationContext, "ca-app-pub-1882812461462801~1466334037")
        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    private fun openOuterUri(uri: Uri?) {
        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun checkUrl(url: String) : Boolean {
        val regex = Regex("memo_[0-9][0-9].html")
        val result = regex.find(url) ?: return false

        val numRegex = Regex("[0-9][0-9]")
        val numResult = numRegex.find(result.value) ?: return false

        currentPageNumber = numResult.value.toInt()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(CURRENT_PAGE_NUMBER_KEY, currentPageNumber)
    }

    override fun onResume() {
        super.onResume()

        loadHTML(currentPageNumber)
    }

    override fun onBackPressed() {
        // デバイスのバックボタンタップ時の処理
        checkUrl(webViewMemo.url)
        when {
            (currentPageNumber != 0 && webViewMemo.canGoBack()) -> {
                webViewMemo.goBack()
                checkUrl(webViewMemo.url)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun loadHTML(pageNumber: Int) {
        currentPageNumber = pageNumber

        var url = "file:///android_asset/memo_"
        if (pageNumber < 10) {
            url += "0"
        }
        url += pageNumber.toString()
        url += ".html"

        val webViewMemo = findViewById<WebView>(R.id.webViewMemo)
        webViewMemo.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.memo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_close) {
            // 閉じる
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
