package biz.riverone.shohinge

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import biz.riverone.shohinge.models.SD
import biz.riverone.shohinge.models.ShoshingeData
import biz.riverone.shohinge.views.ContentsViewControl
import biz.riverone.shohinge.views.MemoActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.lang.ref.WeakReference

/**
 * 練習！正信偈
 * Copyright (C) 2017 J.Kawahara
 * 2017.12.5 J.Kawahara 新規作成
 * 2018.2.16 J.Kawahara ver.1.2 丸形アイコンを更新
 */

class MainActivity : AppCompatActivity() {

    companion  object {
        private const val DEBUG_MODE = false
    }

    private var mediaPlayer: MediaPlayer? = null
    private var timeChecker: Handler? = null
    private var taskFadeout: MediaPlayerFadeOut? = null

    private val textViewTitle by lazy { findViewById<TextView>(R.id.textViewTitle) }

    // デバグ用情報表示コントロール
    private val textViewCurrentPosition by lazy { findViewById<TextView>(R.id.textViewCurrentPosition) }

    private val buttonPlay by lazy { findViewById<Button>(R.id.buttonPlay) }
    private val buttonStop by lazy { findViewById<Button>(R.id.buttonStop) }

    private var fadeInAnimation: Animation? = null
    private var fadeOutAnimation: Animation? = null

    private var textSizeLarge: Float = 0.0f
    private var textSizeMiddle: Float = 0.0f

    private var currentDataIndex: Int = -999
    private var viewerList = ArrayList<ContentsViewControl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 画面をポートレートに固定する
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // 音量調整を端末のボタンで行う
        volumeControlStream = AudioManager.STREAM_MUSIC

        textSizeLarge = resources.getDimension(R.dimen.textSizeCurrent)
        textSizeMiddle = resources.getDimension(R.dimen.textSizeCurrentMiddle)

        initializeControls()

        refresh(0)

