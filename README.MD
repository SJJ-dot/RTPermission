### 添加存储库
- 项目根目录build.gradle添加存储库
```groovy
allprojects {
    repositories {
        ...
        maven {
            url "https://raw.githubusercontent.com/lTBeL/repo/master/repository"
        }
    }
}
```
- 依赖
```groovy
    implementation 'com.sjianjun:permission-request:1.1.4'
```
- 代码
```kotlin
    PermissionUtil.requestPermissions(
        context,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE)
    ) {
        Log.e(it.isGranted())
        Log.e(it)
    }
```