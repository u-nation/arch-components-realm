package com.example.u_nation.arch_components_realm

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().inMemory().build())
    }
}