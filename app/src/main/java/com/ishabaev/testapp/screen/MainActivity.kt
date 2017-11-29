package com.ishabaev.testapp.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ishabaev.testapp.R
import com.ishabaev.testapp.screen.currency.CurrencyListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CurrencyListFragment())
                    .commit()
        }
    }
}
