package com.bassem.weathernow

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class Splash : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

      Handler().postDelayed(Runnable {
         checkPermission()
      },1200)


}
    fun goToContainer(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
       if (requestCode==101){
           goToContainer()
           println("Granted")
       }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
       if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
           AppSettingsDialog.Builder(Activity()).build().show()
       } else {
           requestPermission()
       }
    }
    fun hasPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Weather now needs to access location",
            101,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun checkPermission() {
        if (!hasPermission()) {
            requestPermission()
            println("REQUEST")
        } else {
            goToContainer()

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
}