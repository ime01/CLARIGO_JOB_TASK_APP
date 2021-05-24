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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.flowz.byteworksjobtask.util.*
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.databinding.FragmentAddBinding
import com.flowz.clarigojobtaskapp.databinding.FragmentListBinding
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee
import com.flowz.clarigojobtaskapp.roomdb.ClarigoEmployeeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClarigoEmployeeViewModel by viewModels()
    private var imageUri : Uri? = null
    private lateinit var dateOFbirthToDb : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddBinding.bind(view)

        binding.apply {

            ceSelectDob.setOnClickListener {
                selectDateOfBirth()
            }

            editPassport.setOnClickListener {
                val layoutInflater = LayoutInflater.from(requireContext())
                val alertView = layoutInflater.inflate(R.layout.camera_or_gallery_alert_dialog, null)

                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                alertDialog.setView(alertView)
                alertDialog.setTitle(getString(R.string.choose_image))
                alertDialog.setCancelable(false)
                val dialog = alertDialog.create()

                val openCameraImage = alertView.findViewById<ImageView>(R.id.rg_open_camera)
                val openGalleryImage = alertView.findViewById<ImageView>(R.id.open_gallery)


                dialog.show()

                openCameraImage.setOnClickListener {
                    checkPermssion()
                    openCamera()
                    dialog.dismiss()
                }

                openGalleryImage.setOnClickListener {
                    checkPermssion()
                    pickImageFromGallery()
                    dialog.dismiss()
                }

            }


            saveNewEmployee.setOnClickListener {

                if (TextUtils.isEmpty(ceNewName.text.toString())){
                    ceNewName.setError(getString(R.string.enter_name_error))
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(ceNewEmail.text.toString())){
                    ceNewEmail.setError(getString(R.string.enter_valid_email))
                    return@setOnClickListener
                }else if(ceSelectDob.text.toString()== getString(R.string.select_dob)){
                    ceSelectDob.setError(getString(R.string.chose_dob))
                    return@setOnClickListener
                }else if(imageUri == null){
                   showSnackbar(ceMapFab, "Ensure You have chosen a profile photo")
                    return@setOnClickListener
                }

                val newEmployee = ClarigoEmployee(ceNewName.takeWords(), ceNewEmail.takeWords(), dateOFbirthToDb, imageUri)
                viewModel.insertClarigoEmployee(newEmployee)
                showSnackbar(ceMapFab, getString(R.string.new_employee_saved))
//                viewModel.insertClarigoEmployee(ClarigoEmployee("Tony", "Tony@gmailcom", "13-07-1983"))
                val arrayOfViewsToClearAfterSavingEmployee = arrayOf(ceNewName,ceNewEmail)
                clearTexts(arrayOfViewsToClearAfterSavingEmployee)
                newPhoto.setImageResource(R.drawable.ic_baseline_person_24)
                ceSelectDob.setText(resources.getString(R.string.select_dob))
            }
        }
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
            binding.newPhoto.setImageURI(imageUri)
            showSnackbar(binding.ceMapFab, "Profile passport selected for upload....")
        }
        else if (requestCode == IMAGECAPUTRECODE && resultCode == Activity.RESULT_OK){

            val rgPhoto = data!!.extras?.get("data") as Bitmap
            binding.newPhoto.setImageBitmap(rgPhoto)

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



    fun selectDateOfBirth(){

        val layoutInflater = LayoutInflater.from(this.requireContext())
        val setTimeDialogView = layoutInflater.inflate(R.layout.date_picker_alert_dialog, null)
        val enteredDate = setTimeDialogView.findViewById<DatePicker>(R.id.date_p)


        val alertDialog = MaterialAlertDialogBuilder(requireContext())
        alertDialog.setView(setTimeDialogView)
        alertDialog.setTitle(getString(R.string.enter_birth_date))
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(getString(R.string.submit_birth_date), null)
        alertDialog.setNegativeButton(getString(R.string.cancel_dob), null)
        val dialog = alertDialog.create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                dateOFbirthToDb = " ${enteredDate.dayOfMonth} - ${enteredDate.month} - ${enteredDate.year} "
                binding.ceSelectDob.setText(dateOFbirthToDb)
                dialog.dismiss()
            }
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    companion object {
        val READIMAGE = 255
        val REQUESTCODE = 100
        val IMAGECAPUTRECODE = 400

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}