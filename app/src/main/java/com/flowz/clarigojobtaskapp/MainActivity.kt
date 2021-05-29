package com.flowz.clarigojobtaskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.clarigojobtaskapp.adapter.CeAdapter
import com.flowz.clarigojobtaskapp.adapter.ViewPagerAdapter
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.ui.AddFragment
import com.flowz.clarigojobtaskapp.ui.ListFragment
import com.flowz.clarigojobtaskapp.util.Editable.Companion.EMPLOYEE
import com.flowz.clarigojobtaskapp.util.Editable.Companion.EMPLOYEE1
import com.flowz.clarigojobtaskapp.util.PassData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListFragment.RowClickListenerFromFragment , PassData{

//    private lateinit var navController: NavController
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navController = findNavController( R.id.nav_host_fragment)
//
//        setupActionBarWithNavController(navController)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.ce_viewPager)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, this)
        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Clarigo-Task"

            when(position){
                0->{
                    tab.text = "List"
                }
                1->{
                    tab.text = "Add"
                }
            }

        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onEditClick(clarigoEmployee: ClarigoEmployee) {

        viewPager.currentItem = 1
        Toast.makeText(this, "Editing ${clarigoEmployee.name} From Activity", Toast.LENGTH_LONG).show()
    }

    override fun passData(clarigoEmployee: ClarigoEmployee) {

//        val bundle = Bundle()
//        bundle.putString("input", "HELLO TESTER ONE")
//        bundle.putParcelable("input2", clarigoEmployee)
//        val addFragment= AddFragment()
//        addFragment.arguments = bundle
    }


//    override fun onSupportNavigateUp(): Boolean {
//
//        return navController.navigateUp()|| super.onSupportNavigateUp()
//    }
}
