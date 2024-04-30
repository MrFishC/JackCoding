package cn.jack.module_fragment_04.entity

/**
 * @author Jack
 * @time
 * @describe
 */
class AllFunctionInfoRes() {
    var name: String? = null
    var children: MutableList<ChildrenBean>? = null

    class ChildrenBean {

        var attributes: AttributesBean? = null

        class AttributesBean {
            var appFunctionIcon: String? = null
            var appFunctionName: String? = null
            var invokingWay = 0
        }
    }
}