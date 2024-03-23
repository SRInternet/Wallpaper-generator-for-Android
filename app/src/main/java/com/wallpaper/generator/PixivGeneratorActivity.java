package com.wallpaper.generator;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.microsoft.clarity.Clarity;
import com.microsoft.clarity.ClarityConfig;
import com.microsoft.clarity.models.ApplicationFramework;
import com.microsoft.clarity.models.LogLevel;

public class PixivGeneratorActivity extends AppCompatActivity {
    // 重要注释：代码中部分敏感字符串存储的xml文件未推送至存储库，请注意处理所有报错的地方
    // proxy：私有Pixiv反代服务器；cid：Microsoft Clarity 项目ID
    private Boolean NavigationShowed = false ;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private List<Drawable> drawables = new ArrayList<>();

    private List<String> informations = new ArrayList<>();

    private int ImagePosition;

    private String NoResibonse  = "思锐工作室 对于《壁纸生成器》系列产品的用户须知和免责声明（以下简称《声明》），请所有用户在使用本产品前务必认真仔细阅读。使用本产品即代表您同意本《声明》。" +
            "\n1. 本软件依赖于 Miklroi API、Lolicon API 等其他接口和开源项目，所有本产品相关的内容及所生成的任何图片均不可以用作任何商业用途，生成的图片由其作者保留所有权利，思锐工作室及其用户不承担任何责任和义务保护图片资源的规范和权利不受侵犯。若是AI生成的图片资源，则该图片无实际权利所有人，或按照生成该图片的AI机构认定权利归属。" +
            "\n2. 本产品永不用于任何商业用途。若有图片作者认为本系列产品侵犯了其正当权益，请与接口提供商和开源项目负责人联系和交涉，思锐工作室及其用户不承担任何责任。" +
            "\n3. 本产品所生成的任何图片资源均来源于接口提供商和开源项目，由接口提供商和开源项目通过爬取或代理（《Pixiv 生成器》除外）网络图片资源并返回给本产品。若图片有任何不规范的内容，思锐工作室及其用户不承担任何责任，所有图片资源均来自于网络。" +
            "\n4. 请所有用户遵守以上须知并严格遵守中华人民共和国法律，不得用本产品进行任何非法活动或侵犯他人权利的活动，不得试图使用本产品生成任何不符合法律或规范的图片。" +
            "\n5. 本产品仅供学习交流和娱乐使用，所有保存的图片请于24小时内删除。" +
            "\n6. 变更通知：若本《声明》的内容有任何变更，恕我们不另行通知，请用户每次更新本产品后及时在 “关于” 中阅读本《声明》以获得最新信息。" +
            "\n\n思锐工作室 2024.1.28 思锐工作室保留所有权和最终解释权";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button2);
        Button button2 = findViewById(R.id.toggleButton);
        EditText editTextText = findViewById(R.id.editTextText1);
        NavigationView navigationView = findViewById(R.id.NavigationView);

        navigationView.setClickable(true);

        LinearLayout linearLayout = findViewById(R.id.LinearLayout1);

        TextView textModel = findViewById(R.id.textModel);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // 权限已授予
                // 在这里执行相关操作
            } else {
                // 权限被拒绝，可以根据需要处理
            }
        });

        ClarityConfig config = new ClarityConfig(
                "kz3hptw1lp",
                "", // Default user id (using empty string)
                LogLevel.None,
                false, // Disallow metered network usage
                true, // Enable web view capturing
                Collections.singletonList("*"), // Allowed domains
                ApplicationFramework.Native,
                Collections.emptyList(), // Allowed activities
                Collections.emptyList(), // Disallowed activities (ignore activities)
                false, // Disable on low-end devices
                null
        );

        Clarity.initialize(getApplicationContext(), config);

        textModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextText.setText("少女&白丝&萝莉");
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击事件响应代码
                System.out.println("111444555111444");

                NavigationHide();
            }
        });

        ScrollView scrollView = findViewById(R.id.scrollView);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // 获取当前滚动位置
                int scrollX = scrollView.getScrollX(); // 水平滚动位置
                int scrollY = scrollView.getScrollY(); // 垂直滚动位置

                System.out.println("111444555111444");

                NavigationHide();

                // 处理滚动事件
                // ...
            }
        });

        Button download_button = findViewById(R.id.download_button);
        download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("WPGenerator", MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("NonResponsible", true);
                if (isFirstTime) {

                    // 第一次打开程序的逻辑
                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                    builder.setTitle("免责声明");
                    builder.setMessage(NoResibonse);
                    builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 将isFirstTime设置为false，表示程序已经不是首次打开
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("NonResponsible", false);
                            editor.apply();

                            if (ContextCompat.checkSelfPermission(PixivGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                System.out.println("No Permission！");
                                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                ActivityCompat.requestPermissions(PixivGeneratorActivity.this,
                                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                        PERMISSION_REQUEST_CODE);
                            }

                            Start_generator();
                        }
                    });
                    builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了取消按钮，可以进行一些处理，如禁用相关功能等
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    builder.show();


                } else {
                    Start_generator();
                }


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHide();

            }
        });

        Button inforbutton = findViewById(R.id.download_button2);
        inforbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    ImageView imageView = findViewById(R.id.image_view);
                    Button button = findViewById(R.id.download_button2);

