package com.example.androidproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.androidproject.utils.applyAnimation
import com.example.androidproject.utils.getBooleanPreference
import com.example.androidproject.utils.isOnline
import com.example.androidproject.utils.startActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

private const val DELAY = 3000L
const val DATA_IMPORTED = "com.example.androidproject.data_imported"
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimations()
        redirect()

    }

    private fun startAnimations() {
        //tvSplash.applyAnimation(R.anim.blink)
        ivSplash.applyAnimation(R.anim.fromtopcorner)
        tvSplash.applyAnimation(R.anim.frombottomcorner)
    }

    private fun redirect() {
        if(getBooleanPreference(DATA_IMPORTED)){
                Handler(Looper.getMainLooper()).postDelayed({ startActivity<HostActivity>() }, DELAY)
        } else {
            if (isOnline()){
                Intent(this,MoviesService::class.java).apply {
                    MoviesService.enqueueWork(this@SplashScreenActivity, this)
                }
            }else {
                showToast("Please connect to the internet", Toast.LENGTH_LONG)
                finish()
            }
        }
    }

    fun Context.showToast(text : String, length : Int) = Toast.makeText(this,text,length).show()
}