package cn.edu.heuet.slidingconflictdemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.heuet.slidingconflictdemo.R;


/**
 * RecyclerViewInViewPager2.java 源码基于以下两篇文章整合而来：
 * [《解决ViewPager2嵌套RecyclerView滑动冲突的问题》](https://blog.csdn.net/jingzz1/article/details/101535788)
 * [《仿豆瓣弹性滑动控件，史上最全方位讲解事件滑动冲突》](https://www.jianshu.com/p/2d7a63455c83)
 * 如果想了解一些理论知识，可以参考：
 * 《Android开发艺术探索》章节3.5.3  作者：任玉刚
 * 《Android事件分发机制完全解析，带你从源码的角度彻底理解(上)》 作者：郭霖
 * 《Android事件分发机制完全解析，带你从源码的角度彻底理解(下)》 作者：郭霖
 * <p>
 * Google官方给出了一个ViewPager2解决滑动冲突的Demo：[《ViewPager2 with Nested RecyclerViews》](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt)
 * 不过，在那个示例中Google官方也说了：
 * This solution has limitations when using multiple levels of nested scrollable elements
 * 此方法在使用多层嵌套时有局限性
 * (e.g. a horizontal RecyclerView in a vertical RecyclerView in a horizontal ViewPager2).
 * 例如：一个 ↔ 水平的ViewPager2
 * 嵌套一个 ↕ 垂直的RecyclerView
 * 嵌套一个 ↔ 水平的RecyclerView
 * <p>
 * 此类解决了ViewPager2、SwipeRefreshLayout、RecyclerView、Banner(RecyclerView的HeaderView)之间的滑动冲突
 * 布局层级关系为：
 * ViewPager2 ( ↔ )
 * |--> SwipeRefreshLayout ( ↕ )
 * |--> RecyclerView （ ↕ ）
 * |--> Banner(RecyclerView的HeaderView) ( ↔ )
 * <p>
 * 更多详情参考我的博客文章[《【Android】解决一种常见的复杂的滑动冲突》]()
 */


public class RecyclerViewInViewPager2 extends RecyclerView {

    public RecyclerViewInViewPager2(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewInViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewInViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int startX, startY;

    // 分发Touch事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                /*
                此处 requestDisallowInterceptTouchEvent(true)
                表示内部拦截法
                详情见《Android开发艺术探索》章节3.5.3  作者：任玉刚
                true 请求父布局放行Touch事件，交由当前布局处理
                */
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                [《android MotionEvent中getX()和getRawX()的区别》](https://www.cnblogs.com/wangying222/p/5498753.html)
                */
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX); // 取绝对值
                int disY = Math.abs(endY - startY); // 取绝对值
                // 横向滑动
                // 解决ViewPager2与Banner的水平滑动冲突
                if (disX > disY) {
                    boolean isConsume = false;
                    // 取得Banner的标签
                    View bannerView = findViewById(R.id.top_banner);
                    if (bannerView != null) {
                        String bannerTag = bannerView.getTag().toString();
                        // 判断是否由当前布局消耗Touch事件
                        isConsume = childInterceptEvent(this, bannerTag, (int) ev.getRawX(), (int) ev.getRawY());
                    }
                    // isConsume == true 父布局放行，当前布局消耗Touch事件
                    // isConsume == false 父布局不放行，父布局消耗Touch事件
                    this.getParent().requestDisallowInterceptTouchEvent(isConsume);
                }
                // 纵向滑动
                // 解决SwipeRefreshLayout下拉刷新与RecyclerView的垂直滑动冲突
                else {
                    this.getParent().requestDisallowInterceptTouchEvent(this.canScrollVertically(startY - endY));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    // 递归遍历当前View树
    private boolean childInterceptEvent(ViewGroup parentView, String targetViewTag, int touchX, int touchY) {
        boolean isConsume = false;
        for (int i = parentView.getChildCount() - 1; i >= 0; i--) {
            View childView = parentView.getChildAt(i);
            if (!childView.isShown()) {
                continue;
            }
            // 判断view是否在触摸区域内
            boolean isTouchView = isTouchView(touchX, touchY, childView);
            // 消耗Touch事件的条件
            if (isTouchView && childView.getTag() != null && targetViewTag.equals(childView.getTag().toString())) {
                isConsume = true;
                break;
            }
            // 如果前面都不满足，继续递归遍历
            if (childView instanceof ViewGroup) {
                ViewGroup itemView = (ViewGroup) childView;
                if (!isTouchView) {
                    continue;
                } else {
                    isConsume = childInterceptEvent(itemView, targetViewTag, touchX, touchY);
                    if (isConsume) {
                        break;
                    }
                }
            }
        }
        return isConsume;
    }

    // 判断view是否在触摸区域内
    private boolean isTouchView(int touchX, int touchY, View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect.contains(touchX, touchY);
    }
}