//                    Drawable drawable = imageView.getDrawable();
//                    int position = -1;
//
//                    for (int i = 0; i < drawables.size(); i++) {
//                        if (drawables.get(i).equals(drawable)) {
//                            position = i;
//                            break;
//                        }
//                    }

                    int position = ImagePosition;

                    if (position != -1) {
                        // drawable 存在于 drawables 列表中
                        AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                        builder.setTitle("Pixiv 作品信息")
                                .setMessage(button.getText() + "\n" + informations.get(position))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击确定按钮后的操作
                                    }
                                })
                                .setCancelable(false); // 不可取消对话框
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        // drawable 不存在于 drawables 列表中
                        // 在这里弹窗提示用户
                        AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);

                        if (drawables.isEmpty()) {
                            builder.setTitle("没有生成图片")
                                    .setMessage("请先生成图片哦o((>ω< ))o")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        } else {
                            builder.setTitle("图像列表被清除")
                                    .setMessage("无法读取当前图片，可能是图片未成功保存到相册。请重新生成，或反馈与交流群 367798007 。\n详细信息：第 " + position + " 张，列表有 " + drawables.toArray().length + " 张。")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                    builder.setTitle("图片展示失败")
                            .setMessage("无法读取当前图片，可能是由于图片没有正常显示。请前往相册查看，或重新生成图片。我们建议您一次性不要生成太多张图片，以缓解内存压力。\n详细信息：\n\n" + e.toString())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作
                                }
                            })
                            .setCancelable(false); // 不可取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }
        });

        Button LeftButton = findViewById(R.id.LeftButton);
        LeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ImageView imageView = findViewById(R.id.image_view);
                    Button button = findViewById(R.id.download_button2);

                    //                   Drawable drawable = imageView.getDrawable();
