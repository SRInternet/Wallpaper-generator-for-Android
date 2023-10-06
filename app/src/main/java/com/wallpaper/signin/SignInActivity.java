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
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.application.R;
import com.wallpaper.generator.MainActivity;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;
import android.text.TextUtils;
import android.provider.MediaStore;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class SignInActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        Intent intent = getIntent();
        String IsFirst = intent.getStringExtra("IsFirst");

        Synchronous();

        ImageView first_img = findViewById(R.id.imageView2);
        ImageView second_img = findViewById(R.id.imageView3);
        ImageView third_img = findViewById(R.id.imageView4);
        ImageView foruth_img = findViewById(R.id.imageView5);
        ImageView fifth_img = findViewById(R.id.imageView6);
        ImageView sixth_img = findViewById(R.id.imageView7);
        ImageView seventh_img = findViewById(R.id.imageView8);

        int image1 = R.drawable.first_day;
        int image2 = R.drawable.second_day;
        int image3 = R.drawable.third_day;
        int image4 = R.drawable.x2;
        int image5 = R.drawable.x3;
        int image6 = R.drawable.sign_background;
        int image7 = R.drawable.last;

        Glide.with(this)
                .load(image1)
                .into(first_img);

        Glide.with(this)
                .load(image2)
                .into(second_img);

//        Glide.with(this)
//                .load(R.drawable.third_day)
//                .into(third_img);

        Glide.with(this)
                .load(image4)
                .into(foruth_img);

        Glide.with(this)
                .load(image5)
                .into(fifth_img);

