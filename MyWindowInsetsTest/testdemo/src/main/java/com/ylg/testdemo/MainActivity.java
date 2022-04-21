package com.ylg.testdemo;

import android.graphics.Insets;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.widget.LinearLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE;
import static android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_STOP;
import static android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvChat;

    private LinearLayout container;//根视图

    private LinearLayout messageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        container = findViewById(R.id.container);
        messageHolder = findViewById(R.id.message_holder);
        //初始化RecycleView
        rvChat = findViewById(R.id.rv_chat);
        rvChat.setAdapter(new RecycleviewAdapter());

        //必要，否正inset填充物不生效
        getWindow().setDecorFitsSystemWindows(false);
        //container.getWindowInsetsController().hide(WindowInsets.Type.systemBars());
       // container.getWindowInsetsController().setSystemBarsBehavior(BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        /*container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {

            //OnApplyWindowInsetsListener会自动更新我们的根视图向上移动，根据输入法和底部虚拟导航栏的size
            @Override
            public WindowInsets onApplyWindowInsets(View rootView, WindowInsets windowInsets) {
                Log.i("qiao","container setOnApplyWindowInsetsListener");

                //这里返回一个新的设置替代WindowInsets.consumeSystemWindowInsets()
                return windowInsets;
            }
        });*/

        //TranslateViewInsetsAnimationListener会自动它会自动移动他的绑定视图作为软键盘动画
        /*rvChat.setWindowInsetsAnimationCallback
                (new TranslateViewInsetsAnimationListener(rvChat, WindowInsets.Type.ime()));

        messageHolder.setWindowInsetsAnimationCallback
                (new TranslateViewInsetsAnimationListener(messageHolder, WindowInsets.Type.ime()));*/
        /*messageHolder.setWindowInsetsAnimationCallback(new WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            @NonNull
            @Override
            public WindowInsets onProgress(@NonNull WindowInsets insets, @NonNull List<WindowInsetsAnimation> runningAnimations) {
                Log.i("qiao","TranslateViewInsetsAnimationListener onProgress");
                Log.e("qiao", "ime:" + insets.getInsets(WindowInsets.Type.ime()).top +
                        " " + insets.getInsets(WindowInsets.Type.ime()).bottom);
                return insets;
            }

            @Override
            public void onPrepare(@NonNull WindowInsetsAnimation animation) {
                Log.i("qiao","TranslateViewInsetsAnimationListener onPrepare");
                super.onPrepare(animation);
            }

            @NonNull
            @Override
            public WindowInsetsAnimation.Bounds onStart(@NonNull WindowInsetsAnimation animation, @NonNull WindowInsetsAnimation.Bounds bounds) {
                Log.i("qiao","TranslateViewInsetsAnimationListener onStart");
                return super.onStart(animation, bounds);
            }

            @Override
            public void onEnd(@NonNull WindowInsetsAnimation animation) {
                Log.i("qiao","TranslateViewInsetsAnimationListener onEnd");
                super.onEnd(animation);
            }

        });*/
        //通过监听用户对RecycleView滚动监听来控制拖动打开IME（软键盘）*/
        rvChat.setOnTouchListener(new InsetsAnimationOverscrollingTouchListener());

    }

}