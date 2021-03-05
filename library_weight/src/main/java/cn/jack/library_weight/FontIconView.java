package cn.jack.library_weight;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @创建者 Jack
 * @创建时间 2021/3/3 15:05
 * @描述
 * 参考：https://www.jianshu.com/p/52169364a25b
 */
public class FontIconView extends AppCompatTextView{

    public FontIconView(Context context) {
        super(context);
        init(context);
    }

    public FontIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        //设置字体图标
        Typeface font = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        this.setTypeface(font);
    }

}
