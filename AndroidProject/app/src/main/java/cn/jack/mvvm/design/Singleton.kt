package cn.jack.mvvm.design

/**
 * @创建者 Jack
 * @创建时间 2024-06-09 17:18
 * @描述
 */
//object Singleton {
//
//}

//class Singleton private constructor(){
//    companion object{
//        private var instance:Singleton? = null
//            get() {
//                if(field == null){
//                    field = Singleton()
//                }
//                return field
//            }
//
//        @Synchronized
//        fun get():Singleton{
//            return instance!!
//        }
//    }
//}

//class Singleton private constructor(){
//    companion object {
//        val INSTANCE:Singleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            Singleton()
//        }
//    }
//}

class Singleton {
    companion object {
        val instance = SingletonProvider.holder
    }

    private object SingletonProvider {
        val holder = Singleton()
    }
}