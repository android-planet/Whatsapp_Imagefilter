package com.example.whatsapp_imagefilter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.droidninja.imageeditengine.ImageEditor
import com.example.whatsapp_imagefilter.filepicker.FilePickerBuilder
import com.example.whatsapp_imagefilter.filepicker.FilePickerConst

import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

//TODO
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rxPermissions = RxPermissions(this)
        select_image_btn.setOnClickListener {

            rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        FilePickerBuilder.getInstance().setMaxCount(1)
                            .setActivityTheme(R.style.LibAppTheme)
                            .pickPhoto(this)
                    } else {
                        // Oups permission denied
                        Toast.makeText(this, "Not given permission", Toast.LENGTH_SHORT).show()
                    }
                })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerConst.REQUEST_CODE_PHOTO ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val photoPaths = ArrayList<String>()
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA)!!);

                    if (photoPaths.size > 0) {
                        ImageEditor.Builder(this, photoPaths[0])
                            .setStickerAssets("stickers")
                            .open()
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                    }
                }
            ImageEditor.RC_IMAGE_EDITOR ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imagePath: String = data.getStringExtra(ImageEditor.EXTRA_EDITED_PATH)!!
                    edited_image.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                }
        }
    }
}