        // AdMob
        MobileAds.initialize(applicationContext, "ca-app-pub-1882812461462801~1466334037")
        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun initializeControls() {

        viewerList.add(ContentsViewControl(this, findViewById(R.id.textViewFuri0), findViewById(R.id.textViewText0)))
        viewerList.add(ContentsViewControl(this, findViewById(R.id.textViewFuri1), findViewById(R.id.textViewText1)))
        viewerList.add(ContentsViewControl(this, findViewById(R.id.textViewFuri2), findViewById(R.id.textViewText2)))
        viewerList.add(ContentsViewControl(this, findViewById(R.id.textViewFuri3), findViewById(R.id.textViewText3)))

        buttonPlay.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                // 再生中
                audioPause()
            } else {
                audioPlay()
            }
        }

        buttonStop.setOnClickListener {
            audioStop()
        }

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        if (DEBUG_MODE) {
            textViewCurrentPosition.setOnClickListener {
                mediaPlayer?.seekTo(588 * 1000)
            }
        } else {
            textViewCurrentPosition.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        audioStop()
    }

    private fun audioSetup() {
        mediaPlayer = MediaPlayer.create(this, R.raw.shoshinge)
        mediaPlayer?.setOnCompletionListener {
            audioStop()
        }
    }

    private fun audioPlay() {
        if (mediaPlayer == null) {
            audioSetup()
        }

        // 再生中は画面がスリープ状態にならないように設定する
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mediaPlayer?.start()

        if (timeChecker == null) {
            timeChecker = Handler()
        } else {
            timeChecker?.removeCallbacksAndMessages(null)
        }
        timeChecker?.postDelayed(playTimeChecker, 1000)

        buttonPlay.setText(R.string.caption_button_pause)
        buttonPlay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_pause, 0, 0)
    }

    private val playTimeChecker = object: Runnable{
        override fun run() {
            if (mediaPlayer?.isPlaying == true) {
                refresh((mediaPlayer?.currentPosition?: 0) / 1000)
                timeChecker?.postDelayed(this, 1000)
            }
        }
    }

    private fun audioPause() {
        mediaPlayer?.pause()

        buttonPlay.setText(R.string.caption_button_play)
        buttonPlay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_play, 0, 0)

        // 画面のスリープ状態を許可する
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun audioStop() {
        if (taskFadeout == null) {
            taskFadeout = MediaPlayerFadeOut(this).execute() as MediaPlayerFadeOut
        }
    }

    private fun releasePlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()

        mediaPlayer = null
        taskFadeout = null

        buttonPlay.setText(R.string.caption_button_play)
        buttonPlay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_play, 0, 0)

        refresh(0)

        // 画面のスリープ状態を許可する
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun refreshTitle(sec: Int) {
        val currentTitle = ShoshingeData.timeToTitle(sec)
        if (currentTitle != textViewTitle.text) {
            textViewTitle.text = currentTitle
        }
    }

    private fun refresh(sec: Int) {

        // デバグ用情報表示
        if (DEBUG_MODE) {
            textViewCurrentPosition.text = sec.toString()
        }

        refreshTitle(sec)

        val currentIndex = ShoshingeData.timeToIndex(sec)
        if (currentIndex != currentDataIndex) {
            currentDataIndex = currentIndex

            if (currentDataIndex == -1) {
                setStartView()
                return
            } else if (currentDataIndex < 0) {
                // 終了
                return
            }

            if (currentDataIndex >= ShoshingeData.contentsList.size - 2) {
                for (v in viewerList) {
                    v.setCurrent(false)
                }
                return
            }

            // 現在読み上げ中のテキストの色を変更する
            val currentDataPosition = currentDataIndex % 4
            for (i in viewerList.indices) {
                viewerList[i].setCurrent(i == currentDataPosition)
            }

            if (currentDataIndex >= ShoshingeData.contentsList.size - 3) {
                // 最後はテキストの更新を行わない
                return
            }

            // テキストを更新する
            if (currentDataPosition == 0) {
                // 最上段のテキストを読み上げ中
                if (currentDataIndex > 0) {
                    // 最下段のテキストを更新する
                    setView(3, currentDataIndex + 3)
                }
            } else if (currentDataPosition == 3) {
                // 最下段のテキストを読み上げ中
                // 1～3段目のテキストを更新する
                for (i in 0..2) {
                    setView(i, currentDataIndex + i + 1)
                }
            }
        }
    }

    private fun setView(position: Int, dataIndex: Int) {
        val sd = if (dataIndex < ShoshingeData.contentsList.size) {
            ShoshingeData.contentsList[dataIndex]
        } else {
            SD(0, "", "")
        }
        viewerList[position].changeText(sd, (sd.time in 491..619))
    }

    private fun setStartView() {
        viewerList[0].changeText(ShoshingeData.contentsList[0])
        viewerList[1].changeText(ShoshingeData.contentsList[1])
        viewerList[2].changeText(ShoshingeData.contentsList[2])
        viewerList[3].changeText(ShoshingeData.contentsList[3])
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_memo) {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private class MediaPlayerFadeOut(mainActivity: MainActivity) : AsyncTask<String, Int, String>() {

        private val mainActivity: WeakReference<MainActivity> = WeakReference(mainActivity)
        private val loopTime = 30
        private val waitTime = (200 / loopTime).toLong()

        override fun doInBackground(vararg p0: String?): String {
            val mediaPlayer = mainActivity.get()?.mediaPlayer ?: return "dummy"
            var level = 1.0f
            val per = 0.9f

            var i = 1
            while (i < loopTime) {
                i++
                level *= per
                mediaPlayer.setVolume(level, level)

                try {
                    Thread.sleep(waitTime)
                }
                catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            mediaPlayer.setVolume(0.0f, 0.0f)
            return "dummy"
        }

        override fun onPostExecute(result: String?) {
            mainActivity.get()?.releasePlayer()
        }

    }
}
