
# Flutter报错记录
## 1.数据解析插件
+ 痛点问题
+ 使用FlutterJsonBeanFactory插件没有生成对应的文件，[参考资料-无效](https://blog.csdn.net/weixin_45677723/article/details/134005991)
  + 由于是在Android项目中混合了flutter，开始并未生成代码，后面是把flutter模块单独当成一个项目打开，代码就可以自动生成了。
+ 暂时不使用该插件，采用[json_serializable 解析](https://blog.csdn.net/jdsjlzx/article/details/126145817)
  + [辅助-为了便利使用 json_serializable库](https://caijinglong.github.io/json2dart/index_ch.html)
-------------
+ 以json_serializable的方式创建model类
+ json_serializable 支持泛型（需要添加注解），借助辅助工具[为了便利使用 json_serializable库](https://caijinglong.github.io/json2dart/index_ch.html)
+ 在Android嵌套flutter模块的方式下，生成的g.dart文件比较理想。

## 1.stderr: fatal: unable to connect to github.com:
+ 更改pubspec.yaml中git中url的地址，如将git:开头的改成https:开头的,同时注意是否有使用翻墙工具；
+ 我们在当前项目的目录下运行flutter packages pub run build_runner build
## 2.一路飘摇:解决由于flutter的升级导致第三方库无法使用的问题
+ [一路飘摇:解决由于flutter的升级导致第三方库无法使用的问题](https://www.jianshu.com/p/c2ecfc304c5c)

## 3.【Flutter 问题系列第 68 篇】为什么提示 The plugins “xxx_plugin“ use a deprecated version of the Android embedding
+ [【Flutter 问题系列第 68 篇】为什么提示 The plugins “xxx_plugin“ use a deprecated version of the Android embedding](https://blog.csdn.net/qq_42351033/article/details/125340617)

## 4.同样的代码，换一台设备重新拉取，build都没有通过，尤其是flutter模块，会提示一些报错信息；
+ 跟下载的依赖包环境有很大关系，拆解进行处理啊
+ 4.1.Android依赖了flutter模块，先将flutter模块有关的配置注释掉，单独让Android项目依赖成功下载。
+ 4.2.Android项目依赖成功之后在取消4.1中的注释，继续同步。此时，可能会报错，提示flutter模块下Android模块缺少local.properties（因为在忽略文件中做了配置）；
+ 4.3.进入flutter模块，点击pub get
+ 总结1：经过以上几步，问题得到解决。
+ 开始没有按照上方的步骤来进行，出现了多种报错，尝试过很多中方式，都不管用，于是采取分解的方式逐一的缩小范围；
+ 依赖包的下载环境还是比较重要，由于网络的不稳定性，需要检查辅助工具是否稳定；
+ 提示：同样的方式，在笔记本上操作可以处理好。换了一个环境和电脑进行操作，Android部分依赖下载正常，但是Android模块依赖flutter模块后重新同步出现了失败，flutter引擎相关的依赖没有下载下来；
+ 换一种思路，检查flutter模块，执行flutter clean、flutter pub，再来同步Android项目，问题仍存在；
  + 存在的区别：笔记本打开Android项目时，project下包含两个项目的目录，但是，这里仅存在Android的项目目录；
  + 猜测：未成功的原因，应该是4.2中再次同步时flutter引擎依赖没有下载成功导致的，可能还是下载依赖所在的环境导致（应该是local.properties中flutter.sdk未配置正确）。
+ 总结2：提示缺少xxx等或者xxx失败等，猜测更多是因为配置或下载依赖的原因导致的； 
+ 补充1：时不时报错“this and base files have different roots xxx”，处理方式：指定Flutter的pub缓存目录，通知环境变量--->***用户变量***设置。key为：PUB_CACHE，value：指定一个目录；
第4点的问题，大概率跟PUB_CACHE有关系，[参考资料-this and base files have different roots](https://blog.csdn.net/LuoHuaX/article/details/132304886)中提到：pub get下载的缓存位置和项目位置不在同一个磁盘，就会报这个错。
估计是这个原因；
+ 补充2：x:\xxx\flutter\packages\flutter_tools\gradle\src\main\groovy中的flutter.groovy可以设置依赖的镜像地址；
=======

## 5.运行开源flutter项目出现的报错
+ 处理方案：将compileSdkVersion提升，目前是由 28 ---> 31
```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:checkDebugAarMetadata'.
> Multiple task action failures occurred:
   > A failure occurred while executing com.android.build.gradle.internal.tasks.CheckAarMetadataWorkAction
      > The minCompileSdk (31) specified in a
        dependency's AAR metadata (META-INF/com/android/build/gradle/aar-metadata.properties)
        is greater than this module's compileSdkVersion (android-28).
        Dependency: androidx.window:window-java:1.0.0-beta04.
        AAR metadata file: D:\xxx\Cache\AndroidStudio\.gradle\caches\transforms-2\files-2.1\b2d779a3a6f49cacbe1c4a638e946840\jetified-window-java-1.0.0-beta04\META-INF\com\android\build\gradle\aar-metadata.properties.
   > A failure occurred while executing com.android.build.gradle.internal.tasks.CheckAarMetadataWorkAction
      > The minCompileSdk (31) specified in a
        dependency's AAR metadata (META-INF/com/android/build/gradle/aar-metadata.properties)
        is greater than this module's compileSdkVersion (android-28).
        Dependency: androidx.window:window:1.0.0-beta04.
        AAR metadata file: D:\xxx\Cache\AndroidStudio\.gradle\caches\transforms-2\files-2.1\9c5fcee88564c7e411a0fe1eb9c506c8\jetified-window-1.0.0-beta04\META-INF\com\android\build\gradle\aar-metadata.properties.

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 3s

┌─ Flutter Fix ───────────────────────────────────────────────────────┐
│ [!] Your project requires a higher compileSdkVersion.               │
│ Fix this issue by bumping the compileSdkVersion in                  │
│ G:\develop\code\wanandroid_flutter-master\android\app\build.gradle: │
│ android {                                                           │
│   compileSdkVersion 31                                              │
│ }                                                                   │
└─────────────────────────────────────────────────────────────────────┘
Exception: Gradle task assembleDebug failed with exit code 1

```

# 参考资料
+ https://github.com/yechaoa/wanandroid_flutter
