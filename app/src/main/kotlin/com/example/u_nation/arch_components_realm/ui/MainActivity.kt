package com.example.u_nation.arch_components_realm.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.u_nation.arch_components_realm.R
import com.example.u_nation.arch_components_realm.databinding.ActivityMainBinding
import com.example.u_nation.arch_components_realm.util.dataBinding
import com.example.u_nation.arch_components_realm.util.findView
import com.example.u_nation.arch_components_realm.util.startActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val binding by lazy { dataBinding<ActivityMainBinding>(R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val adapter = MyPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.text = "記事一覧"
        binding.tabLayout.getTabAt(1)?.text = "いいね"


        startActivity<ArticleDetailActivity>()


//Arrays.copyOf()
 val a:List<String> = ArrayList()

        val textView = findView<TextView>(R.id.text)
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = if (position == 0) ArticleFragment() else BookMarkFragment()

        override fun getCount(): Int = 2
    }
}