//                    int position = -1;
//
//                    for (int i = 0; i < drawables.size(); i++) {
//                        if (drawables.get(i).equals(drawable)) {
//                            position = i;
//                            break;
//                        }
//                    }
                    int position = ImagePosition;
                    if (position != -1) {
                        // drawable 存在于 drawables 列表中
                        int next = position - 1;
                        if (!(next < 0)) {
                            ImagePosition = next;
                            imageView.setImageDrawable(drawables.get(next));
//                            Glide.with(PixivGeneratorActivity.this)
//                                    .load(drawables.get(next))
//                                    .into(imageView);
                            button.setText("第 " + String.valueOf(next + 1) + "/" + drawables.toArray().length + " 张");
                            imageView.invalidate();
                        } else {
                            Toast toast = Toast.makeText(PixivGeneratorActivity.this, "已经是第一张了", Toast.LENGTH_SHORT);
                            // 显示 Toast 弹窗
                            toast.show();
                        }
                    } else {
                        // drawable 不存在于 drawables 列表中
                        // 在这里弹窗提示用户
                        AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);

                        if (drawables.isEmpty()) {
                            builder.setTitle("没有生成图片")
                                    .setMessage("请先生成图片哦o((>ω< ))o")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        } else {
                            builder.setTitle("图像列表被清除")
                                    .setMessage("无法读取上一张图片，可能是图片未成功保存到相册。请重新生成，或反馈与交流群 367798007 。\n详细信息：第 " + position + " 张，列表有 " + drawables.toArray().length + " 张。")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                    builder.setTitle("无法获取图像列表")
                            .setMessage("无法读取上一张图片，可能是由于图片没有正常显示。请前往相册查看，或重新生成图片。我们建议您一次性不要生成太多张图片，以缓解内存压力。\n详细信息：\n\n" + e.toString())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作
                                }
                            })
                            .setCancelable(false); // 不可取消对话框

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        Button RightButton = findViewById(R.id.RightButton);
        RightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ImageView imageView = findViewById(R.id.image_view);
                    Button button = findViewById(R.id.download_button2);

//                    Drawable drawable = imageView.getDrawable();
//                    int position = -1;
//
//                    for (int i = 0; i < drawables.size(); i++) {
//                        if (drawables.get(i).equals(drawable)) {
//                            position = i;
//                            break;
//                        }
//                    }

                    int position = ImagePosition;
                    if (position != -1) {
                        // drawable 存在于 drawables 列表中
                        int next = position + 1;
                        System.out.println("1. 下一张；2. 总量");
                        System.out.println(next);
                        System.out.println(drawables.toArray().length);
                        if (!(next >= Integer.valueOf(drawables.toArray().length))) {
                            ImagePosition = next;
                            imageView.setImageDrawable(drawables.get(next));
//                            Glide.with(PixivGeneratorActivity.this)
//                                    .load(drawables.get(next))
//                                    .into(imageView);
                            button.setText("第 " + String.valueOf(next + 1) + "/" + drawables.toArray().length + " 张");
                            imageView.invalidate();
                        } else {
                            Toast toast = Toast.makeText(PixivGeneratorActivity.this, "已经是最后一张了", Toast.LENGTH_SHORT);
                            // 显示 Toast 弹窗
                            toast.show();
                        }
                    } else {
                        // drawable 不存在于 drawables 列表中
                        // 在这里弹窗提示用户
                        AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);

                        if (drawables.isEmpty()) {
                            builder.setTitle("没有生成图片")
                                    .setMessage("请先生成图片哦o((>ω< ))o")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        } else {
                            builder.setTitle("图像列表被清除")
                                    .setMessage("无法读取下一张图片，可能是图片未成功保存到相册。请重新生成，或反馈与交流群 367798007 。\n详细信息：第 " + position + " 张，列表有 " + drawables.toArray().length + " 张。")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);

                    builder.setTitle("无法获取图像列表")
                            .setMessage("无法读取下一张图片，可能是由于图片没有正常显示。请前往相册查看，或重新生成图片。我们建议您一次性不要生成太多张图片，以缓解内存压力。\n详细信息：\n\n" + e.toString())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作
                                }
                            })
                            .setCancelable(false); // 不可取消对话

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个 TranslateAnimation 对象，使导航视图还原到初始位置
                LinearLayout ProgressLayout = findViewById(R.id.ProgressLayout);
                ProgressLayout.setVisibility(View.GONE);
                if (NavigationShowed == false) {
                    NavigationShowed = true;
                    // 创建逐渐显示的动画效果

                    TranslateAnimation animation = new TranslateAnimation(0, 0, 0, navigationView.getHeight());
                    animation.setDuration(300);
                    animation.setFillAfter(true);
                    animation.setInterpolator(new DecelerateInterpolator());

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // 动画开始时的操作
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 动画结束时的操作
                            navigationView.setEnabled(true); // 启用NavigationView的控件交互

                            // 获取 NavigationView 的布局参数
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) navigationView.getLayoutParams();

