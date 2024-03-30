package com.wallpaper.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.application.R;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Objects;

import android.content.SharedPreferences;
import android.widget.Button;
import android.text.TextUtils;
import android.provider.MediaStore;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.microsoft.clarity.Clarity;
import com.microsoft.clarity.ClarityConfig;
import com.microsoft.clarity.models.ApplicationFramework;
import com.microsoft.clarity.models.LogLevel;

public class SignInActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private int medals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

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

        Intent intent = getIntent();
        String IsFirst = intent.getStringExtra("IsFirst");

        Synchronous();

        ImageView title_img = findViewById(R.id.imageView);
        ImageView first_img = findViewById(R.id.imageView2);
        ImageView second_img = findViewById(R.id.imageView3);
        ImageView third_img = findViewById(R.id.imageView4);
        ImageView foruth_img = findViewById(R.id.imageView5);
        ImageView fifth_img = findViewById(R.id.imageView6);
        ImageView sixth_img = findViewById(R.id.imageView8);
        ImageView seventh_img = findViewById(R.id.imageView9);
        ImageView eighth_img = findViewById(R.id.imageView10);
        ImageView ninth_img = findViewById(R.id.imageView11);
        ImageView tenth_img = findViewById(R.id.imageView12);
        ImageView th_img11 = findViewById(R.id.imageView13);
        ImageView th_img12 = findViewById(R.id.imageView14);
        ImageView img13 = findViewById(R.id.imageView15);
        ImageView img14 = findViewById(R.id.background_image);

        int image = R.drawable.find2;
        int image1 = R.drawable.first_day;
        int image2 = R.drawable.second_day;
        int image3 = R.drawable.third_day;
        int image4 = R.drawable.shoot;
        int image5 = R.drawable.card100;
        int image6 = R.drawable.fstage;
        int image7 = R.drawable.lolicon;
        int image8 = R.drawable.otherpurpose;
        int image9 = R.drawable.bestlove;
        int image10 = R.drawable.bigvoyage;
        int image11 = R.drawable.pcsucaii;
        int image12 = R.drawable.whitehair;
        int image13 = R.drawable.last;
        int image14 = R.drawable.put_background;

        Glide.with(this)
                .load(image)
                .into(title_img);

        Glide.with(this)
                .load(image1)
                .into(first_img);

        Glide.with(this)
                .load(image2)
                .into(second_img);

