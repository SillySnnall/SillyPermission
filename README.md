# SillyPermission
Android 6.0+动态权限申请类</br>
minSdkVersion 16+
***
## 效果
<div>
   <img src="https://github.com/StringBOX/SillyPermission/blob/master/img/Screenshot_20180313-162726.png" width="48%"/>
   <img src="https://github.com/StringBOX/SillyPermission/blob/master/img/Screenshot_20180313-162738.png" width="48%"/>
</div>
</br>
<div>
   <img src="https://github.com/StringBOX/SillyPermission/blob/master/img/Screenshot_20180313-162750.png" width="48%"/>
   <img src="https://github.com/StringBOX/SillyPermission/blob/master/img/Screenshot_20180313-162817.png" width="48%"/>
</div>
## 使用
1.Add it in your root build.gradle at the end of repositories:
<pre><code>
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
</code></pre>
2.Add the dependency:
<pre><code>
dependencies {
  compile 'com.github.StringBOX:SillyPermission:v1.0'
}
</code></pre>
### Java
<pre><code>
// 申请一个权限
SillyPermission.INSTANCE.requestPermission(this, new SillyPermissionCall() {
   @Override
  public void call(boolean b) {
    if (b) Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
    else Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
  }
},PERMISSION_STORAGE);

// 申请多个权限
SillyPermission.INSTANCE.requestPermission(this, new SillyPermissionCall() {
   @Override
  public void call(boolean b) {
    if (b) Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
    else Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
  }
},PERMISSION_CALENDAR, PERMISSION_CAMERA, PERMISSION_CONTACTS, PERMISSION_LOCATION, PERMISSION_AUDIO,
    PERMISSION_PHONE, PERMISSION_SENSORS, PERMISSION_SMS, PERMISSION_STORAGE);

// 必须在Activity中重写 onActivityResult 做回调
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  SillyPermission.INSTANCE.onActivityResultPermission(this,requestCode);
}
// 必须在Activity中重写 onRequestPermissionsResult 做回调
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  SillyPermission.INSTANCE.onRequestPermissionsResultPermission(this,requestCode,permissions,grantResults);
}
</code></pre>
### Kotlin
<pre><code>
// 申请一个权限
SillyPermission.requestPermission(this, SillyPermissionCall {
  if (it) Toast.makeText(this, "日历权限申请成功", Toast.LENGTH_SHORT).show()
  else Toast.makeText(this, "日历权限被拒绝", Toast.LENGTH_SHORT).show()
}, PERMISSION_CALENDAR)

// 申请多个权限
SillyPermission.requestPermission(this, SillyPermissionCall {
  if (it) Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show()
  else Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show()
}, PERMISSION_CALENDAR, PERMISSION_CAMERA, PERMISSION_CONTACTS, PERMISSION_LOCATION, PERMISSION_AUDIO,
    PERMISSION_PHONE, PERMISSION_SENSORS, PERMISSION_SMS, PERMISSION_STORAGE)

// 必须在Activity中重写 onActivityResult 做回调
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
  super.onActivityResult(requestCode, resultCode, data)
  SillyPermission.onActivityResultPermission(this, requestCode)
}

// 必须在Activity中重写 onRequestPermissionsResult 做回调
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  SillyPermission.onRequestPermissionsResultPermission(this, requestCode, permissions, grantResults)
}
</code></pre>
## 权限常量
|     权限         | 使用常量    |  详细权限  |
|:----:            |:------:         |:-------:|
| 日历        | PERMISSION_CALENDAR      |   READ_CALENDAR、WRITE_CALENDAR    |
| 相机        | PERMISSION_CAMERA      |   CAMERA    |
| 联系人        | PERMISSION_CONTACTS      |   READ_CONTACTS、WRITE_CONTACTS、GET_ACCOUNTS    |
| 定位        | PERMISSION_LOCATION      |   ACCESS_FINE_LOCATION、ACCESS_COARSE_LOCATION    |
| 麦克风        | PERMISSION_AUDIO      |   RECORD_AUDIO    |
| 手机状态        | PERMISSION_PHONE      |   READ_PHONE_STATE、CALL_PHONE、READ_CALL_LOG、WRITE_CALL_LOG、ADD_VOICEMAIL、USE_SIP、PROCESS_OUTGOING_CALLS    |
| 传感器        | PERMISSION_SENSORS      |   BODY_SENSORS    |
| 短信        | PERMISSION_SMS      |   SEND_SMS、RECEIVE_SMS、READ_SMS、RECEIVE_WAP_PUSH、RECEIVE_MMS    |
| 存储        | PERMISSION_STORAGE      |   READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE    |