// 设置 marginTop 的值
                            int marginTop = 0; // 设置你需要的 marginTop 的值
                            layoutParams.topMargin = marginTop;

// 应用新的布局参数
                            navigationView.setLayoutParams(layoutParams);

                            // 清除动画
                            navigationView.clearAnimation();

                            PixivGeneratorActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // 动画重复时的操作
                        }
                    });

                    // 应用动画效果到导航视图
                    navigationView.startAnimation(animation);
                    navigationView.bringToFront();
                }

            }
        });


    }

    public void Test_start() {
        NavigationView navigationView = findViewById(R.id.NavigationView);

        LinearLayout ProgressLayout = findViewById(R.id.ProgressLayout);
        ProgressLayout.setVisibility(View.VISIBLE);


// 创建平移动画
        float dpValue = 75f; // 要转换的 dp 值
        float pxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());

        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, pxValue);
        animation.setDuration(300); // 设置动画持续时间为3秒
        animation.setFillAfter(true);
        animation.setInterpolator(new DecelerateInterpolator());

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时的操作
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束时的操作
                navigationView.setEnabled(true); // 启用NavigationView的控件交互

                // 更新布局参数的marginTop值
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) navigationView.getLayoutParams();

// 设置marginTop的值
                float dpValue1 = -357f; // 要转换的 dp 值
                float scale1 = getResources().getDisplayMetrics().density;
                int marginTop = (int) (dpValue1 * scale1 + 0.5f);

                layoutParams.topMargin = marginTop;
                navigationView.setLayoutParams(layoutParams);

                // 清除动画
                navigationView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复时的操作
            }
        });

// 应用动画效果到导航视图
        navigationView.startAnimation(animation);
        navigationView.bringToFront();
    }

    public void Test_End() {
        NavigationView navigationView = findViewById(R.id.NavigationView);

        LinearLayout ProgressLayout = findViewById(R.id.ProgressLayout);

        float dpValue = 75f; // 要转换的 dp 值
        float pxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());

        TranslateAnimation animation = new TranslateAnimation(0, 0, navigationView.getTranslationY(), -navigationView.getHeight());
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setInterpolator(new DecelerateInterpolator());

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时的操作
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                // 动画结束时的操作
                navigationView.setEnabled(true); // 启用NavigationView的控件交互

                // 更新布局参数的marginTop值
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) navigationView.getLayoutParams();

                float dpValue1 = -430f; // 要转换的 dp 值
                float scale1 = getResources().getDisplayMetrics().density;
                int marginTop = (int) (dpValue1 * scale1 + 0.5f);

                layoutParams.topMargin = marginTop;
                ProgressLayout.setVisibility(View.GONE);
                navigationView.setLayoutParams(layoutParams);

                // 清除动画
                navigationView.clearAnimation();
                // 动画结束时的操作
