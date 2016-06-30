package com.voisd.testmain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.voisd.vanimator.animator.CarAddView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jm on 2016/6/29.
 */
public class MainActivity extends AppCompatActivity {

    View carImg,addTv,enterbtn;
    CarAddView carAddView;
    SystemBarTintManager tintManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSystemBar(R.color.colorWhite);
        carImg = (View)findViewById(R.id.car_Img);
        addTv = (View)findViewById(R.id.add_Tv);
        enterbtn = (View)findViewById(R.id.enter_btn);
        carAddView = (CarAddView)findViewById(R.id.carAddView);
        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carAddView.setAnimScreenSystemBar(addTv,carImg,-1,1300).showAnim();
            }
        });

    }


    public void setupSystemBar(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorRes);
        tintManager.setStatusBarTintColor(android.R.color.black);

        setStatusBarDarkMode(true, this);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {

        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


}
