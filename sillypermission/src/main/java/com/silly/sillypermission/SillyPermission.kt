package com.silly.sillypermission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import java.util.ArrayList

object SillyPermission {

    private const val TAG = "SillyPermission"

    /**
     * 日历
     * READ_CALENDAR
     * WRITE_CALENDAR
     */
    const val PERMISSION_CALENDAR = Manifest.permission.READ_CALENDAR
    /**
     * 相机
     * CAMERA
     */
    const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    /**
     * 联系人
     * READ_CONTACTS
     * WRITE_CONTACTS
     * GET_ACCOUNTS
     */
    const val PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS
    /**
     * 定位
     * ACCESS_FINE_LOCATION
     * ACCESS_COARSE_LOCATION
     */
    const val PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    /**
     * 麦克风
     * RECORD_AUDIO
     */
    const val PERMISSION_AUDIO = Manifest.permission.RECORD_AUDIO
    /**
     * 手机状态
     * READ_PHONE_STATE
     * CALL_PHONE
     * READ_CALL_LOG
     * WRITE_CALL_LOG
     * ADD_VOICEMAIL
     * USE_SIP
     * PROCESS_OUTGOING_CALLS
     */
    const val PERMISSION_PHONE = Manifest.permission.READ_PHONE_STATE
    /**
     * 传感器
     * BODY_SENSORS
     */
    const val PERMISSION_SENSORS = Manifest.permission.BODY_SENSORS
    /**
     * 短信
     * SEND_SMS
     * RECEIVE_SMS
     * READ_SMS
     * RECEIVE_WAP_PUSH
     * RECEIVE_MMS
     */
    const val PERMISSION_SMS = Manifest.permission.SEND_SMS
    /**
     * 存储
     * READ_EXTERNAL_STORAGE
     * WRITE_EXTERNAL_STORAGE
     */
    const val PERMISSION_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    const val ACTIVITY_RESULT = 830
    private const val REQUEST_PERMISSIONS_RESULT = 831
    private var mSillyPermissionClick: SillyPermissionClick? = null

    var mSillyPermissionCall: SillyPermissionCall? = null
    private var mPermissions: Array<out String>? = null