//                navigationView.setEnabled(true); // 启用NavigationView的控件交互
//
//                // 获取 NavigationView 的布局参数
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) navigationView.getLayoutParams();
//
//                float scale = getResources().getDisplayMetrics().density;
//                int marginTop = (int) (-navigationView.getHeight() * scale + 0.5f);
//                layoutParams.topMargin = marginTop;
//
//// 应用新的布局参数
//                navigationView.setLayoutParams(layoutParams);
//                ProgressLayout.setVisibility(View.GONE);
//                // 清除动画
//                navigationView.clearAnimation();
//
//                PixivGeneratorActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复时的操作
            }
        });

        navigationView.startAnimation(animation);
        navigationView.bringToFront();

    }

    public void Start_generator() {

        TextView ProgressText = findViewById(R.id.ProgressText);
        ProgressText.setText("正在浏览 Pixiv 准备");
        Test_start();

        String url = "https://api.lolicon.app/setu/v2?";
        EditText tag = findViewById(R.id.editTextText1);
        EditText uid = findViewById(R.id.editTextText);
        Switch R18 = findViewById(R.id.switch1);
        Switch AI = findViewById(R.id.switch2);
        Spinner number = findViewById(R.id.spinner);

        Object selectedObject = number.getSelectedItem();
        if (selectedObject != null) {
            // 如果有选中的项
            String GetNum = selectedObject.toString(); // 获取显示内容
            url = url + "num=" + GetNum + "&" ;
        } else {
            //  报错
            url = url + "num=1&" ;
        }

        if (!tag.getText().toString().trim().isEmpty()) {
            String text = tag.getText().toString();
            String[] splits = text.split("&");
            StringBuilder stringBuilder = new StringBuilder();
            for (String split : splits) {
                stringBuilder.append("tag=").append(split).append("&");
            }
            String result = stringBuilder.toString();
            System.out.println(result);

            url = url + result;
        }

        if (!uid.getText().toString().trim().isEmpty()) {
            url = url + "uid=" + uid.getText().toString() + "&" ;
        }

        if (R18.isChecked()) {
            url = url + "r18=2&" ;
        } else {
            url = url + "r18=0&" ;
        }

        if (AI.isChecked()) {
            url = url + "excludeAI=ture";
        } else {
            url = url + "excludeAI=false";
        }

        System.out.println(url);

        // 在主线程之外进行网络请求
        String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建 OkHttpClient 实例
                    OkHttpClient client = new OkHttpClient();
                    System.out.println("已创建");
                    // 创建请求对象
                    Request request = new Request.Builder()
                            .url(finalUrl)
                            .build();

                    // 发起请求并获取响应
                    Response response = client.newCall(request).execute();
                    System.out.println("发送了");

                    // 检查响应是否成功
                    if (!response.isSuccessful()) {
                        System.out.println("未成功");
                        AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                        if (ContextCompat.checkSelfPermission(PixivGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            System.out.println("No Permission！");
                            requestPermissionLauncher.launch(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            ActivityCompat.requestPermissions(PixivGeneratorActivity.this,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                    PERMISSION_REQUEST_CODE);
                        }
                        builder.setTitle("无法连接至 Pixiv API")
                                .setMessage("请检查网络权限是否给予，或更换网络，并在稍后重试。")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击确定按钮后的操作
                                    }
                                })
                                .setCancelable(false); // 不可取消对话框

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    // 获取响应数据
                    String jsonData = response.body().string();
                    try {
                        System.out.println("更新UI");
                        // 解析 JSON 数据
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        if (dataArray.length() > 0) {
                            System.out.println("载入中");
                            JSONObject dataObject = dataArray.getJSONObject(0);

                            drawables.clear();
                            informations.clear();
                            TextView ProgressText = findViewById(R.id.ProgressText);
                            List<String> originalUrls = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {

                                int finalI = i;
                                runOnUiThread(() -> ProgressText.setText("正在浏览 Pixiv " + String.valueOf(finalI +1) + "/" + dataArray.length()));
                                System.out.println(dataArray.length());
                                JSONObject item = dataArray.getJSONObject(i);
                                String Pid = String.valueOf(item.getInt("pid"));
                                String Uid = String.valueOf(item.getInt("uid"));
                                String Title = item.getString("title");
                                String Author = item.getString("author");
                                String Width = String.valueOf(item.getInt("width"));
                                String Height = String.valueOf(item.getInt("height"));
                                String Ext = item.getString("ext");
                                Long UploadDate = item.getLong("uploadDate");

                                Date date = new Date(UploadDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                                String UpDate = sdf.format(date);

                                String information = "作品PID：" + Pid + "\n作者UID：" + Uid + "\n作品标题：" + Title + "\n作者昵称：" + Author + "\n画幅（宽）" + Width + "\n画幅（高）" + Height + "\n作品文件类型：" + Ext + "\n上载时间：" + UpDate;
                                JSONObject urlsObject = item.getJSONObject("urls");
                                String originalUrl = urlsObject.getString("original").replace("i.pixiv.re", getResources().getString(R.string.proxy));

//String originalUrl = dataObject.getJSONObject("urls").getString("original").replace("i.pixiv.re", "pixiv.t.srinternet.top");

                                // 加载图片到 ImageView

                                System.out.println("准备保存");

                                String filename = originalUrl.substring(originalUrl.lastIndexOf("/") + 1);

                                // 新起线程进行网络请
                                Drawable drawable_image = null;
                                try {
                                    // 创建 URL 对象
                                    System.out.println("载入111");
                                    URL url1 = new URL(originalUrl);
                                    // 打开连接
                                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                                    // 设置请求方式为 GET
                                    connection.setRequestMethod("GET");
                                    // 获取响应码
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == HttpURLConnection.HTTP_OK) {
                                        // 获取输入流
                                        System.out.println("载入122");
                                        InputStream inputStream = connection.getInputStream();
                                        // 将输入流转换为 Drawable
                                        System.out.println("载入133");
                                        drawable_image = Drawable.createFromStream(inputStream, "image_name");
                                        drawables.add(drawable_image);
                                        saveImageToGallery(PixivGeneratorActivity.this, drawable_image, Title + "." + Ext);
                                        informations.add(information);
                                        // 将获取的 Drawable 对象应用到你的视图或者其他逻辑中
                                        System.out.println("载入14111");

                                        // 关闭输入流
                                        inputStream.close();
                                        System.out.println("载入完~~~~~");
                                    }
                                    // 关闭连接
                                    connection.disconnect();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                                            builder.setTitle("已浏览，但是无法载入")
                                                    .setMessage(e.getMessage() + "\n\n 请截图并反馈。")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // 点击确定按钮后的操作
                                                        }
                                                    })
                                                    .setCancelable(false); // 不可取消对话框

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    });
                                }

                                System.out.println("载入完了   " + originalUrl);
                                originalUrls.add(originalUrl);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Button download_button2 = findViewById(R.id.download_button2);
                                    ImageView yourImageView = findViewById(R.id.image_view);
                                    if (!(drawables.isEmpty())) {
                                        download_button2.setText("第 1/" + String.valueOf(drawables.toArray().length) + " 张");
                                        ImagePosition = 0;
                                        yourImageView.setImageDrawable(drawables.get(0));
//                                        Glide.with(PixivGeneratorActivity.this)
//                                                .load(drawables.get(0))
//                                                .into(yourImageView);
                                        yourImageView.invalidate();
                                    }
                                }
                            });

                        }  else {
                            System.out.println("没有数据");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                                    if (ContextCompat.checkSelfPermission(PixivGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        System.out.println("No Permission！");
                                        requestPermissionLauncher.launch(
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        ActivityCompat.requestPermissions(PixivGeneratorActivity.this,
                                                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                                PERMISSION_REQUEST_CODE);
                                    }
                                    builder.setTitle("已连接，但是无指定图片")
                                            .setMessage("该UID作者没有有符合指定标签的图片，请检查指定的作者UID和标签是否正确。\n若您没有指定作者UID和标签或您确保它们都是对的，请稍后再重试。")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // 点击确定按钮后的操作
                                                }
                                            })
                                            .setCancelable(false); // 不可取消对话框

                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (ContextCompat.checkSelfPermission(PixivGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            System.out.println("No Permission！");
                            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            ActivityCompat.requestPermissions(PixivGeneratorActivity.this,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                    PERMISSION_REQUEST_CODE);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                                builder.setTitle("发生未指定的致命错误")
                                        .setMessage(e.getMessage())
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // 点击确定按钮后的操作
                                            }
                                        })
                                        .setCancelable(false); // 不可取消对话框

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    // 在主线程中更新 UI

                } catch (IOException e) {
                    e.printStackTrace();
                    if (ContextCompat.checkSelfPermission(PixivGeneratorActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        System.out.println("No Permission！");
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        ActivityCompat.requestPermissions(PixivGeneratorActivity.this,
                                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                PERMISSION_REQUEST_CODE);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                            builder.setTitle("发生未指定的致命错误")
                                    .setMessage(e.getMessage())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作
                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Test_End();
                    }
                });

            }
        }).start();



    }

    public void settingsShow(View view) {



        if (drawables.isEmpty()) {
            AlertDialog.Builder builde = new AlertDialog.Builder(PixivGeneratorActivity.this);

            builde.setTitle("没有生成图片")
                    .setMessage("请先生成图片哦o((>ω< ))o")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击确定按钮后的操作b
                        }
                    })
                    .setCancelable(false); // 不可取消对话框

            AlertDialog dialog = builde.create();
            dialog.show();


        } else {



            AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View customView = inflater.inflate(R.layout.pixiv_settings, null);
            builder.setView(customView);

            ImageView NormalImage = findViewById(R.id.image_view);
            Button button = findViewById(R.id.download_button2);
            Button button1 = customView.findViewById(R.id.button_negative2);
            Button button2 = customView.findViewById(R.id.button_negative);
            Button button3 = customView.findViewById(R.id.button_negative3);
            TextView dialog_title = customView.findViewById(R.id.dialog_title);
            ImageView imageView_inside = customView.findViewById(R.id.imageView10);
            TextView dialog_message = customView.findViewById(R.id.dialog_message2);

            Glide.with(this)
                    .load(drawables.get(ImagePosition))
                    .into(imageView_inside);

            dialog_message.setText(button.getText());


            button1.setEnabled(true);
            AlertDialog dialog = builder.create();

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Button 1 的点击事件逻辑
                    if (setWallpaperFromImageView()) {
                        Toast toast = Toast.makeText(PixivGeneratorActivity.this, "已成功设置壁纸", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(PixivGeneratorActivity.this, "设置当前壁纸失败", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            button3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        Uri imageUri = FileProvider.getUriForFile(PixivGeneratorActivity.this, "com.pixiv.srinternet.fileprovider", saveBitmapToFile(drawableToBitmap(NormalImage.getDrawable())));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(shareIntent, "分享 Pixiv 图片"));
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(PixivGeneratorActivity.this, "无法分享当前图片", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });

            dialog.show();

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Button 2 的点击事件逻辑
                    // 在这里写下你希望 Button 2 点击后执行的代码
                    dialog.dismiss();
                }
            });
        }


    }

    public void showAbout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No Permission！");
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        }


        builder.setTitle("关于本程序")
                .setMessage("程序名称：Pixiv 生成器（壁纸生成器 系列插件）\n内部名称：Pixiv_of_Wallpaper_Generator\n版本：1.0 (壁纸生成器 3.1)\n内部版本：1.0 (of 3.1.New_Year_of_2024) \n制作：SR思锐（思锐工作室）\n发布：SR思锐（思锐工作室）\n依赖：Lolicon API\n代理：SRInternet 内置Pixiv代理\n开源与更新：https://github.com/SRInternet/Wallpaper-generator-for-Android（已停止开源）\n\n感谢您的使用！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的操作
                    }
                })
                .setNegativeButton("免责声明", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(PixivGeneratorActivity.this);
                        builder1.setTitle("免责声明");
                        builder1.setMessage(NoResibonse);
                        builder1.setPositiveButton("明白", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 将isFirstTime设置为false，表示程序已经不是首次打开
                                dialog.dismiss();
                            }
                        });
                        builder1.setCancelable(false); // 禁止点击对话框外部取消对话框
                        builder1.show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean setWallpaperFromImageView() {
        ImageView image_view = findViewById(R.id.image_view);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable drawable = image_view.getDrawable();

// 检查是否有正在显示的图片
        if (drawable == null) {
            return false;
        } else {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                // 如果是BitmapDrawable，则直接获取Bitmap
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else {
                // 如果是其他类型的Drawable（如VectorDrawable），则创建一个新的Bitmap并将Drawable绘制到其中
                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                bitmap = Bitmap.createBitmap(width, height, config);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }

            try {
                // 计算壁纸的宽度和高度
                int wallpaperWidth = wallpaperManager.getDesiredMinimumWidth();
                int wallpaperHeight = wallpaperManager.getDesiredMinimumHeight();

                // 计算图片的宽度和高度
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                // 计算目标宽度和高度，保留纵横比
                int targetWidth, targetHeight;
                if (imageWidth > imageHeight) {
                    targetWidth = wallpaperWidth;
                    targetHeight = (int) ((float) imageHeight / imageWidth * wallpaperWidth);
                } else {
                    targetHeight = wallpaperHeight;
                    targetWidth = (int) ((float) imageWidth / imageHeight * wallpaperHeight);
                }

                // 调整图片大小，保持纵横比不变
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
                // 设置壁纸
                wallpaperManager.setBitmap(resizedBitmap);

                return true;
            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }
    }

    // 将Drawable对象转换为Bitmap对象的方法
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    // 将Bitmap对象保存为临时文件的方法
    private File saveBitmapToFile(Bitmap bitmap) {
        File tempDir = new File(Environment.getExternalStorageDirectory(), "temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        String filename = "temp_image.jpg";
        File tempFile = new File(tempDir, filename);

        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    public void NavigationHide() {
        NavigationView navigationView = findViewById(R.id.NavigationView);

        if (NavigationShowed == true){
            NavigationShowed = false;

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏键盘
            Button button1 = findViewById(R.id.button2);
            inputMethodManager.hideSoftInputFromWindow(button1.getWindowToken(), 0);

            // 计算导航视图要滑动的距离
            int translationDistance = -navigationView.getHeight();

            // 创建一个 TranslateAnimation 对象，使导航视图向上滑动
            TranslateAnimation animation = new TranslateAnimation(0, 0, 0, translationDistance);
            animation.setDuration(300);
            animation.setFillAfter(true);
            animation.setInterpolator(new DecelerateInterpolator());

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // 动画开始时的操作
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // 动画结束时的操作
                    navigationView.setEnabled(true); // 启用NavigationView的控件交互

                    // 获取 NavigationView 的布局参数
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) navigationView.getLayoutParams();

                    float scale = getResources().getDisplayMetrics().density;
                    int marginTop = (int) (-navigationView.getHeight() * scale + 0.5f);
                    layoutParams.topMargin = marginTop;

// 应用新的布局参数
                    navigationView.setLayoutParams(layoutParams);

                    // 清除动画
                    navigationView.clearAnimation();

                    PixivGeneratorActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // 动画重复时的操作
                }
            });

            // 应用动画效果到导航视图
            navigationView.startAnimation(animation);
            navigationView.bringToFront();
        }
    }

    private void saveImageToGallery(Context context, Drawable drawable, String filename) {
        System.out.println("开始保存");
        try {
            Bitmap bitmap = null;
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof GifDrawable) {
                bitmap = ((GifDrawable) drawable).getFirstFrame();
            }
            if (bitmap != null) {
                System.out.println("可以保存");
                String displayName = filename;  // 自定义图片文件名
                // 获取文件扩展名
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(filename);
                // 根据文件扩展名获取 MIME 类型
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension); //"image/jpeg"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android Q 及以上版本
                    ContentResolver resolver = context.getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                    Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    try {
                        OutputStream fos = resolver.openOutputStream(uri);
                        if (fos != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Android Q 以下版本
                    String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                    File imageFile = new File(imageDir, displayName);
                    try {
                        FileOutputStream fos = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                        MediaScannerConnection.scanFile(context, new String[]{imageFile.getPath()}, null, null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("保存好了");
            }
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PixivGeneratorActivity.this);
                    builder.setTitle("不能存储一张或多张图片")
                            .setMessage(e.getMessage())
                            .setPositiveButton("明白", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

    }
}