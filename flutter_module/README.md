
# Flutter报错记录
## 1.数据解析插件
+ 痛点问题
+ 使用FlutterJsonBeanFactory插件没有生成对应的文件，[参考资料-无效](https://blog.csdn.net/weixin_45677723/article/details/134005991)
  + 由于是在Android项目中混合了flutter，开始并未生成代码，后面是把flutter模块单独当成一个项目打开，代码就可以自动生成了。
+ 暂时不适用该插件，采用[json_serializable 解析](https://blog.csdn.net/jdsjlzx/article/details/126145817)
  + [辅助-为了便利使用 json_serializable库](https://caijinglong.github.io/json2dart/index_ch.html)
  + 出现生成的数据格式不理想的情况（可能是缓存原因，猜测原因有可能同上）

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
+ 总结：经过以上几步，问题得到解决。
+ 开始没有按照上方的步骤来进行，出现了多种报错，尝试过很多中方式，都不管用，于是采取分解的方式逐一的缩小范围；
+ 依赖包的下载环境还是比较作用，由于网络的不稳定性，z；