//        Glide.with(this)
//                .load(image3)
//                .into(third_img);

        Glide.with(this)
                .load(image4)
                .into(foruth_img);

        Glide.with(this)
                .load(image5)
                .into(fifth_img);

        Glide.with(this)
                .load(image6)
                .into(sixth_img);

        Glide.with(this)
                .load(image7)
                .into(seventh_img);

        Glide.with(this)
                .load(image8)
                .into(eighth_img);

        Glide.with(this)
                .load(image9)
                .into(ninth_img);

        Glide.with(this)
                .load(image10)
                .into(tenth_img);

        Glide.with(this)
                .load(image11)
                .into(th_img11);

        Glide.with(this)
                .load(image12)
                .into(th_img12);

        Glide.with(this)
                .load(image13)
                .into(img13);

        Glide.with(this)
                .load(image14)
                .into(img14);

        ScrollView scrollView = findViewById(R.id.scrollview);
        Button button = findViewById(R.id.button);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            boolean isScrollingUp = false; // 记录滑动方向是否向上
            int scrollY = 0;

            @Override
            public void onScrollChanged() {
                System.out.println("这是值：" + button.getHeight()); //217
                //int scrollY = scrollView.getScrollY();
                int nowY = scrollView.getScrollY();
                //if (scrollY > 0 && !isScrollingUp) {
                if (nowY < scrollY && Math.abs(nowY - scrollY) >= 50) {
                    // 当从下往上滑动时，按钮从屏幕底部浮出
                    System.out.println("值范围内");
                    if (!isScrollingUp) {
                        button.animate()
                                .translationY(155)
                                .alpha(0)
                                .setDuration(300)
                                .start();
                    }
                    isScrollingUp = true;
                    scrollY = nowY;
                } else if (nowY > scrollY && Math.abs(nowY - scrollY) >= 50) {
                        //} else if (scrollY == 0 && isScrollingUp) {
                    // 当从上往下滑动并回到顶部时，按钮从顶部反方向浮动下来
                    if (isScrollingUp) {
                        button.animate()
                                .translationY(-105)
                                .alpha(1)
                                .setDuration(300)
                                .start();
                    }
                    isScrollingUp = false;
                    System.out.println("值内");
                    scrollY = nowY;
                } else if (scrollView.getScrollY() == scrollView.getChildAt(0).getHeight() - scrollView.getHeight()) {
                    if (isScrollingUp) {
                        button.animate()
                                .translationY(-105)
                                .alpha(1)
                                .setDuration(300)
                                .start();
                    }
                    isScrollingUp = false;
                    System.out.println("值内");
                    scrollY = nowY;
                }
            }
        });


        first_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 1) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//                    builder.setMessage("重新保存已获取的奖励（图片）？");
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“是”，可以执行相应操作
//                            reward_again(1);
//                        }
//                    });
//                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
//                        }
//                    });
//                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    reward_show("【刚睡醒】勋章", Boolean.TRUE, image1);
                } else {
                    reward_show("【刚睡醒】勋章", Boolean.FALSE, image1);
                }


            }
        });

        second_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 2) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//                    builder.setMessage("重新保存已获取的奖励（图片）？");
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“是”，可以执行相应操作
//                            reward_again(2);
//                        }
//                    });
//                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
//                        }
//                    });
//                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    reward_show("美图 x 1", Boolean.TRUE, image2);
                } else {
                    reward_show("美图 x 1", Boolean.FALSE, image2);
                }


            }
        });

        third_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 3) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//                    builder.setMessage("重新保存已获取的奖励（图片）？");
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“是”，可以执行相应操作
//                            reward_again(3);
//                        }
//                    });
//                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
//                        }
//                    });
//                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    reward_show("五周年限定礼包", Boolean.TRUE, R.drawable.onlychirstmas);
                } else {
                    reward_show("五周年限定礼包", Boolean.FALSE, R.drawable.onlychirstmas);
                }


            }
        });

        foruth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 4) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//                    builder.setMessage("重新保存已获取的奖励（图片）？");
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“是”，可以执行相应操作
//                            reward_again(4);
//                        }
//                    });
//                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
//                        }
//                    });
//                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    reward_show("【坚持到底】勋章", Boolean.TRUE, image4);
                } else {
                    reward_show("【坚持到底】勋章", Boolean.FALSE, image4);
                }


            }
        });

        fifth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 5) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//                    builder.setMessage("重新保存已获取的奖励（图片）？");
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“是”，可以执行相应操作
//                            reward_again(5);
//                        }
//                    });
//                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
//                        }
//                    });
//                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    reward_show("大图集", Boolean.TRUE, image5);
                } else {
                    reward_show("大图集", Boolean.FALSE, image5);
                }


            }
        });

        sixth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("daliy_task", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 5) {
                    reward_show("【初阶二次元】勋章", Boolean.TRUE, image6);
                } else {
                    reward_show("【初阶二次元】勋章", Boolean.FALSE, image6);
                }


            }
        });

        seventh_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("daliy_task", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 20) {
                    reward_show("【萝莉控！】勋章", Boolean.TRUE, image7);
                } else {
                    reward_show("【萝莉控！】勋章", Boolean.FALSE, image7);
                }


            }
        });

        eighth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("daliy_task", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 50) {
                    reward_show("【一定有其他目的】勋章", Boolean.TRUE, image8);
                } else {
                    reward_show("【一定有其他目的】勋章", Boolean.FALSE, image8);
                }


            }
        });

        ninth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("daliy_task", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 100) {
                    reward_show("【最喜欢啦！】勋章", Boolean.TRUE, image9);
                } else {
                    reward_show("【最喜欢啦！】勋章", Boolean.FALSE, image9);
                }


            }
        });

        tenth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("big_voyage", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 5) {
                    reward_show("【大航海！】勋章", Boolean.TRUE, image10);
                } else {
                    reward_show("【大航海！】勋章", Boolean.FALSE, image10);
                }


            }
        });

        th_img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("chunjie", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 25) {
                    reward_show("【我还很纯洁呢~】勋章", Boolean.TRUE, image11);
                } else {
                    reward_show("【我还很纯洁呢~】勋章", Boolean.FALSE, image11);
                }


            }
        });

        th_img12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String daily_task = sharedPreferences.getString("white_hair", "");

                if (TextUtils.isEmpty(daily_task)) {
                    daily_task = "0";
                }

                int CompletedDate_1 = Integer.parseInt(daily_task);

                if (CompletedDate_1 >= 50) {
                    reward_show("【白毛什么的我最喜欢啦】勋章", Boolean.TRUE, image12);
                } else {
                    reward_show("【白毛什么的我最喜欢啦】勋章", Boolean.FALSE, image12);
                }


            }
        });

        img13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    reward_show("彩蛋超大图集", null, image13);


            }
        });


        TextView tuji = findViewById(R.id.textView35);

        tuji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setTitle("兑换 彩蛋超大图集？");
                builder.setMessage("你需要至少达成 6 枚勋章。检查你的勋章数量，确保充足后，点击【确认】。");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Synchronous();
                        if (medals >= 6){
                                Toast tosat = Toast.makeText(SignInActivity.this, "兑换成功✅", Toast.LENGTH_LONG);
                                tosat.show();
                                String htmlUrl = "https://disk.srinternet.top/d/srdisk-files/direct/wallpaper_generator/reward_of_2024_new_year/%E4%BA%8C%E6%AC%A1%E5%85%83%E5%A3%81%E7%BA%B8.zip";  // 要打开的HTML的URL
                                openHTMLInBrowser(htmlUrl);
                        } else {
                            Toast tosat = Toast.makeText(SignInActivity.this, "勋章不够哦 awa", Toast.LENGTH_LONG);
                            tosat.show();
                        }
                    }
                });
                builder.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击了取消按钮，可以进行一些处理，如禁用相关功能等
                        dialog.dismiss();
                    }

                });
                builder.show();
            }
        });

        TextView Event_text = findViewById(R.id.textView16);
        Event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsFirst == "true") {
                    SignMessage("记得每日签到！","关闭此界面后，您仍然可以从 主页 —> 侧边栏顶部海报 进入签到噢 (/≧▽≦)/");
                }
                finish();
            }
        });

        TextView Refresh_text = findViewById(R.id.textView17);
        Refresh_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        Button Event_Button = findViewById(R.id.button);
        Event_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign();
            }
        });
    }



    private void Sign() {
        Synchronous();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());
        SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);

        String savedDate = sharedPreferences.getString("currentDate", "0");
        String CompletedDate_fuben = sharedPreferences.getString("CompletedDate", "0");

        if (TextUtils.isEmpty(CompletedDate_fuben)) {
            CompletedDate_fuben = "0";
        }

        int CompletedDate = Integer.parseInt(CompletedDate_fuben);

        //单日任务判断检测
        SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
        String daily_task = sharedPreferences1.getString("daliy_task", "0");

        int process = Integer.parseInt(daily_task);
        String txt = "";
        String txt2 = "";

        System.out.println("这是daliy_task_" + daily_task);

        if (process < 5) {
            int gap = Math.abs(5 - process);
            txt = "距离达成【初阶二次元】还有 " + Integer.toString(gap) + " 张";
        } else if (process >= 5 && process < 20) {
            int gap = 20 - process;
            txt2 = txt2 + "\n您已达成【初阶二次元】";
            txt = "距离达成【萝莉控！】还有 " + Integer.toString(gap) + " 张";
        } else if (process >= 20 && process < 50) {
            int gap = 50 - process;
            txt2 = txt2 + "\n您已达成【初阶二次元】";
            txt2 = txt2 + "\n您已达成【萝莉控！】";
            txt = "距离达成【一定有其他目的】还有 " + Integer.toString(gap) + " 张";
        } else if (process >= 50 && process < 100) {
            int gap = 100 - process;
            txt2 = txt2 + "\n您已达成【初阶二次元】";
            txt2 = txt2 + "\n您已达成【萝莉控！】";
            txt2 = txt2 + "\n您已达成【一定有其他目的】";
            txt = "距离达成【最喜欢啦！】还有 " + Integer.toString(gap) + " 张";
        } else if (process >= 100) {
            txt2 = txt2 + "\n您已达成所有单日任务！";
        } else {
            // Nothing here
        }

        // 特殊任务判断检测
        SharedPreferences sharedPreferences2 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
        String special_task = sharedPreferences1.getString("big_voyage", "0");
        String special_task1 = sharedPreferences1.getString("chunjie", "0");
        String special_task2 = sharedPreferences1.getString("white_hair", "0");

        int b = Integer.parseInt(special_task);
        int c = Integer.parseInt(special_task1);
        int w = Integer.parseInt(special_task2);

        if (b >= 5) {
            txt2 = txt2 + "\n您已达成【大航海！】";
        } else {
            txt = txt + "\n距离达成【大航海！】还有 " + Integer.toString(5 - b) + " 次";
        }

        if (c >= 25) {
            txt2 = txt2 + "\n您已达成【我还很纯洁呢~】";
        } else {
            txt = txt + "\n距离达成【我还很纯洁呢~】还有 " + Integer.toString(25 - c) + " 张";
        }

        if (w >= 50) {
            txt2 = txt2 + "\n您已达成【白毛什么的我最喜欢啦】";
        } else {
            txt = txt + "\n距离达成【白毛什么的我最喜欢啦】还有 " + Integer.toString(50 - w) + " 张";
        }

        if (!savedDate.equals(currentDate)) {
            if (CompletedDate != 5){
                CompletedDate += 1;
                CompletedDate_fuben = String.valueOf(CompletedDate); // 更新 CompletedDate_fuben 的值
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentDate", currentDate);
                editor.putString("CompletedDate", CompletedDate_fuben);
                editor.apply();

                int RemainingDate = 5 - Integer.parseInt(CompletedDate_fuben);
                reward(RemainingDate);
            } else {
                SignMessage("签到已结束","恭喜您已完成所有签到！\n" + txt + txt2);
            }
        } else {
            int RemainingDate = 5 - Integer.parseInt(CompletedDate_fuben);
            SignMessage("已签到","今天是第 " + CompletedDate + " 天，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天。\n" + txt + txt2);
        }
    }

    private void SignMessage(String Title, String Text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        builder.setTitle(Title)
                .setMessage(Text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的操作
                        Synchronous();
                    }
                })
                .setCancelable(false); // 不可取消对话框

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void Synchronous() {

        TextView first = findViewById(R.id.textView19);
        TextView second = findViewById(R.id.textView3);
        TextView third = findViewById(R.id.textView4);
        TextView foruth = findViewById(R.id.textView8);
        TextView fifth = findViewById(R.id.textView9);

        TextView d1 = findViewById(R.id.textView13);
        TextView d2 = findViewById(R.id.textView20);
        TextView d3 = findViewById(R.id.textView22);
        TextView d4 = findViewById(R.id.textView24);

        TextView t1 = findViewById(R.id.textView27);
        TextView t2 = findViewById(R.id.textView29);
        TextView t3 = findViewById(R.id.textView31);


        try {
            medals = 0;
            // 签到判断检测
            System.out.println("这是开始同步");
            SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String CompletedDate = sharedPreferences.getString("CompletedDate", "");

            int CompletedDate_1 = Integer.parseInt(CompletedDate);

            if (CompletedDate_1 == 1) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 1;
            } else if (CompletedDate_1 == 2) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 1;
            } else if (CompletedDate_1 == 3) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                third.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                third.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 1;
            } else if (CompletedDate_1 == 4) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                third.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                third.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                foruth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                foruth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 2;
            } else if (CompletedDate_1 == 5) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                third.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                third.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                foruth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                foruth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                fifth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                fifth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 2;
            } else {
                //Nothing there
            }

            //单日任务判断检测
            SharedPreferences sharedPreferences1 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String daily_task = sharedPreferences1.getString("daliy_task", "");

            int process = Integer.parseInt(daily_task);

            if (process >= 5 && process < 20) {
                d1.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体

                medals += 1;
            } else if (process >= 20 && process < 50) {
                d1.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d2.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体

                medals += 2;
            } else if (process >= 50 && process < 100) {
                d1.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d2.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d3.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d3.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 3;
            } else if (process >= 100) {
                d1.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d2.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d3.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d3.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                d4.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                d4.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体

                medals += 4;
            } else {
                // Nothing here
            }

            // 特殊任务判断检测
