package biz.riverone.shohinge.views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import biz.riverone.shohinge.R
import biz.riverone.shohinge.models.SD

/**
 * ContentsVIewControl.kt: テキスト表示
 * Copyright (C) 2017 J.Kawahara
 * Created by kawahara on 2017/12/06.
 */
class ContentsViewControl(
        private val context: Context,
        private val textViewFuri: TextView,
        private val textViewText: TextView) {

    private var currentTextSizeIsLarge: Boolean = false
    private val textSizeLarge = context.resources.getDimension(R.dimen.textSizeCurrent)
    private val textSizeMiddle = context.resources.getDimension(R.dimen.textSizeCurrentMiddle)

    private val fadeInAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
    private val fadeOutAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)

    init {
        setTextSizeLarge()
    }

    private fun cutIn(furi: String, text: String) {
        textViewFuri.text = furi
        textViewText.text = text
    }

    private fun fadeIn() {
        textViewFuri.startAnimation(fadeInAnimation)
        textViewText.startAnimation(fadeInAnimation)
    }

    private fun fadeOut() {
        textViewFuri.startAnimation(fadeOutAnimation)
        textViewText.startAnimation(fadeOutAnimation)
    }

    fun setCurrent(onoff: Boolean) {
        val color = if (onoff) {
            ContextCompat.getColor(context, R.color.textColorGold)
        } else {
            ContextCompat.getColor(context, R.color.textColorSilver)
        }
        //textViewFuri.setTextColor(color)
        textViewText.setTextColor(color)
    }

    private fun changeText(furi: String, text: String, textSizeMiddle: Boolean) {
        // フェードアウト
        fadeOut()

        Handler(Looper.getMainLooper()).postDelayed({
            // カレントを解除する
            setCurrent(false)

            // 文字サイズを変更する
            if (textSizeMiddle && currentTextSizeIsLarge) {
                setTextSizeMiddle()
            } else if (!textSizeMiddle && !currentTextSizeIsLarge) {
                setTextSizeLarge()
            }

            // テキストを更新してフェードイン
            cutIn(furi, text)
            fadeIn()
        }, 500)
    }

    fun changeText(sd: SD, textSizeMiddle: Boolean = false) {
        changeText(sd.furi, sd.text, textSizeMiddle)
    }

    private fun setTextSizeLarge() {
        currentTextSizeIsLarge = true
        textViewText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeLarge)
    }

    private fun setTextSizeMiddle() {
        currentTextSizeIsLarge = false
        textViewText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeMiddle)
    }
}