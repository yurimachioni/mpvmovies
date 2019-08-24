package com.machioni.mpvsample.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.machioni.mpvsample.R
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), FlowProvider {

    private val cicerone = Cicerone.create()
    private val navigator by lazy { SupportAppNavigator(this, supportFragmentManager, R.id.sceneContainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            cicerone.router.newRootScreen(SomethingListScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun getRouter(): Router {
        return cicerone.router
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.sceneContainer) as BackButtonListener
        if (!currentFragment.onBackPressed())
            finish()
    }
}