//            SharedPreferences sharedPreferences2 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
//            String special_task = sharedPreferences1.getString("big_voyage", "");
//            String special_task1 = sharedPreferences1.getString("chunjie", "");
//            String special_task2 = sharedPreferences1.getString("white_hair", "");
//
//            int b = Integer.parseInt(special_task);
//            int c = Integer.parseInt(special_task1);
//            int w = Integer.parseInt(special_task2);
//
//            System.out.println("这是b：" + Integer.toString(b));
//
//            if (b >= 5) {
//                t1.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
//                t1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
//
//                medals += 1;
//            }
            SharedPreferences sharedPreferences2 = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String special_task = sharedPreferences2.getString("big_voyage", "");
            String special_task1 = sharedPreferences2.getString("chunjie", "");
            String special_task2 = sharedPreferences2.getString("white_hair", "");

            int b = 0;
            int c = 0;
            int w = 0;

            if (!TextUtils.isEmpty(special_task) && TextUtils.isDigitsOnly(special_task)) {
                b = Integer.parseInt(special_task);
            }

            if (!TextUtils.isEmpty(special_task1) && TextUtils.isDigitsOnly(special_task1)) {
                c = Integer.parseInt(special_task1);
            }

            if (!TextUtils.isEmpty(special_task2) && TextUtils.isDigitsOnly(special_task2)) {
                w = Integer.parseInt(special_task2);
            }

            if (b >= 5) {
                t1.setTextColor(Color.parseColor("#88FF00")); // 设置文本颜色为 #88FF00
                t1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 1;
            }
            if (c >= 25) {
                t2.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                t2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体

                medals += 1;
            }
            if (w >= 50) {
                t3.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                t3.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                medals += 1;

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public void reward_show(String reward_name, Boolean saving, int image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.reward_dialog, null);
        builder.setView(customView);

        Button button1 = customView.findViewById(R.id.button_negative2);
        Button button2 = customView.findViewById(R.id.button_negative);
        TextView dialog_title = customView.findViewById(R.id.dialog_title);
        ImageView imageView = customView.findViewById(R.id.imageView10);
        TextView dialog_message = customView.findViewById(R.id.dialog_message2);

        Glide.with(this)
                .load(image)
                .into(imageView);

        dialog_message.setText("当前已达成 " + Integer.toString(medals) + "/9 个勋章");

        if (saving == Boolean.FALSE) {
            dialog_title.setText(reward_name + " 未达成");
            button1.setTextColor(Color.parseColor("#a1a3a6"));
            button1.setEnabled(saving);
        } else if (saving == Boolean.TRUE) {
            dialog_title.setText(reward_name + " 已达成");
            button1.setTextColor(Color.parseColor("#00BCD4"));
            button1.setEnabled(saving);
        } else {
            dialog_title.setText(reward_name);
            button1.setTextColor(Color.parseColor("#a1a3a6"));
            button1.setEnabled(Boolean.FALSE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Button 1 的点击事件逻辑
                // 在这里写下你希望 Button 1 点击后执行的代码

                // String htmlUrl = "https://www.123pan.com/s/2bLlVv-gJKph.html";  // 要打开的HTML的URL
                String title = reward_name;
                String description = reward_name;
                String savedImageURL = null;
                String savedImageURL1 = null;
                String savedImageURL2 = null;
                String savedImageURL3 = null;

                if (reward_name == "五周年限定礼包") {
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
                    Drawable drawable = imageView.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                    savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

                    if (savedImageURL != null) {
                        Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
                    }

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

//    private void reward_again(int rewarded_date) {
//        if (rewarded_date == 1) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first_day);
//
//            String title = "【刚睡醒】勋章";
//            String description = "【刚睡醒】勋章";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (rewarded_date == 2) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_day);
//
//            String title = "美图";
//            String description = "美图";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (rewarded_date == 3) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.third_day);
//
//            String title = "五周年限定";
//            String description = "五周年限定";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (rewarded_date == 4) {
//            String title = "【坚持到底】勋章";
//            String description = "【坚持到底】勋章";
//            Boolean result = true;
//
//            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
//            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);
//
//            if (savedImageURL1 == null) {
//                result = false;
//            }
//
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null && result != false) {
//                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            //Nothing there
//        }
//    }

    private void reward(int RemainingDate){

        SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
        String CompletedDate = sharedPreferences.getString("CompletedDate", "");

        int CompletedDate_1 = Integer.parseInt(CompletedDate);

        if (CompletedDate_1 == 1) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first_day);
//
//            String title = "绪山 みはり se图";
//            String description = "绪山 みはり se图";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
//            } else {
//                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 1 天“ 下方的图片再次尝试保存。");
//            }
            Synchronous();
            reward_show("【刚睡醒】勋章", Boolean.TRUE, R.drawable.first_day);



        } else if (CompletedDate_1 == 2) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_day);
//
//            String title = "中秋限定";
//            String description = "中秋限定（AI生成）";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
//            } else {
//                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
//            }
            Synchronous();
            reward_show("美图 x 1", Boolean.TRUE, R.drawable.second_day);


        } else if (CompletedDate_1 == 3) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.third_day);
