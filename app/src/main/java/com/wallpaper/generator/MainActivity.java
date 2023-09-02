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
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import android.app.Notification;
import android.media.RingtoneManager;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import android.content.SharedPreferences;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import androidx.core.content.FileProvider;
import android.view.LayoutInflater;
import android.os.Handler;
import android.os.Looper;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private Button downloadButton;
    private ImageView imageView;
    private long backButtonPressedTime = 0;
    private boolean isAppInBackground = true; // 标记应用是否在后台
    private boolean completed = false;

    private boolean isAppInForeground = false;
    private volatile Uri imageUri;
    private volatile boolean stopUpdateProgress = false;
    private String url = "https://api.iw233.cn/api.php?sort=mp&type=json&num=1";




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = findViewById(R.id.download_button);
        imageView = findViewById(R.id.image_view);


        /*setContentView(R.layout.activity_main);*/

        // 弹出信息窗口
        SharedPreferences sharedPreferences = getSharedPreferences("Wallpaper_Generator", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            // 第一次打开程序的逻辑
            showDialog();

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


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        builder.setTitle("欢迎 Welcome")
                .setMessage("欢迎使用 壁纸生成器手机版 2.0.0 ！\n本次更新主要新增了侧边栏功能项，现已可以生成不同类型的壁纸，快去试试吧！\n若有任何问题，请及时反馈，邮箱：srinternet@qq.com\n欢迎加入QQ群 367798007 与我们交流！\n\n 此消息只显示一次，请认真阅读。")
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

    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        builder.setTitle("关于本程序")
                .setMessage("程序名称：壁纸生成器手机版\n内部名称：Wallpaper_Generator_for_Android\n版本：2.0.0\n内部版本：2 Public\n制作：SR思锐（思锐工作室）\n发布：SR思锐（思锐工作室）\n依赖接口：MirlKoi API\n开源与更新：https://github.com/SRInternet/Wallpaper-generator-for-Android\n\n感谢您的使用！")
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

    private void showContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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


                            boolean completed = setWallpaperFromImageView();

                            if (completed == true) {
                                Toast.makeText(MainActivity.this, "成功 设置壁纸", Toast.LENGTH_SHORT).show();
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
        }
    }

    private void saveBitmapToGallery(Bitmap bitmap) {

        System.out.println("in this step too");
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
// 创建通知
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setSmallIcon(R.mipmap.ic_notification)
                    //.setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentTitle("正在保存图片")
                    .setProgress(0, 0, true)
                    .setOngoing(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId("channel_id");

            }
            *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }*//*

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.notify(1, builder.build());

// 执行操作
            String fileName = System.currentTimeMillis() + ".jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            ContentResolver resolver = getContentResolver();
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (imageUri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

// 更新通知
            builder.setProgress(0, 0, false)
                    .setContentTitle("保存完成")
                    .setOngoing(false);
            notificationManager.notify(1, builder.build());*/



            // 在这里执行保存图片的操作
            String fileName = System.currentTimeMillis() + ".jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            } else {
                String pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String imagePath = pictureDirectory + "/" + fileName;

                values.put(MediaStore.Images.Media.DATA, imagePath);
            }
            ContentResolver resolver = getContentResolver();
                // 对imageUri进行写入操作
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (imageUri != null) {
                try (OutputStream outputStream = resolver.openOutputStream(imageUri)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
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
/*    public boolean isAppInBackground() {
        return isAppInBackground;
    }*/


}