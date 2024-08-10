package com.wallpaper.generator;

/*
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

// 引入OkHttp库


/*import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;
import android.os.Environment;
import java.io.FileOutputStream;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.widget.Toast;
import java.io.IOException;*/

import android.Manifest;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import com.android.application.R;
import android.provider.MediaStore;
import android.content.ContentValues;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;

import android.content.Intent;
/*import android.net.Uri;
import android.view.KeyEvent;*/
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.app.NotificationManager;
import android.app.NotificationChannel;

import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.wallpaper.signin.SignInActivity;

import androidx.core.view.GravityCompat;
import android.content.SharedPreferences;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.os.Looper;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.microsoft.clarity.Clarity;
import com.microsoft.clarity.ClarityConfig;
import com.microsoft.clarity.models.ApplicationFramework;
import com.microsoft.clarity.models.LogLevel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private Button downloadButton;
    private ImageView imageView;
    private long backButtonPressedTime = 0;
    private boolean isAppInBackground = true; // 标记应用是否在后台
    //private boolean completed = false;

    private boolean isAppInForeground = false;
    private volatile Uri imageUri;
    private volatile boolean stopUpdateProgress = false;
    private String url = "https://api.iw233.cn/api.php?sort=mp&type=json&num=1";
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private final String Version = "3.1.0";
    int PICK_IMAGE_REQUEST_CODE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 重要注释：代码中部分敏感字符串存储的xml文件未推送至存储库，请注意处理所有报错的地方
        // proxy：私有Pixiv反代服务器；cid：Microsoft Clarity 项目ID
        // GitHeaderName与GitPassport：代表GitHub私有令牌

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = findViewById(R.id.download_button);
        imageView = findViewById(R.id.image_view);

        ClarityConfig config = new ClarityConfig(
                getString(R.string.cid),
                null, // Default user id
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

        /*setContentView(R.layout.activity_main);*/

        // 弹出信息窗口

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // 权限已授予
                // 在这里执行相关操作
            } else {
                // 权限被拒绝，可以根据需要处理
            }
        });

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String currentDate = dateFormat.format(calendar.getTime());
//        SharedPreferences sharedPreferences2 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
//
//        String savedDate = sharedPreferences2.getString("currentDate", "0");
//
//        if (!savedDate.equals(currentDate)) {
//
//            SharedPreferences.Editor editor = sharedPreferences2.edit();
//            editor.putString("daliy_task_process", "0");
//            editor.apply();
//
//        }

        SharedPreferences sharedPreferences = getSharedPreferences("WPGenerator", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {

            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.clear();
            editor1.apply();

            // 第一次打开程序的逻辑
            showInContextUI();

            // 将isFirstTime设置为false，表示程序已经不是首次打开
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();
        }

        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myBroadcastReceiver, intentFilter);

        //showToastIfAppInBackground();

        // 在活动中获取侧边栏引用
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setClickable(true); // 设置抽屉视图可点击


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String json = "{\"type\": \"json\",\"num\": 1}";

                new DownloadImageTask().execute(url);/* , json*/
            }
        });

        Button toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    //drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                    navigationView.bringToFront();
                }
            }
        });

        Button Phone_button = findViewById(R.id.Phone_button);
        Phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.iw233.cn/api.php?sort=mp&type=json&num=1";

                ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayoutA);
                constraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.background));

                Toast.makeText(MainActivity.this, "将会生成 手机壁纸.", Toast.LENGTH_SHORT).show();
            }
        });

        Button AI_button = findViewById(R.id.AI_button);
        AI_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.iw233.cn/api.php?sort=top&type=json&num=1";

                ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayoutA);
                constraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.ai_background));

                Toast.makeText(MainActivity.this, "将会生成 AI推荐的壁纸.", Toast.LENGTH_SHORT).show();
            }
        });

        Button silverhair = findViewById(R.id.silver_hair);
        silverhair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.iw233.cn/api.php?sort=yin&type=json&num=1";

                ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayoutA);
                constraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.sign_background_blurring));

                Toast.makeText(MainActivity.this, "将会生成 银发女孩.", Toast.LENGTH_SHORT).show();

            }
        });

        Button chunjiede = findViewById(R.id.chunjiede);
        chunjiede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://api.iw233.cn/api.php?sort=iw233&type=json&num=1";

                ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayoutA);
                constraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.chaste));

                Toast.makeText(MainActivity.this, "已禁用所有其他图片模式.", Toast.LENGTH_SHORT).show();

            }
        });

        Button pixivb = findViewById(R.id.PixivBrowser);
        pixivb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PixivGeneratorActivity.class);
                startActivity(intent);

            }
        });

        Button About = findViewById(R.id.About);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAbout();
            }
        });

        Button Contact = findViewById(R.id.Contact);
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContact();
            }
        });

        Button Sponsor = findViewById(R.id.Sponsor);
        Sponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String htmlUrl = "https://afdian.net/a/srinternet";  // 要打开的HTML的URL
                openHTMLInBrowser(htmlUrl);
            }
        });

        TextView Event_text = findViewById(R.id.textView14);
        Event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.putExtra("IsFirst", "false");
                startActivity(intent);
            }
        });

        ImageView Event_Img = findViewById(R.id.imageView9);
        Glide.with(this)
                .load(R.drawable.find2)
                .into(Event_Img);
        Event_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
