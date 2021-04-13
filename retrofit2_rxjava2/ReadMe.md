
笔记,[参考](https://www.jianshu.com/p/2e8b400909b7)

### 1.1 导入依赖
    compile "io.reactivex.rxjava2:rxjava:2.1.0"                 // 必要rxjava2依赖
    compile "io.reactivex.rxjava2:rxandroid:2.0.1"              // 必要rxandrroid依赖，切线程时需要用到
    compile 'com.squareup.retrofit2:retrofit:2.3.0'             // 必要retrofit依赖
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'      // 必要依赖，和Rxjava结合必须用到，下面会提到
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'       // 必要依赖，解析json字符所用
    compile 'com.squareup.okhttp3:logging-interceptor:3.8.1'    // 非必要依赖， log依赖，如果需要打印OkHttpLog需要添加

### 1.2 新建Manger类，管理所需要的API
    + 在Application里面初始化。
    + 使用单例模式。
    + 在一个"管理类"中初始化所需要的所有参数。

    [addInterceptor()更多用法](http://blog.csdn.net/jdsjlzx/article/details/52063950)

    1.2.1.初始化Retrofit的时候的两个必要配置：
    + addCallAdapterFactory(RxJava2CallAdapterFactory.create()):决定你的返回值是Observable还是Call;
    + addConverterFactory(GsonConverterFactory.create()):将服务器返回的json字符串转化为对象;可以自定义
    Converter来应对服务器返回的不同的数据的,[自定义Converter](https://blog.csdn.net/new_abc/article/details/53021387);

    1.2.2.初始化Request:即API封装类;
    1.2.3.约定Response;
    1.2.4.定义ResponseTransformer处理数据和异常;
    + 自己定义的类,好处: todo
    1.2.5.处理Exception,这里的Exception分为两部分：
    + 自己本地产生的Exception，比如解析错误，网络链接错误等等。
    + 服务器产生的Excption，比如404，503等等服务器返回的Excption。
    1.2.6.线程切换
    + 将线程切换单独封装成一个类再结合compose使用.


### 1.3 拦截器

    [参考](https://blog.csdn.net/u011486491/article/details/80669346)

    1.3.1.应用拦截器：是在请求执行刚开始，还没有执行OkHttp的核心代码前进行拦截，使用addInterceptor方法；

    1.3.2.网络拦截器：在连接网络之前进行拦截，使用addNetWorkInterceptor方法；

    1.3.3.应用拦截器和网络拦截器比较
        前者，不会影响OKHttp的请求策略和请求速度，即使是从缓存中取数据，应用拦截器也会执行，允许重试（即Chain.proceed()可以执行多次），只会在response被调用一次；
        后者（理解的不是很清晰，暂时做个笔记，过段时间就会明白的）,可以操作像重定向和重试这样的中间响应,对于短路网络的缓存响应不会调用,监视即将要通过网络传输的数据,访问运输请求的Connection，会在request和resposne时分别被调用一次;

    1.3.4.拦截器的重要对象
        Request类
        Response类

    1.3.5.拦截器的使用
        网络请求log打印
        url的判断和再跳转
        公共变量的添加（添加token）
        返回数据的时候根据code进行数据的分类处理

    1.3.6.Interceptor两种缓存


