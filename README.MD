### 添加存储库 [![](https://jitpack.io/v/SJJ-dot/RTPermission.svg)](https://jitpack.io/#SJJ-dot/RTPermission)
- 项目根目录build.gradle添加存储库
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- 依赖
```groovy
	dependencies {
	        implementation 'com.github.SJJ-dot:RTPermission:Tag'
	}
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