//                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
//                activityManager.getMemoryInfo(memoryInfo);
//
//                long availableMemory = memoryInfo.availMem;
//                long requiredMemory = 280351962; // 将 MB 转换为 byte
//
//                System.out.println("可用内存" + availableMemory);
//
//                if (availableMemory >= requiredMemory) {
//                    // 可用内存大于等于243MB
//                    // 执行相应的操作
//
//                } else {
//                    // 可用内存小于243MB
//                    // 执行相应的操作或提示用户
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("无法打开活动页") // 设置对话框标题
//                            .setMessage("内存不足。请释放内存，尽可能地使内存达到最低限制 267MB (280351962) 以确保程序不会闪退。") // 设置对话框内容
////                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int id) {
////                                    // 点击确定按钮后的逻辑处理
////                                }
////                            })
//                            .setNegativeButton("好的", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // 点击取消按钮后的逻辑处理
//                                    dialog.dismiss(); // 关闭对话框
//                                }
//                            });
//
//// 创建并显示对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                }

            }
        });

        Button btnCheckUpdate = findViewById(R.id.Update);
        btnCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForUpdate();
            }
        });

        // 获取 ImageView 实例
        ImageView imageView = findViewById(R.id.image_view);

// 定义图片资源数组
        int[] imageResources = new int[] {
                R.drawable.test_resourse1,
                R.drawable.test_resourse2,
                R.drawable.test_resourse3,
                R.drawable.test_resourse4,
                R.drawable.test_resourse5,
                R.drawable.test_resourse6
        };

// 生成一个随机数
        Random random = new Random();
        int randomIndex = random.nextInt(imageResources.length);

// 设置随机图片到 ImageView
        imageView.setImageResource(imageResources[randomIndex]);

    }

    public void reward_show(String reward_name, Boolean saving, int image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.reward_dialog, null);
        builder.setView(customView);

        Button button1 = customView.findViewById(R.id.button_negative2);
        Button button2 = customView.findViewById(R.id.button_negative);
        TextView dialog_title = customView.findViewById(R.id.dialog_title);
        ImageView imageView_inside = customView.findViewById(R.id.imageView10);
        TextView dialog_message = customView.findViewById(R.id.dialog_message2);

        Glide.with(this)
                .load(image)
                .into(imageView_inside);

        dialog_message.setText("前往活动页点击【立即签到】查看活动进度");

        if (saving == Boolean.FALSE) {
            dialog_title.setText(reward_name + " 未达成");
            button1.setTextColor(Color.parseColor("#a1a3a6"));
        } else {
            dialog_title.setText(reward_name + " 已达成");
            button1.setTextColor(Color.parseColor("#00BCD4"));
        }

        button1.setEnabled(saving);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Button 1 的点击事件逻辑
                // 在这里写下你希望 Button 1 点击后执行的代码

                // String htmlUrl = "https://www.123pan.com/s/2bLlVv-gJKph.html";  // 要打开的HTML的URL
                String savedImageURL = null;
                String savedImageURL1 = null;
                String savedImageURL2 = null;
                String savedImageURL3 = null;


                if (Objects.equals(reward_name, "五周年限定礼包")) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.third_day);
                    savedImageURL2 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, reward_name, reward_name);
                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.third_day1);
                    savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, reward_name, reward_name);
                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.third_day2);
                    savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap2, reward_name, reward_name);
                    Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.third_day3);
                    savedImageURL3 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap3, reward_name, reward_name);

                    if (savedImageURL != null && savedImageURL1 != null && savedImageURL2 != null && savedImageURL3 != null) {
                        Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
                    }

                } else if (Objects.equals(reward_name, "大图集")) {
                    String htmlUrl = "https://cloudreve.srinternet.top/s/lws0";  // 要打开的HTML的URL
                    openHTMLInBrowser(htmlUrl);
                } else {
                    Drawable drawable = imageView_inside.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                    savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, reward_name, reward_name);

                    if (savedImageURL != null) {
                        Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
                    }

