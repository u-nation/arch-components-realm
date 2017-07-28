package com.example.u_nation.arch_components_realm.util

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery

fun <T : ViewDataBinding> Activity.dataBinding(@LayoutRes res: Int): T = DataBindingUtil.setContentView<T>(this, res)

inline fun <reified T : View> View.findById(id: Int): T = findViewById(id) as T

inline fun <reified T : ViewModel> ViewModelProvider.get(): T = this.get(T::class.java)

inline fun <reified T : Activity> Context.intent(): Intent = Intent(this, T::class.java)

inline fun <reified T : RealmModel> Realm.where(): RealmQuery<T> = where(T::class.java)
inline fun <reified T : RealmModel> Realm.getAutoIncrementKey(): Int {
    if (where(T::class.java).count() == 0L) return 1
    else return where(T::class.java).max("id").toInt() + 1
}

inline fun <reified T : ViewDataBinding> ViewGroup.inflateBinding(): T {
    return T::class.java
            .getDeclaredMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.javaPrimitiveType
            )
            .invoke(null, LayoutInflater.from(context), this, false) as T
}

inline fun <reified T : ViewModel> ViewModelProvider.NewInstanceFactory.create(viewModel: ViewModel): T = viewModel as T


inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

//@Suppress("UNCHECKED_CAST")
//fun <T : View> Activity.findView(id: Int): T = findViewById(id) as T

inline fun <reified T : View> Activity.findView(id: Int): T = findViewById(id) as T