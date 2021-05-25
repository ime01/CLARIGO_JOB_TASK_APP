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
//    lateinit var navController : NavController


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
//        navController = Navigation.findNavController(requireView())

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


    fun checkPermssion(){
        if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this.requireActivity()
                    ,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                   READIMAGE
                )
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
          READIMAGE ->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    showToast("Cannnot access your images",this.requireContext() )
                }
            }else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK && data!!.data != null ){

            imageUri = data.data
//            binding.newPhoto.setImageURI(imageUri)
//            showSnackbar(binding.ceMapFab, "Profile passport selected for upload....")
        }
        else if (requestCode == IMAGECAPUTRECODE && resultCode == Activity.RESULT_OK){

            val rgPhoto = data!!.extras?.get("data") as Bitmap
//            binding.newPhoto.setImageBitmap(rgPhoto)

            imageUri = getImageUri(requireContext(), rgPhoto)

        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,
            REQUESTCODE
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGECAPUTRECODE)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        val READIMAGE = 100
        val REQUESTCODE = 101
        val IMAGECAPUTRECODE = 402
    }




}
