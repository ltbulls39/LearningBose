package com.hernandez.learningbose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bose.wearable.BoseWearable
import com.bose.wearable.Config

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        BoseWearable.configure(this, Config.Builder().build())


        val fragmentManager: FragmentManager = supportFragmentManager


        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = HomeFragment()
        fragmentTransaction.add(R.id.fragment_holder, fragment, "home_frag")
        fragmentTransaction.commit()

//        addFragment.setOnClickListener {
//            var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//            var fragment: HomeFragment = HomeFragment()
//            fragmentTransaction.add(R.id.fragment_holder, fragment, "My_FRAG")
//            fragmentTransaction.commit()
//        }

    }
}