//                    Drawable drawable = imageView.getDrawable();
//                    Drawable imageViewDrawable = imageView.getDrawable();
//                    Drawable embeddedDrawable = null;
//
//                    Field[] drawableFields = R.drawable.class.getFields();
//                    for (Field field : drawableFields) {
//                        try {
//                            int drawableResId = field.getInt(null);
//                            // 在这里可以对获取到的 drawable 资源进行处理
//                            // 比如打印资源名称或执行其他操作
//                            String drawableName = getResources().getResourceName(drawableResId);
//                            Drawable resourceDrawable = getDrawable(drawableResId);
//
//                            // 比较 ImageView 中的 Drawable 和资源中的 Drawable
//                            if (imageViewDrawable.equals(resourceDrawable)) {
//                                embeddedDrawable = resourceDrawable;
//                                break;
//                            }
//                            // System.out.println(drawableName);
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                    Bitmap bitmap = ((BitmapDrawable) embeddedDrawable).getBitmap();
//
//                    savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, reward_name, reward_name);
//
//                    if (savedImageURL != null) {
//                        Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
//                    }

                }

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Button 2 的点击事件逻辑
                // 在这里写下你希望 Button 2 点击后执行的代码
                dialog.dismiss();
            }
        });
    }


    private void showInContextUI() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("壁纸生成器 正在获取权限");
        builder.setMessage("描述：程序需要读取读写您的手机的内部存储以保存生成的图片。若您不同意，则可能导致图片无法正常保存至相册，甚至导致程序闪退。");
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户点击了确定按钮，继续请求权限
                System.out.println("没有权限！");
                requestPermissionLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                System.out.println("没有权限1213！");
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        PERMISSION_REQUEST_CODE);
                System.out.println("没有权限56546！");
                showDialog();
            }
        });
        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户点击了取消按钮，可以进行一些处理，如禁用相关功能等
                dialog.dismiss();
                showDialog();
            }
        });
        builder.setCancelable(false); // 禁止点击对话框外部取消对话框
        builder.show();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
            System.out.println("No Permission！");
        }
        builder.setTitle(getString(R.string.welcomeTitle))
                .setMessage("欢迎使用 壁纸生成器 " + Version + " ！" + getString(R.string.welcome))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的操作
