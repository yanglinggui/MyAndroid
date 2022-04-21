package com.ylg.testdemo;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * WindowInsetsAnimation.Callback 用来返回你指定类型的转换后的View
 * 它通过在动画开始之前复制视图的边界来实现
 * 动画开始的时候，随着动画的进行过程，监听器及时更新View的属性，
 * 让动画从开始到结束阶段开始移动
 * 但是不会处理View更新过程中View的size的变化
 */
public class TranslateViewInsetsAnimationListener extends WindowInsetsAnimation.Callback {

    /**
     * 应用动画的对象
     */
    private View view;
    /**
     * 应用的bitMask类型
     */
    private int typeMask;

    //开始和结束的view bounds容器
    private Rect startBounds = new Rect();
    private Rect endBounds = new Rect();

    //初始化
    public TranslateViewInsetsAnimationListener(@NonNull final View view, @NonNull final int typeMask) {
        super(DISPATCH_MODE_STOP);
        this.view = view;
        this.typeMask = typeMask;
    }

    /**
     * @param animation onPrepare()在insets动画开始前被调用,此时的 window 还没有加载到新的位置
     *                  在此获取初始的bounds
     */
    @Override
    public void onPrepare(@NonNull WindowInsetsAnimation animation) {
        super.onPrepare(animation);
        Log.i("qiao","TranslateViewInsetsAnimationListener onPrepare");
        //将View当前状态存入startBounds
        Utils.copyBounds(view, startBounds);
    }

    /**
     * @param animation
     */
    @NonNull
    @Override
    public WindowInsetsAnimation.Bounds onStart(@NonNull WindowInsetsAnimation animation, @NonNull WindowInsetsAnimation.Bounds bounds) {
        Log.i("qiao","TranslateViewInsetsAnimationListener onStart");
        //获取结束状态的bounds
        Utils.copyBounds(view, endBounds);
        //关闭了所有父类的Clip防止留白
        Object parent = view.getParent();
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                Utils.storeClipChildren((ViewGroup) parent);
                ((ViewGroup) parent).setClipChildren(false);
                Utils.storeClipToPadding((ViewGroup) parent);
                ((ViewGroup) parent).setClipToPadding(false);
            }
            parent = ((ViewParent) parent).getParent();
        }
        view.setTranslationX((float) (startBounds.right - endBounds.right));
        view.setTranslationY((float) (startBounds.bottom - endBounds.bottom));
        return bounds;

    }

    /**
     * onProgress()在动画进行时被调用…
     */
    @NonNull
    @Override
    public WindowInsets onProgress(@NonNull WindowInsets insets, @NonNull List<WindowInsetsAnimation> runningAnimations) {
        Log.i("qiao","TranslateViewInsetsAnimationListener onProgress");
        //这个监听器只关心与typeMask匹配的动画
        //第一个动画
        WindowInsetsAnimation filteredAnim = null;
        Log.e("TranslateViewInsetsAnimationListener", "onProgress");
        Log.e("runningAnimations:", runningAnimations.size() + "");
        //遍历获取typemask匹配的anim
        filteredAnim = runningAnimations.stream()
                .filter(o -> o.getTypeMask() == this.typeMask)
                .findAny().orElse(null);

        //用插值器获取动画进度,进行操作
        if (filteredAnim != null) {
            view.setTranslationX(
                    Utils.lerp((startBounds.right - endBounds.right),
                            0,
                            filteredAnim.getInterpolatedFraction()));
            view.setTranslationY(
                    Utils.lerp(startBounds.bottom - endBounds.bottom,
                            0,
                            filteredAnim.getInterpolatedFraction())
            );
        }

        return insets;
    }

    //onEnd()在动画结束时调用…
    @Override
    public void onEnd(@NonNull WindowInsetsAnimation animation) {
        Log.i("qiao","TranslateViewInsetsAnimationListener onEnd");
        //根据typeMask过滤到匹配的WindowInsetsAnimation对象
        if (animation.getTypeMask() == this.typeMask) {
            Object parent = view.getParent();
            while (parent != null) {
                if (parent instanceof ViewGroup) {
                    //恢复原态
                    Utils.restoreClipChildren((ViewGroup) parent);
                    Utils.restoreClipToPadding((ViewGroup) parent);
                }
                parent = ((ViewParent) parent).getParent();
            }
            //属性重置
            view.setTranslationX(0f);
            view.setTranslationY(0f);
            //清除状态
            startBounds.setEmpty();
            endBounds.setEmpty();
        }
    }

}
