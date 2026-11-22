package com.zulalnb.tabnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        createViewPager()
        // tabLayout.setupWithViewPager(viewPager) // DEPRECATED
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            createTab(tab, position)
        }.attach()

        // tabLayout.addTab(tabLayout.newTab())
        // tabLayout.addTab(tabLayout.newTab())

    }

    fun createViewPager() {
        val adapter = ViewPageAdapter(this)
        adapter.addFragment(DocumentFragment())
        adapter.addFragment(ProfileFragment())
        viewPager.adapter = adapter
    }

    private fun createTab(tab: TabLayout.Tab, position: Int) {
        val customTab = LayoutInflater.from(this)
            .inflate(R.layout.custom_tab, null)

        val textView = customTab.findViewById<TextView>(R.id.textView)
        val imageView = customTab.findViewById<ImageView>(R.id.imageView)

        when (position) {
            0 -> {
                textView.text = "Belgeler"
                imageView.setImageResource(R.drawable.ic_document)
            }

            1 -> {
                textView.text = "Profil"
                imageView.setImageResource(R.drawable.ic_profile)
            }
        }

        tab.customView = customTab
    }

    internal class ViewPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

        private val fragmentList: MutableList<Fragment> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}