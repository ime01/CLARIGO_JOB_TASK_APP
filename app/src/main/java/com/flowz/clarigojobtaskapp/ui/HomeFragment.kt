package com.flowz.clarigojobtaskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        tabLayout = view.findViewById(R.id.tab_layout)
//        viewPager = view.findViewById(R.id.ce_viewPager)
//        ceCollectionAdapter = CollectionAdapter(this)
//        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, )
//        viewPager.adapter = adapter
//
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when(position){
//                0->{
//                    tab.text = "List"
//                }
//                1->{
//                    tab.text = "Add"
//                }
//            }
//
//        }.attach()
//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Toast.makeText(requireContext(), "ReSelected ${tab?.text}", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Toast.makeText(requireContext(), "UnSelected ${tab?.text}", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Toast.makeText(requireContext(), "Selected ${tab?.text}", Toast.LENGTH_SHORT).show()
//            }
//        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}