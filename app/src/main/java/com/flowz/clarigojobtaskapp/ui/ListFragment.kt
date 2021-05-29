package com.flowz.clarigojobtaskapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.byteworksjobtask.util.*
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.adapter.CeAdapter
import com.flowz.clarigojobtaskapp.databinding.FragmentListBinding
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeViewModel
import com.flowz.clarigojobtaskapp.ui.map.MapsActivity
import com.flowz.clarigojobtaskapp.util.Editable.Companion.EMPLOYEE
import com.flowz.clarigojobtaskapp.util.Editable.Companion.EMPLOYEE1
import com.flowz.clarigojobtaskapp.util.PassData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_EDIT1 = "edit"

@AndroidEntryPoint
class ListFragment(private var callback: RowClickListenerFromFragment): Fragment(R.layout.fragment_list), CeAdapter.RowClickListener{

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var ceAdapter: CeAdapter
    private lateinit var passData: PassData
    private val viewModel: ClarigoEmployeeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        loadRecylcerView()

    }

   fun loadRecylcerView(){

        ceAdapter = CeAdapter(this@ListFragment, callback)

        viewModel.ceEmployeesFromDb.observe(viewLifecycleOwner, Observer {
            ceAdapter.submitList(it)
        })

       binding.apply {

           ceRecyclerview.apply {
               layoutManager = LinearLayoutManager(this.context)
               adapter = ceAdapter

           }

           fab.setOnClickListener {
               val intent = Intent(requireActivity(), MapsActivity::class.java)
               startActivity(intent)
           }
       }

    }

    override fun onEditClickListener(clarigoEmployee: ClarigoEmployee, callback: RowClickListenerFromFragment) {

//        EMPLOYEE1?.value = clarigoEmployee
        EMPLOYEE = clarigoEmployee
//        passData = activity as PassData
//
//        passData.passData(clarigoEmployee)
        callback.onEditClick(clarigoEmployee)
    }


    override fun onDeleteClickListener(clarigoEmployee: ClarigoEmployee) {

        AlertDialog.Builder(this.requireContext()).setTitle(getString(R.string.delete_employee_title))
            .setMessage(getString(R.string.sure_to_delete))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                viewModel.deleteClarigoEmployee(clarigoEmployee)

                Snackbar.make(binding.ceRecyclerview, " ${clarigoEmployee.name} Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){ viewModel.insertClarigoEmployee(clarigoEmployee)}.show()

            }
            .setNegativeButton(getString(R.string.cancel_delete)){ _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface RowClickListenerFromFragment {
        fun onEditClick(clarigoEmployee: ClarigoEmployee)
    }


}

