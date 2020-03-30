package com.memo.project.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.animation.AccelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.transition.addListener
import androidx.databinding.DataBindingUtil
import com.memo.project.R
import com.memo.project.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        val root = binding.root as ConstraintLayout

        root.post{
            TransitionManager.beginDelayedTransition(root, AutoTransition().apply {
                duration = 2500L
                interpolator = AccelerateInterpolator()
                addListener(onEnd = {
                    startActivity(
                        Intent(applicationContext, MainActivity::class.java)
                            .apply {
                                flags = flags or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            }
                    )
                    finish()
                })
            })

            ConstraintSet().apply {
                clone(applicationContext, R.layout.activity_splash_end)
            }.applyTo(root)
        }
    }
}
