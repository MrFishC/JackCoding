import 'package:shared_preferences/shared_preferences.dart';

class SharedPreferencesU {
  SharedPreferences? _sharedPreferences;
  static SharedPreferencesU? _instance;

  SharedPreferencesU.of() {
    init();
  }
  SharedPreferencesU._pre(SharedPreferences prefs) {
    _sharedPreferences = prefs;
  }

  static SharedPreferencesU getInstance() {
    _instance ??= SharedPreferencesU.of();
    return _instance!;
  }

  void init() async {
    _sharedPreferences ??= await SharedPreferences.getInstance();
  }

  static Future<SharedPreferencesU> preInit() async {
    if (_instance == null) {
      var prefs = await SharedPreferences.getInstance();
      _instance = SharedPreferencesU._pre(prefs);
    }
    return _instance!;
  }

  void setData<T>(String key, T data) {
    if (data is String) {
      _sharedPreferences?.setString(key, data);
    } else if (data is double) {
      _sharedPreferences?.setDouble(key, data);
    } else if (data is int) {
      _sharedPreferences?.setInt(key, data);
    } else if (data is bool) {
      _sharedPreferences?.setBool(key, data);
    } else if (data is List<String>) {
      _sharedPreferences?.setStringList(key, data);
    }
  }

  void remove(String key) {
    _sharedPreferences?.remove(key);
  }

  T? get<T>(String key) {
    var value = _sharedPreferences?.get(key);
    if (value != null) {
      return value as T;
    }
    return null;
  }
}