//        Glide.with(this)
//                .load(R.drawable.sign_background)
//                .into(sixth_img);

        Glide.with(this)
                .load(image7)
                .into(seventh_img);


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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("重新保存已获取的奖励（图片）？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“是”，可以执行相应操作
                            reward_again(1);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("重新保存已获取的奖励（图片）？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“是”，可以执行相应操作
                            reward_again(2);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("重新保存已获取的奖励（图片）？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“是”，可以执行相应操作
                            reward_again(3);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("重新保存已获取的奖励（图片）？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“是”，可以执行相应操作
                            reward_again(4);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setMessage("重新保存已获取的奖励（图片）？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“是”，可以执行相应操作
                            reward_again(5);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户点击了“否”，可以进行一些处理，或者什么都不做
                        }
                    });
                    builder.setCancelable(false); // 禁止点击对话框外部取消对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
                }


            }
        });

        sixth_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 6) {
                    SignMessage("已解锁🔒"," ”银发“ 类型壁纸 已经解锁了。");
                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
                }


            }
        });

        seventh_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
                String CompletedDate = sharedPreferences.getString("CompletedDate", "");

                if (TextUtils.isEmpty(CompletedDate)) {
                    CompletedDate = "0";
                }

                int CompletedDate_1 = Integer.parseInt(CompletedDate);

                if (CompletedDate_1 >= 7) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View customView = inflater.inflate(R.layout.multibutton_dialog, null);
                    builder.setView(customView);

                    Button button1 = customView.findViewById(R.id.button_positive);
                    Button button2 = customView.findViewById(R.id.button_negative);

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Button 1 的点击事件逻辑
                            // 在这里写下你希望 Button 1 点击后执行的代码

                            String htmlUrl = "https://www.123pan.com/s/2bLlVv-gJKph.html";  // 要打开的HTML的URL
                            openHTMLInBrowser(htmlUrl);
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

                } else {
                    SignMessage("本日奖励还未解锁","坚持，你马上就能得到了！( •̀ ω •́ )✧");
                }


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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());
        SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);

        String savedDate = sharedPreferences.getString("currentDate", "");
        String CompletedDate_fuben = sharedPreferences.getString("CompletedDate", "");

        if (TextUtils.isEmpty(CompletedDate_fuben)) {
            CompletedDate_fuben = "0";
        }

        int CompletedDate = Integer.parseInt(CompletedDate_fuben);

        if (!savedDate.equals(currentDate)) {
            if (CompletedDate != 7){
                CompletedDate += 1;
                CompletedDate_fuben = String.valueOf(CompletedDate); // 更新 CompletedDate_fuben 的值
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentDate", currentDate);
                editor.putString("CompletedDate", CompletedDate_fuben);
                editor.apply();

                int RemainingDate = 7 - Integer.parseInt(CompletedDate_fuben);
                reward(RemainingDate);
            } else {
                SignMessage("签到已结束","恭喜您已完成所有签到！\n让我们一起完整、快乐地畅游壁纸生成器，个性化你的手机吧！");
            }
        } else {
            int RemainingDate = 7 - Integer.parseInt(CompletedDate_fuben);
            SignMessage("已签到","今天是第 " + CompletedDate + " 天，您已签到，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天。");
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

        TextView first = findViewById(R.id.textView2);
        TextView second = findViewById(R.id.textView3);
        TextView third = findViewById(R.id.textView4);
        TextView foruth = findViewById(R.id.textView8);
        TextView fifth = findViewById(R.id.textView9);
        TextView sixth = findViewById(R.id.textView10);
        TextView seventh = findViewById(R.id.textView14);

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
            String CompletedDate = sharedPreferences.getString("CompletedDate", "");

            int CompletedDate_1 = Integer.parseInt(CompletedDate);

            if (CompletedDate_1 == 1) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
            } else if (CompletedDate_1 == 2) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
            } else if (CompletedDate_1 == 3) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                third.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                third.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
            } else if (CompletedDate_1 == 4) {
                first.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                first.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                second.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                second.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                third.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                third.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                foruth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                foruth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
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
            } else if (CompletedDate_1 == 6) {
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
                sixth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                sixth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
            } else if (CompletedDate_1 == 7) {
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
                sixth.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                sixth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
                seventh.setTextColor(Color.parseColor("#88FF00")); // 将文本颜色设置为 #88FF00
                seventh.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // 设置粗体
            } else {
                //Nothing there
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    private void reward_again(int rewarded_date) {
        if (rewarded_date == 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first_day);

            String title = "绪山 みはり se图";
            String description = "绪山 みはり se图";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
            }

        } else if (rewarded_date == 2) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_day);

            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
            }

        } else if (rewarded_date == 3) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.third_day);

            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
            }

        } else if (rewarded_date == 4) {
            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            Boolean result = true;

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourth_day_1);
            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);

            if (savedImageURL1 == null) {
                result = false;
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fourth_day_2);
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null && result != false) {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
            }

        } else if (rewarded_date == 5) {
            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            Boolean result = true;

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_1);
            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);

            if (savedImageURL1 == null) {
                result = false;
            }

            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_2);
            String savedImageURL2 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap2, title, description);

            if (savedImageURL2 == null) {
                result = false;
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_3);
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null && result != false) {
                Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败！请检查权限", Toast.LENGTH_SHORT).show();
            }

            Synchronous();

        } else {
            //Nothing there
        }
    }

    private void reward(int RemainingDate){

        SharedPreferences sharedPreferences = getSharedPreferences("Lasted_Date", Context.MODE_PRIVATE);
        String CompletedDate = sharedPreferences.getString("CompletedDate", "");

        int CompletedDate_1 = Integer.parseInt(CompletedDate);

        if (CompletedDate_1 == 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first_day);

            String title = "绪山 みはり se图";
            String description = "绪山 みはり se图";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
            } else {
                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 1 天“ 下方的图片再次尝试保存。");
            }

            Synchronous();

        } else if (CompletedDate_1 == 2) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_day);

            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
            } else {
                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
            }

            Synchronous();

        } else if (CompletedDate_1 == 3) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.third_day);

            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null) {
                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
            } else {
                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
            }

            Synchronous();

        } else if (CompletedDate_1 == 4) {
            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            Boolean result = true;

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourth_day_1);
            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);

            if (savedImageURL1 == null) {
                result = false;
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fourth_day_2);
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null && result != false) {
                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
            } else {
                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
            }

            Synchronous();

        } else if (CompletedDate_1 == 5) {
            String title = "中秋限定";
            String description = "中秋限定（AI生成）";
            Boolean result = true;

            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_1);
            String savedImageURL1 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap1, title, description);

            if (savedImageURL1 == null) {
                result = false;
            }

            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_2);
            String savedImageURL2 = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap2, title, description);

            if (savedImageURL2 == null) {
                result = false;
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fifth_day_3);
            String savedImageURL = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, description);

            if (savedImageURL != null && result != false) {
                SignMessage("签到成功，图片奖励已保存至相册","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包还有 " +  Integer.toString(RemainingDate) + " 天！");
            } else {
                SignMessage("签到成功，但是奖励失败","请检查 壁纸生成器 是否具有读写内部存储或访问相册的权限，然后点击 ”第 " + CompletedDate + " 天“ 下方的图片再次尝试保存。");
            }

            Synchronous();

        } else if (CompletedDate_1 == 6) {
            SignMessage("恭喜，新的 ”银发“ 类型已解锁！","今天是第 " + CompletedDate + " 天，继续加油，距离最终的大礼包仅剩 " +  Integer.toString(RemainingDate) + " 天！");
        } else if (CompletedDate_1 == 7) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View customView = inflater.inflate(R.layout.multibutton_dialog, null);
            builder.setView(customView);

            Button button1 = customView.findViewById(R.id.button_positive);
            Button button2 = customView.findViewById(R.id.button_negative);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Button 1 的点击事件逻辑
                    // 在这里写下你希望 Button 1 点击后执行的代码

                    String htmlUrl = "https://www.123pan.com/s/2bLlVv-gJKph.html";  // 要打开的HTML的URL
                    openHTMLInBrowser(htmlUrl);
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