//
//            String title = "中秋限定";
//            String description = "中秋限定（AI生成）";
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null) {
//                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
//            } else {
//                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
//            }
//
            Synchronous();
            reward_show("五周年限定礼包", Boolean.TRUE, R.drawable.onlychirstmas);


        } else if (CompletedDate_1 == 4) {
//            String title = "中秋限定";
//            String description = "中秋限定（AI生成）";
//            Boolean result = true;
//
//            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
//            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);
//
//            if (savedImageURL1 == null) {
//                result = false;
//            }
//
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fourth_day_2);
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null && result != false) {
//                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
//            } else {
//                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
//            }
            Synchronous();
            reward_show("【坚持到底】勋章", Boolean.TRUE, R.drawable.shoot);


        } else if (CompletedDate_1 == 5) {
//            String title = "中秋限定";
//            String description = "中秋限定（AI生成）";
//            Boolean result = true;
//
//            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_1);
//            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);
//
//            if (savedImageURL1 == null) {
//                result = false;
//            }
//
//            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_2);
//            String savedImageURL2 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap2, title, description);
//
//            if (savedImageURL2 == null) {
//                result = false;
//            }
//
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_3);
//            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);
//
//            if (savedImageURL != null && result != false) {
//                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
//            } else {
//                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
//            }
            Synchronous();
            reward_show("大图集", Boolean.TRUE, R.drawable.card100);

        } else {
            //Nothing there
        }

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

}