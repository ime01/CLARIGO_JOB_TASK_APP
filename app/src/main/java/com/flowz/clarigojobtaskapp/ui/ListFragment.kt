package com.flowz.clarigojobtaskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.adapter.CeAdapter
import com.flowz.clarigojobtaskapp.databinding.FragmentListBinding
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), CeAdapter.RowClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var ceAdapter: CeAdapter
    private val viewModel: ClarigoEmployeeViewModel by viewModels()
    lateinit var navController : NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        loadRecylcerView()

    }
    private fun loadRecylcerView(){

        viewModel.insertClarigoEmployee(  ClarigoEmployee(
            "Prachi",
            "Prachi@gmail.com",
            "12/04/1990",
            null
        ))
        viewModel.insertClarigoEmployee(  ClarigoEmployee(
            "John",
            "John@gmail.com",
            "19/04/1990",
            null
        ))

        ceAdapter = CeAdapter(this@ListFragment)

        viewModel.ceEmployeesFromDb.observe(viewLifecycleOwner, Observer {
            ceAdapter.submitList(it)
        })

        binding.ceRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ceAdapter

        }
        navController = Navigation.findNavController(requireView())

        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    override fun onEditClickListener(clarigoEmployee: ClarigoEmployee) {



        Toast.makeText(requireContext(), "EDIT CLICKED", Toast.LENGTH_LONG).show()
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




}