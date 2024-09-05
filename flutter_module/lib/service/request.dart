// import 'package:dio/dio.dart';
//
// //https://pub-web.flutter-io.cn/packages/dio
//
// class XTXRequestManager {
//   Dio? dio;
//   //1.私有的静态属性
//   static XTXRequestManager? _instance;
//
//   //2.私有的命名构造函数
//   XTXRequestManager._initManager() {
//     if (dio == null) {
//       //使用BaseOptions设置全局的配置信息
//       BaseOptions baseOptions = BaseOptions(
//           connectTimeout: Duration(seconds: 15000),
//           receiveTimeout: Duration(seconds: 5000),
//           baseUrl: "https://www.wanandroid.com/");
//       dio = Dio(baseOptions);
//       //拦截器
//       dio!.interceptors.add(
//         InterceptorsWrapper(
//           onRequest:
//               (RequestOptions options, RequestInterceptorHandler handler) {
//             //设置全局的请求头
//             options.headers = {
//               'Authorization': '', // 用户身份信息（只有注册登录才有，如果当前没有可以指定''）
//               'source-client': 'app', // 客户端类型
//             };
//             return handler.next(options);
//           },
//           onResponse: (Response response, ResponseInterceptorHandler handler) {
//             return handler.next(response);
//           },
//           onError: (DioException error, ErrorInterceptorHandler handler) {
//             //统一的异常处理
//             return handler.next(error);
//           },
//         ),
//       );
//     }
//   }
//
//   //3.创建单例对象并向外界提供单例对象的方法（工厂构造函数）
//   factory XTXRequestManager() {
//     //判断单例对象是否存在，如果不存在，新建单例对象，反之，直接返回单例对象
//     if (_instance == null) {
//       _instance = XTXRequestManager._initManager();
//     }
//     return _instance!;
//   }
//
//   // 处理请求的公共方法
//   Future<Response> handleRequest(
//     String path,
//     String method, {
//     data,
//     Map<String, dynamic>? queryParameters,
//   }) {
//     return dio!.request(
//       path,
//       data: data,
//       queryParameters: queryParameters,
//       options: Options(method: method),
//     );
//   }
// }
