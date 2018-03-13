package com.silly.sillypermission.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.silly.sillypermission.SillyPermission
import com.silly.sillypermission.SillyPermission.PERMISSION_AUDIO
import com.silly.sillypermission.SillyPermission.PERMISSION_CALENDAR
import com.silly.sillypermission.SillyPermission.PERMISSION_CAMERA
import com.silly.sillypermission.SillyPermission.PERMISSION_CONTACTS
import com.silly.sillypermission.SillyPermission.PERMISSION_LOCATION
import com.silly.sillypermission.SillyPermission.PERMISSION_PHONE
import com.silly.sillypermission.SillyPermission.PERMISSION_SENSORS
import com.silly.sillypermission.SillyPermission.PERMISSION_SMS
import com.silly.sillypermission.SillyPermission.PERMISSION_STORAGE
import com.silly.sillypermission.SillyPermissionCall

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.calendar).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "日历权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "日历权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_CALENDAR)
        }
        findViewById<Button>(R.id.camera).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "相机权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "相机权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_CAMERA)
        }
        findViewById<Button>(R.id.contacts).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "联系人权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "联系人权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_CONTACTS)
        }
        findViewById<Button>(R.id.location).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "定位权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "定位权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_LOCATION)
        }
        findViewById<Button>(R.id.audio).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "麦克风权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "麦克风权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_AUDIO)
        }
        findViewById<Button>(R.id.phone).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "手机状态权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "手机状态权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_PHONE)
        }
        findViewById<Button>(R.id.sensors).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "传感器权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "传感器权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_SENSORS)
        }
        findViewById<Button>(R.id.sms).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "短信权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "短信权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_SMS)
        }
        findViewById<Button>(R.id.settings).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "存储权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "存储权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_STORAGE)
        }
        findViewById<Button>(R.id.all).setOnClickListener {
            SillyPermission.requestPermission(this, SillyPermissionCall {
                if (it) Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show()
            }, PERMISSION_CALENDAR, PERMISSION_CAMERA, PERMISSION_CONTACTS, PERMISSION_LOCATION, PERMISSION_AUDIO,
                    PERMISSION_PHONE, PERMISSION_SENSORS, PERMISSION_SMS, PERMISSION_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SillyPermission.onActivityResultPermission(this, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        SillyPermission.onRequestPermissionsResultPermission(this, requestCode, permissions, grantResults)
    }
}
