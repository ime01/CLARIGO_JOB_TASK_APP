package com.flowz.clarigojobtaskapp.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flowz.byteworksjobtask.util.*
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.adapter.CeAdapter
import com.flowz.clarigojobtaskapp.databinding.FragmentListBinding
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeViewModel
import com.flowz.clarigojobtaskapp.ui.map.MapsActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), CeAdapter.RowClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var ceAdapter: CeAdapter
    private val viewModel: ClarigoEmployeeViewModel by viewModels()
    private var imageUri : Uri? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        loadRecylcerView()

    }
    private fun loadRecylcerView(){

        ceAdapter = CeAdapter(this@ListFragment)

        viewModel.ceEmployeesFromDb.observe(viewLifecycleOwner, Observer {
            ceAdapter.submitList(it)
        })

        binding.ceRecyclerview.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ceAdapter

        }

        binding.fab.setOnClickListener {
            val intent = Intent(requireActivity(), MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onEditClickListener(clarigoEmployee: ClarigoEmployee) {

        updateCeEmployee(clarigoEmployee)
        showSnackbar(binding.ceRecyclerview, "${clarigoEmployee.name }  Updated")

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

    private fun updateCeEmployee(clarigoEmployee: ClarigoEmployee){

//        Glide.with(profileImage)
//            .load(clarigoEmployee?.profilePicture)
//            .circleCrop()
//            .placeholder(R.drawable.ic_baseline_person_24)
//            .error(R.drawable.ic_baseline_person_24)
//            .fallback(R.drawable.ic_baseline_person_24)
//            .into(profileImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }





}