    /**
     * 权限申请,跳转到授权界面（多个个权限）
     */
    fun requestPermission(activity: Activity, SillyPermissionCall: SillyPermissionCall, vararg permissions: String) {
        try {
            mPermissions = permissions
            mSillyPermissionCall = SillyPermissionCall
            val checkPermisssions = lackPermissions(activity, *permissions)
            if (checkPermisssions != null && checkPermisssions.isEmpty()) mSillyPermissionCall?.call(true)
            else {
                if (checkPermisssions != null) requestPermissions(activity, REQUEST_PERMISSIONS_RESULT, *checkPermisssions.toTypedArray())
                else mSillyPermissionCall?.call(true)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

    /**
     * 权限整理
     */
    private fun lackPermissions(context: Context, vararg permissions: String): List<String>? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return null
        if (permissions.isEmpty()) return null
        var num = 0
        val permissionArray = ArrayList<String>()
        for (permission in permissions) {
            if (!lacksPermission(context, permission)) {
                permissionArray.add(permission)
                num++
            }
        }
        return permissionArray
    }

    /**
     * 权限请求 (在Activity中实现 onRequestPermissionsResult() 方法)
     *
     * @param activity    Activity
     * @param requestCode 亲求参数
     * @param permissions 权限 Manifest.permission.XXX
     */
    private fun requestPermissions(activity: Activity, requestCode: Int, vararg permissions: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        try {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    /**
     * 多个权限的判断
     *
     * @param permissions 权限
     * @return 是否有权限 true为有权限，false为没有权限
     */
    private fun lacksPermissions(context: Context, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        for (permission in permissions) {
            if (lacksPermission(context, permission)) return true
        }
        return false
    }

    /**
     * 单个权限的判断
     *
     * @param permission 权限
     * @return 是否有权限 true为有权限，false为没有权限
     */
    private fun lacksPermission(context: Context, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true
        else ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 跳转到应用设置界面
     *
     * @param activity Activity
     */
    fun startAppSettings(activity: Activity, requestCode: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivityForResult(intent, requestCode)
    }

    fun onActivityResultPermission(activity: Activity, requestCode: Int) {
        try {
            if (ACTIVITY_RESULT == requestCode) {
                val isPermission = lacksPermissions(activity, *mPermissions!!)
                if (isPermission) mSillyPermissionCall?.call(true)// 同意权限
                else mSillyPermissionCall?.call(false)// 拒绝权限
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

    fun onRequestPermissionsResultPermission(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        try {
            when (requestCode) {
                REQUEST_PERMISSIONS_RESULT -> {
                    if (grantResults.size == 1) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) mSillyPermissionCall?.call(true)// 同意权限
                        else showMissingPermissionDialog(activity, "${permissionMessage(permissions[0])}未授权，功能无法使用,请点击 设置-权限",
                                object : SillyPermissionClick {
                                    override fun onClickYES() {
                                        // 设置界面
                                        startAppSettings(activity, ACTIVITY_RESULT)
                                    }

                                    override fun onClickNO() {
                                        // 拒绝权限
                                        mSillyPermissionCall?.call(false)
                                    }
                                })
                    } else if (grantResults.size > 1) {
                        var num = 0
                        val list = ArrayList<String>()
                        for (i in grantResults.indices) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                list.add(permissions[i])
                                num++
                            }
                        }
                        if (num == 0) mSillyPermissionCall?.call(true)// 同意权限
                        else showMissingPermissionDialog(activity,
                                getPermissionTips(*list.toTypedArray()),
                                object : SillyPermissionClick {
                                    override fun onClickYES() {
                                        // 设置界面
                                        startAppSettings(activity, ACTIVITY_RESULT)
                                    }

                                    override fun onClickNO() {
                                        // 拒绝权限
                                        mSillyPermissionCall?.call(false)
                                    }
                                })
                    } else mSillyPermissionCall?.call(false)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

    /**
     * 权限缺失显示的Dialog
     *
     * @param context           Context
     * @param message           提示信息
     * @param SillyPermissionClick 回调
     */
    private fun showMissingPermissionDialog(context: Context, message: String,
                                            SillyPermissionClick: SillyPermissionClick) {
        mSillyPermissionClick = SillyPermissionClick
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle("权限缺失")
        builder.setMessage(message)
        builder.setNegativeButton("取消") { _, _ ->
            mSillyPermissionClick?.onClickNO()
        }
        builder.setPositiveButton("设置") { _, _ ->
            mSillyPermissionClick?.onClickYES()
        }
        builder.show()
    }

    /**
     * dialog提示内容
     */
    private fun permissionMessage(permissions: String): String {
        return when (permissions) {
            PERMISSION_CALENDAR -> "日历"
            PERMISSION_CAMERA -> "相机"
            PERMISSION_CONTACTS -> "联系人"
            PERMISSION_LOCATION -> "定位"
            PERMISSION_AUDIO -> "麦克"
            PERMISSION_PHONE -> "手机状态"
            PERMISSION_SENSORS -> "传感器"
            PERMISSION_SMS -> "短信"
            PERMISSION_STORAGE -> "存储"
            else -> ""
        }
    }

    /**
     * 权限遍历判断
     * @param permissions 权限集合
     * @return 返回提示信息
     */
    private fun getPermissionTips(vararg permissions: String): String {
        var tip = ""
        for (i in permissions.indices) {
            tip = if (i == 0) {
                permissionMessage(permissions[i])
            } else {
                "$tip、${permissionMessage(permissions[i])}"
            }
        }
        tip = "${tip}未授权，功能无法使用,请点击 设置-权限"
        return tip
    }

    /**
     * 注销SillyPermission
     */
    fun cancel() {
        mSillyPermissionClick = null
        mSillyPermissionCall = null
        mPermissions = null
    }
}