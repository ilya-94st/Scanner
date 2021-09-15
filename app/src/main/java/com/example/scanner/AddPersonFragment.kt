@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.scanner

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.scanner.BaceFragment.BaceFragment
import com.example.scanner.DataBases.User
import com.example.scanner.DataBases.UsersViewModel
import com.example.scanner.databinding.FragmentAddPersonBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.ByteArrayOutputStream

class AddPersonFragment : BaceFragment<FragmentAddPersonBinding>() {
    private val edit = "edit"
    private val save = "save"
    private val PHOTO_CAMERA = 1
    private val CALL = 2
    private var landPhoto: Bitmap? = null
    private val PHOTO_KEY = "keyPhoto"
    lateinit var mUserviewModel: UsersViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserviewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.switchButton.text = edit
                camera()
        callfun()
        mailfun()

        binding.switchButton.setOnClickListener {
            when (binding.switchButton.text) {
                edit -> showSave()
                save -> showEdit()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showEdit() {
        binding.switchButton.text = edit
        binding.editFullname.isFocusable = false
        binding.editPhone.isFocusable = false
        binding.editMail.isFocusable = false
        binding.emailButton.visibility = View.VISIBLE
        binding.callButton.visibility = View.VISIBLE
        binding.imageViewQcode.visibility = View.VISIBLE
        val fallinAnimation = AnimationUtils.loadAnimation(context, R.anim.faling)
        binding.imageViewQcode.startAnimation(fallinAnimation)
        generateQrcode()
        encodePhoto(landPhoto)?.let {
            User(0, binding.editFullname.text.toString(), binding.editPhone.text.toString(), binding.editMail.text.toString(),
                it
            )
        }?.let { mUserviewModel.addUser(it) }
    }

    fun showSave() {
        binding.switchButton.text = save
        binding.editFullname.isFocusableInTouchMode = true
        binding.editPhone.isFocusableInTouchMode = true
        binding.editMail.isFocusableInTouchMode = true
        binding.emailButton.visibility = View.GONE
        binding.callButton.visibility = View.GONE
        binding.imageViewQcode.visibility = View.INVISIBLE

    }

    fun camera() {
        binding.imageButton.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.CAMERA)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                val takePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(takePhoto,PHOTO_CAMERA)
                }catch (e:Exception){
                    Toast.makeText(context, "No camera", Toast.LENGTH_SHORT).show()
                }
            } else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), PHOTO_CAMERA)
            }
        }
    }

    fun callfun() {
        binding.callButton.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.CALL_PHONE)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + binding.editPhone.text.toString()))
                if (binding.editPhone.text.toString().isEmpty()) {
                    Toast.makeText(context, "the call field isEmpty", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(intent)
                }
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL
                )
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateQrcode() {
        try {
            val write = MultiFormatWriter()
            val matrix = write.encode("${binding.editFullname.text.toString()}:${binding.editPhone.text.toString()}:${binding.editMail.text.toString()}",
                BarcodeFormat.QR_CODE,350,350)
            val ecogner = BarcodeEncoder()
            val bitamp = ecogner.createBitmap(matrix)
            binding.imageViewQcode.setImageBitmap(bitamp)
        }catch (e: WriterException){
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodePhoto(photo: Bitmap?): String? {
        val bos = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val byteArray: ByteArray = bos.toByteArray()
        return java.util.Base64.getEncoder().encodeToString(byteArray)
    }

    fun mailfun(){
        binding.emailButton.setOnClickListener {
            if(binding.editMail.text.toString().isEmpty()){
                Toast.makeText(context, "field Email isEmpty", Toast.LENGTH_SHORT).show()
            }else{
                val emailIntent = Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text")
                emailIntent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(binding.editMail.text.toString())
                )
                startActivity(
                    Intent.createChooser(
                        emailIntent,
                        "Отправка письма..."
                    )
                )
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PHOTO_CAMERA && resultCode == RESULT_OK){
            landPhoto = data?.extras?.get("data") as Bitmap
            binding.imageButton.setImageBitmap(landPhoto)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PHOTO_KEY,landPhoto)
    }

    override fun getFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddPersonBinding.inflate(inflater,container, false)
}

