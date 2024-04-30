
+ ViewGroup的职责
+ View的职责
+ ViewGroup和LayoutParams之间的关系

+ View的3种测量模式

+ 何为流式布局
  + 根据ViewGroup的宽，自动的往右添加，如果当前行剩余空间不足，则自动添加到下一行。
  + 大概实现思路：测量子View和自身宽高，需要考虑到Margin，我们借助MarginLayoutParams，对子View进行摆放。

+ 参考
+ [Android中View的布局及绘图机制](https://blog.csdn.net/iispring/article/details/49203945)
  + onMeasure与measure的关系
    + onMeasure方法会完成具体的量算工作
      + onMeasure方法会将测量后的结果保存到View中
      + 子类若需要的话，则重写的是该方法
    + 如果对一个View进行测量：View的measure方法
  + onLayout与layout的关系
    + 同onMeasure与measure的关系类似

+ 测量的目的：让View的父控件知道View想要多大的尺寸
+ (View)measure方法细节
  + 首先走缓存，没有缓存则走onMeasure（进行具体的测量工作），测量后的结果通过View的setMeasuredDimension方法保存到成员变量中
    + 自定义的View重写了onMeasure，若未调用setMeasuredDimension()方法会抛出异常
  + 测量完成，View的父控件通过getMeasuredWidth、getMeasuredState、getMeasuredWidthAndState方法获取View测量后的结果
  
+ MeasureSpec
  + View的静态内部类
  + 模式
    + AT_MOST
    + EXACTLY
    + UNSPECIFIED
  + 尺寸
  + Api调用
    + MeasureSpec.getSize(int measureSpec)：解析出尺寸信息
    + MeasureSpec.getMode(int measureSpec)：解析出mode信息
+ onMeasure默认实现所做的工作
  + getSuggestedMinimumWidth/getSuggestedMinimumHeight：返回View推荐的最小宽度/高度
  + getDefaultSize
    + 解析出尺寸和mode信息
    + 当mode是UNSPECIFIED，View使用想要的Size作为测量的结果
    + 当mode是AT_MOST/EXACTLY，View使用解析出的尺寸作为测量的结果

+ View的父ViewGroup如何读取View测量的结果
  + 三组Api
    + getMeasuredWidth和getMeasuredHeight:该组方法只返回测量结果的尺寸信息，去掉了高位字节的状态信息
    + getMeasuredWidthAndState和getMeasuredHeightAndState：该组方法返回测量的尺寸信息和state（如果存在）信息
    + getMeasuredState:只包含状态信息，不包含尺寸信息
+ measureSpec跟mMeasuredWidth（或mMeasuredHeight）的区别
  + 前者：将限制条件mode从ViewGroup传递给子View
  + 后者：将带有测量结果的state标志位信息从View传递给ViewGroup
  + 两者区别体现相关的代码：resolveSizeAndState方法
    + 当父控件指定的尺寸小于View想要的尺寸，会向测量结果加入尺寸太小的标记

+ [generateLayoutParams() 方法的作用](https://blog.csdn.net/wjr1949/article/details/105607740)
+ 只要重写 generateLayoutParams(AttributeSet attrs) 就可以获取 MarginLayoutParams。
+ [](https://blog.csdn.net/yisizhu/article/details/51582622)