//                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//                        intent.putExtra("IsFirst", "true");
//                        startActivity(intent);
                    }
                })
                .setCancelable(false); // 不可取消对话框

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    private void showWarning() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("No Permission！");
//            requestPermissionLauncher.launch(
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            ActivityCompat.requestPermissions(this,
//                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
//                    PERMISSION_REQUEST_CODE);
//        }
//        builder.setTitle("未解锁")
//                .setMessage("您还未解锁 “银发” 类型的壁纸，请坚持签到，您将很快地解锁这个类型！")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击确定按钮后的操作
//                    }
//                })
//                .setCancelable(false); // 不可取消对话框
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No Permission！");
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        }
        builder.setTitle("更多设置和功能")
                .setMessage("关于本程序\n程序名称：壁纸生成器手机版\n内部名称：Wallpaper_Generator_for_Android\n版本：" + Version + "\n内部版本：3.1 The Special 5 anniversary \n制作：SR思锐（思锐工作室）\n发布：SR思锐（思锐工作室）\n依赖：MirlKoi-API Lolicon-API\n开源与更新：https://github.com/SRInternet/Wallpaper-generator-for-Android\n\n感谢您的使用！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的操作
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("指定更改壁纸", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 用户点击取消按钮的逻辑处理
                        Intent pickIntent = new Intent(Intent.ACTION_PICK);
                        pickIntent.setType("image/*");
                        PICK_IMAGE_REQUEST_CODE = 1;
                        startActivityForResult(pickIntent, PICK_IMAGE_REQUEST_CODE);

                    }
                })
                .setCancelable(true); // 不可取消对话框

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No Permission！");
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        }
        builder.setTitle("如何联系我们")
                .setMessage("官方邮箱：srinternet@qq.com\n官方QQ群：367798007\n官方抖音号：SRInternet.Sr\n官方 Bilibili：SR思锐Offical（可能会变更）")
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

    private void openHTMLInBrowser(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);

        // 检查是否有应用程序可以处理打开链接的意图
        /*if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // 没有找到可以处理打开链接的应用程序
            Toast.makeText(this, "没有找到可以打开链接的应用程序", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void showConfirmDialog(View view) {
        ImageView image_view = findViewById(R.id.image_view);
        Drawable drawable = image_view.getDrawable();

        // 检查是否有正在显示的图片
        if (drawable != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("要将这张图设置为手机壁纸吗？")
                    .setMessage("设置壁纸需要一段时间，请不要关闭程序。")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*// 用户点击确认按钮的逻辑处理
                            // 创建一个 AlertDialog.Builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setCancelable(false); // 设置为不可取消

                            // 设置对话框显示的布局和消息
                            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                            View dialogView = inflater.inflate(R.layout.progress_dialog, null);
                            builder.setView(dialogView);

                            // 创建对话框并显示
                            AlertDialog progressDialog = builder.create();
                            progressDialog.show();

                            // 创建一个新的线程
                            dialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在这里执行耗时操作，例如加载数据或执行网络请求
                                    boolean completed = setWallpaperFromImageView(); // 修改此处，添加布尔变量completed声明

                                    // 使用 Handler 在新线程中更新 UI
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 在主线程中隐藏进度条
                                            progressDialog.dismiss();

                                            // 执行其他更新 UI 的操作
                                            if (completed == true) {
                                                Toast.makeText(MainActivity.this, "成功 设置壁纸", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(MainActivity.this, "无法 设置壁纸", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).start();*/


                            boolean completed = setWallpaperFromImageView(null);

                            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                            String big_voyage = sharedPreferences1.getString("big_voyage", "0");

                            int process = Integer.parseInt(big_voyage);
                            int now_process = process + 1;

                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putString("big_voyage", Integer.toString(now_process));
                            editor.apply();

                            if (completed) {
                                Toast.makeText(MainActivity.this, "成功 设置壁纸", Toast.LENGTH_SHORT).show();

                                if (now_process >= 5) {
                                    if (!IsRewardShowed("【大航海！】勋章")) {
                                        reward_show("【大航海！】勋章", Boolean.TRUE, R.drawable.bigvoyage);
                                        RewardShowed("【大航海！】勋章");
                                    };
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "无法 设置壁纸", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 用户点击取消按钮的逻辑处理
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean setWallpaperFromImageView(Drawable input) {
        ImageView image_view = findViewById(R.id.image_view);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable drawable;
        if (input == null) {
            drawable = image_view.getDrawable();
        } else {
            drawable = input;
        };

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

    private class DownloadImageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            /*String json = params[1];*/
            String imageUrl = "";

            try {
                // 发起GET请求，获取图片链接

                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setDoInput(true);

                /*connection.getOutputStream().write(json.getBytes());*/

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    StringBuilder response = new StringBuilder();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        response.append(new String(buffer, 0, len));
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        imageUrl = jsonObject.optString("pic");
                        imageUrl = imageUrl.replace("\\/", "/");
                        imageUrl = imageUrl.replace("[", "");
                        imageUrl = imageUrl.replace("]", "");
                        imageUrl = imageUrl.replaceAll("\"", "");
// 添加协议部分
                        /*imageUrl = "https://" + imageUrl;*/
                        System.out.println("imageUrl: " + imageUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imageUrl;
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // 保存图片到缓存并显示在ImageView
                new SaveImageTask().execute(imageUrl);
            } else {
                Toast.makeText(MainActivity.this, "无法获取图片，请检查网络.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SaveImageTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            // 在异步任务执行之前，在主线程中调用此方法

            // 显示正在生成的Toast消息
            Toast.makeText(MainActivity.this, "正在生成，请等待", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String daily_task1 = sharedPreferences1.getString("daliy_task", "0");

            int process11 = Integer.parseInt(daily_task1);
            int now_process = process11 + 1;

            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("daliy_task", Integer.toString(now_process));
            editor.apply();
        }
        @Override
        protected Void doInBackground(String... params) {
            //Toast.makeText(MainActivity.this, "正在保存...", Toast.LENGTH_SHORT).show();
            System.out.println("in this step");
            stopUpdateProgress = false;
            String imageUrl = params[0];
            Bitmap bitmap = null;
            // 检查当前设备的API级别
            String channelId = "Progress";
            String channelId_1 = "Saved";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 创建通知通道

                CharSequence channelName = "进度显示"; // 设置通道名称
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);

                // 设置其他通道属性
                // ...

                // 注册通知通道
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(notificationChannel);

                CharSequence channelName_1 = "已保存通知"; // 设置通道名称
                int importance_1 = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel_1 = new NotificationChannel(channelId_1, channelName_1, importance_1);

                // 设置其他通道属性
                // ...

                // 注册通知通道
                NotificationManager notificationManager_1 = getSystemService(NotificationManager.class);
                notificationManager_1.createNotificationChannel(notificationChannel_1);
            }

// 创建通知
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, channelId)
                    .setSmallIcon(R.mipmap.ic_notification)
                    .setContentTitle("正在获取图片")
                    .setProgress(100, 0, false) // 设置初始进度为0
                    .setOngoing(true);

// 发送通知
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.notify(1, builder.build());


            // saveImageThread

            // 在这里执行保存图片的操作

            // 启动 updateProgressThread
            Thread updateProgressThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 模拟保存图片的耗时操作和进度更新
                    int progress = 0;
                    builder.setPriority(NotificationCompat.PRIORITY_LOW);
                    while (progress < 100 && !stopUpdateProgress) {
                        try {
                            System.out.println("add");
                            // 模拟每次保存图片的耗时操作
                            Thread.sleep(100);
                            progress += 1; // 假设每次循环增加10%的进度

                            // 更新通知栏进度条
                            builder.setProgress(100, progress, false);
                            notificationManager.notify(1, builder.build());

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            updateProgressThread.start();

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();

                }

            if (bitmap != null) {
                //Toast.makeText(MainActivity.this, "正在生成，请等待", Toast.LENGTH_LONG).show();


                try {
                    Thread.sleep(1000); // 休眠1秒 (1000毫秒)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                saveBitmapToGallery(bitmap);
                builder.setProgress(100, 100, false);
                stopUpdateProgress = true;
                try {
                    updateProgressThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                notificationManager.cancel(1);


                NotificationCompat.Builder builder_1 = new NotificationCompat.Builder(MainActivity.this, channelId_1)
                        .setSmallIcon(R.mipmap.ic_notification)
                        .setContentTitle("已保存")
                        .setOngoing(false);
                notificationManager.notify(2, builder_1.build());

                // 停止 updateProgressThread

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "图片已保存。", Toast.LENGTH_SHORT).show();

            //单日任务判断检测
            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String daily_task = sharedPreferences1.getString("daliy_task", "0");

            int process = Integer.parseInt(daily_task);

            if (process >= 5 && process < 20) {
                if (!IsRewardShowed("【初阶二次元】勋章")) {
                    reward_show("【初阶二次元】勋章", Boolean.TRUE, R.drawable.fstage);
                    RewardShowed("【初阶二次元】勋章");
                };
            } else if (process >= 20 && process < 50) {
                if (!IsRewardShowed("【萝莉控！】勋章")) {
                    reward_show("【萝莉控！】勋章", Boolean.TRUE, R.drawable.lolicon);
                    RewardShowed("【萝莉控！】勋章");
                }

            } else if (process >= 50 && process < 100) {
                if (!IsRewardShowed("【一定有其他目的】勋章")) {
                    reward_show("【一定有其他目的】勋章", Boolean.TRUE, R.drawable.otherpurpose);
                    RewardShowed("【一定有其他目的】勋章");
                }

            } else if (process >= 100) {
                if (!IsRewardShowed("【最喜欢啦！】勋章")) {
                    reward_show("【最喜欢啦！】勋章", Boolean.TRUE, R.drawable.bestlove);
                    RewardShowed("【最喜欢啦！】勋章");
                }

            } else {
                // Nothing here
            }

            if (Objects.equals(url, "https://api.iw233.cn/api.php?sort=yin&type=json&num=1")) {
                String white_hair = sharedPreferences1.getString("white_hair", "0");

                int process2 = Integer.parseInt(white_hair);
                int now_process1 = process2 + 1;

                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("white_hair", Integer.toString(now_process1));
                editor.apply();

                if (now_process1 >= 50) {
                    if (!IsRewardShowed("【白毛什么的我最喜欢啦】勋章")) {
                        reward_show("【白毛什么的我最喜欢啦】勋章", Boolean.TRUE, R.drawable.whitehair);
                        RewardShowed("【白毛什么的我最喜欢啦】勋章");
                    };
                }

            }

            if (Objects.equals(url, "https://api.iw233.cn/api.php?sort=iw233&type=json&num=1")) {
                String chunjie = sharedPreferences1.getString("chunjie", "0");

                int process1 = Integer.parseInt(chunjie);
                int now_process = process1 + 1;

                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("chunjie", Integer.toString(now_process));
                editor.apply();

                if (now_process >= 25) {
                    if (!IsRewardShowed("【我还很纯洁呢~】勋章")) {
                        reward_show("【我还很纯洁呢~】勋章", Boolean.TRUE, R.drawable.pcsucaii);
                        RewardShowed("【我还很纯洁呢~】勋章");
                    };
                }

            }


        }
    }

    private void saveBitmapToGallery(Bitmap bitmap) {

        System.out.println("in this step too");
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No Permission！");
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE);
        } else {
            Context context = this;
            // 在这里执行保存图片的操作
            String fileName = System.currentTimeMillis() + ".jpg";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                ContentResolver resolver = context.getContentResolver();
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (imageUri != null) {
                    try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String imagePath = pictureDirectory + "/" + fileName;

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, imagePath);

                ContentResolver resolver = context.getContentResolver();
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (imageUri != null) {
                    try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
// 通知相册更新
/*            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File(imageUri.getPath()));
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);*/
            // 通知相册更新
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(imageUri);
            sendBroadcast(mediaScanIntent);


            // 从相册中查询最新添加的图片
            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            String sortOrder = MediaStore.Images.Media._ID + " DESC";

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Cursor cursor = null;
                    try {
                        cursor = getContentResolver().query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                projection,
                                null,
                                null,
                                sortOrder);

                        if (cursor != null && cursor.moveToFirst()) {
                            int idColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                            if (idColumnIndex != -1 && pathColumnIndex != -1) {
                                final String imagePath = cursor.getString(pathColumnIndex);
                                cursor.close();

                                // 在主线程中更新 UI 视图
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 使用路径加载图片到 ImageView
                                        File imageFile = new File(imagePath);
                                        if (imageFile.exists()) {
                                            Uri imageUrl = Uri.fromFile(imageFile);
                                            Glide.with(MainActivity.this)
                                                    .load(imageUrl)
                                                    .into(imageView);
                                            //imageView.setImageURI(imageUrl);
                                        }
                                    }
                                });
                            } else {
                                // 列名不存在时的处理逻辑
                            }
                        }
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                }
            });

            thread.start();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，执行操作
                // 执行操作
                /*System.out.println("in the step");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                //
                ContentResolver resolver = getContentResolver();
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //
                if (imageUri != null) {
                    try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                // 从相册中查询最新添加的图片
                String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                String sortOrder = MediaStore.Images.Media._ID + " DESC";

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    projection,
                                    null,
                                    null,
                                    sortOrder);

                            if (cursor != null && cursor.moveToFirst()) {
                                int idColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                int pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                                if (idColumnIndex != -1 && pathColumnIndex != -1) {
                                    final String imagePath = cursor.getString(pathColumnIndex);
                                    cursor.close();

                                    // 在主线程中更新 UI 视图
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // 使用路径加载图片到 ImageView
                                            File imageFile = new File(imagePath);
                                            if (imageFile.exists()) {
                                                Uri imageUrl = Uri.fromFile(imageFile);
                                                imageView.setImageURI(imageUrl);
                                            }
                                        }
                                    });
                                } else {
                                    // 列名不存在时的处理逻辑
                                }
                            }
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                });

                thread.start();*/
            } else {
                // 权限被拒绝，无法执行操作
            }
        }
    }
    //}

    private void saveImageToGallery() {
        String imagePath = Environment.getExternalStorageDirectory().getPath() + "/YourAppName";
        String fileName = "your_image.jpg";
        File imageFile = new File(imagePath, fileName);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Toast.makeText(MainActivity.this, "图片成功地保存在了相册中.", Toast.LENGTH_SHORT).show();
    }

    private Drawable loadImageFromUri(Uri imageUri) {
        try {
            // 从 Uri 获取输入流
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            // 将输入流解码为 Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // 将 Bitmap 转换为 Drawable
            return new BitmapDrawable(getResources(), bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            // 获取选中的图片的 Uri
            Uri imageUri = data.getData();
            // 将 Uri 转换为 Drawable
            Drawable selectedImageDrawable = loadImageFromUri(imageUri);
            // 在这里使用返回的 Drawable 进行后续操作
            // ...

            boolean completed = setWallpaperFromImageView(selectedImageDrawable);

            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String big_voyage = sharedPreferences1.getString("big_voyage", "0");

            int process = Integer.parseInt(big_voyage);
            int now_process = process + 1;

            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("big_voyage", Integer.toString(now_process));
            editor.apply();

            if (completed) {
                Toast.makeText(MainActivity.this, "成功 设置壁纸", Toast.LENGTH_SHORT).show();

                if (now_process >= 5) {
                    if (!IsRewardShowed("【大航海！】勋章")) {
                        reward_show("【大航海！】勋章", Boolean.TRUE, R.drawable.bigvoyage);
                        RewardShowed("【大航海！】勋章");
                    };
                }

            } else {
                Toast.makeText(MainActivity.this, "无法 设置壁纸", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        // 按下返回键时的时间戳
        long currentTime = System.currentTimeMillis();

        // 判断时间间隔是否小于2秒
        if (currentTime - backButtonPressedTime < 2000) {
            super.onBackPressed(); // 执行默认的返回键操作，退出应用程序
            finish();
            System.exit(0);
        } else {
            backButtonPressedTime = currentTime; // 更新按下返回键的时间戳
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppInBackground = false; // 当Activity启动时，应用处于前台
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppInForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppInForeground = false;

        //showToastIfAppInBackground();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 检查应用是否进入后台
        if (!isChangingConfigurations()) {
            isAppInBackground = true; // 当Activity停止时，应用进入后台
        }
    }

    private void showToastIfAppInBackground() {
        if (!isAppInForeground) {
            Toast.makeText(this, "壁纸生成器 将在后台保持运行，但生成将被停止。", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "return to background", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkForUpdate() {
        OkHttpClient client = new OkHttpClient(); // 创建 OkHttp 客户端
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/SRInternet/Wallpaper-generator-for-Android/releases") // 替换成你的仓库信息
                .addHeader(getString(R.string.GitHeaderName), getString(R.string.GitPassport)) // 添加 Authorization 标头
                .build();

        client.newCall(request).enqueue(new Callback() { // 异步发送 HTTP 请求
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONArray releasesArray = new JSONArray(responseData);
                        if (releasesArray.length() > 0) {
                            JSONObject latestRelease = releasesArray.getJSONObject(0); // 获取最新发布的版本
                            String latestVersion = latestRelease.getString("tag_name"); // 获取最新版本号
                            String Text = latestRelease.getString("body"); // 获取最新版本描述
                            String html_url = latestRelease.getString("html_url"); // 获取最新版本仓库链接

                            String browserDownloadUrl = null;
                            JSONArray assets = latestRelease.getJSONArray("assets"); // 获取最新版本下载链接

                            if (assets.length() > 0) {
                                // 遍历 assets 数组，筛选出目标文件的 browser_download_url
                                for (int i = 0; i < assets.length(); i++) {
                                    JSONObject asset = assets.getJSONObject(i);
                                    browserDownloadUrl = asset.getString("browser_download_url");
                                    // 进行其他操作，比如打印或存储
                                    System.out.println("目标文件的 browser_download_url: " + browserDownloadUrl);
                                }
                            } else {
                                System.out.println("没有找到目标文件。");
                            }
                            compareVersions(latestVersion, Text, html_url, browserDownloadUrl); // 比较版本号
                        } else {
                            // 没有发布的版本
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                System.out.println("No Permission！");
                                requestPermissionLauncher.launch(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                        PERMISSION_REQUEST_CODE);
                            }

                            Looper.prepare(); // 添加此行

                            builder.setTitle("找不到版本")
                                    .setMessage("请稍后重试，或向开发者反馈。\n您可以在“联系我们”中查看与开发者们取得联系。")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 点击确定按钮后的操作

                                        }
                                    })
                                    .setCancelable(false); // 不可取消对话框

                            AlertDialog dialog = builder.create();
                            dialog.show();

                            Looper.loop(); // 添加此行

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 请求失败
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        System.out.println("No Permission！");
                        requestPermissionLauncher.launch(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                PERMISSION_REQUEST_CODE);
                    }

                    Looper.prepare(); // 添加此行

                    builder.setTitle("已连接，但是请求失败")
                            .setMessage("请稍后重试，或前往Github仓库手动检查更新。\n您可以在“关于本程序”中查看Github仓库信息。")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作

                                }
                            })

                            .setCancelable(true); // 不可取消对话框

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Looper.loop(); // 添加此行
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 网络请求失败
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("No Permission！");
                    requestPermissionLauncher.launch(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                            PERMISSION_REQUEST_CODE);
                }

                Looper.prepare(); // 添加此行

                builder.setTitle("无网络，或连接失败")
                        .setMessage("无法连接到服务器，请检查设备的网络状态，以及程序的网络请求权限是否已经给予，并在稍后重试。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作

                            }
                        })
                        .setCancelable(false); // 不可取消对话框

                AlertDialog dialog = builder.create();
                dialog.show();

                Looper.loop(); // 添加此行
            }
        });
    }

    public boolean IsRewardShowed(String reward_name){
        SharedPreferences sharedPreferences1 = getSharedPreferences("Showed_rewards", Context.MODE_PRIVATE);
        String if_get = sharedPreferences1.getString(reward_name, "False");

        boolean boolValue = Boolean.parseBoolean(if_get);

        return boolValue;
    }

    public void RewardShowed(String reward_name){
        SharedPreferences sharedPreferences1 = getSharedPreferences("Showed_rewards", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString(reward_name, "True");
        editor.apply();
    }

    public void compareVersions(String latestVersion, String Text, String html_url, String browserDownloadUrl) {
        String currentVersion = Version;  // 替换成你实际的版本获取方式
        currentVersion = currentVersion.replaceAll("[^\\d.]+", "");
        System.out.println(currentVersion);

        latestVersion = latestVersion.replaceAll("[^\\d.]+", "");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.update_dialog, null);
        builder.setView(customView);

        ImageView icon = customView.findViewById(R.id.imageView10);

        icon.setImageResource(R.mipmap.ic_have_update);

        Button button1 = customView.findViewById(R.id.button_positive);
        Button button2 = customView.findViewById(R.id.button_negative);
        Button button3 = customView.findViewById(R.id.button_update);

        TextView DialogTitle = customView.findViewById(R.id.dialog_title);
        TextView DialogMessage = customView.findViewById(R.id.dialog_message);
        TextView DialogMessage2 = customView.findViewById(R.id.dialog_message2);

        DialogMessage2.setText("    当前版本：" + currentVersion);

        // 进行版本对比，判断是否需要更新
        if (latestVersion.compareTo(currentVersion) > 0) {
            // 需要更新，弹出更新对话框或其他相应操作

            DialogTitle.setText("发现新版本！");
            DialogMessage.setText("    最新版本：" + latestVersion + "\n\n" + Text);

            Looper.prepare(); // 添加此行

            final AlertDialog dialog = builder.create();
            dialog.show();

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String htmlUrl = html_url;  // 要打开的HTML的URL
                    openHTMLInBrowser(htmlUrl);
                    dialog.dismiss();
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String htmlUrl = browserDownloadUrl;  // 要打开的HTML的URL
                    openHTMLInBrowser(htmlUrl);
                    dialog.dismiss();
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Looper.loop(); // 添加此行


        } else {
            // 已是最新版本
            if (Objects.equals(Text, "")) {
                DialogTitle.setText("有新版本准备中➪");
                DialogMessage.setText("有一个更新正在排队等待发布。请稍作等待，新的更新或将在几天之内正式发布。\n若要在此时获取新的更新，您可以加入我们的开发者联系或加入交流群参与内测哦\nヾ(≧∪≦*)ノ〃");
            } else {
                DialogTitle.setText("无更新版本");
                DialogMessage.setText("本版本更新信息：\n" + Text);
            }

            button3.setTextColor(Color.parseColor("#808080"));
            button2.setText("确定");

            Looper.prepare(); // 添加此行

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String htmlUrl = html_url;  // 要打开的HTML的URL
                    openHTMLInBrowser(htmlUrl);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Nothing there
                }
            });

            final AlertDialog dialog = builder.create();
            dialog.show();

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Looper.loop(); // 添加此行
        }
    }
/*    public boolean isAppInBackground() {
        return isAppInBackground;
    }*/


}