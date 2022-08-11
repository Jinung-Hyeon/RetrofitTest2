package com.example.retrofittest2;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://picsum.photos/";
    ViewPagerAdapter viewPagerAdapter;
    ImageView iv;
    ArrayList<String> imageList;
    PlayImage playImage;
    ViewPager viewPager;
    int position = 0;

    //private static final int DP = 24; //수치가 높을수록 옆에 그림이 조금씩 더 많이보임

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iv = findViewById(R.id.iv);
        imageList = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this, imageList);
        //playImage = new PlayImage();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<List<ItemModel>> call = retrofitService.getItem(1,50);
        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                Log.d("msg", "레트로핏 성공");
                List<ItemModel> itemModels = response.body();
                imageList.clear();
                for (ItemModel itemModel : itemModels) {
                    //Log.d("msg", itemModel.toString()); // json정보 전체 뿌려줌.
                    Log.d("msg", itemModel.getDownload_url());
                    imageList.add(itemModel.getDownload_url());
                }

                Log.d("msg", ""+imageList);


                viewPager.setClipToPadding(false);

                //float density = getResources().getDisplayMetrics().density;
                //int margin = (int) (DP * density);
                //viewPager.setPadding(margin, 0, margin, 0);
                //viewPager.setPageMargin(margin/2);

                viewPager.setAdapter(new ViewPagerAdapter(MainActivity.this, imageList));
                position = viewPagerAdapter.getCount();
                Log.e("msg", ": " + viewPagerAdapter.getCount());

                ViewPagerThread viewPagerThread = new ViewPagerThread();
                viewPagerThread.start();

            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                Log.d("msg", "레트로핏 실패");
            }
        });



    }

    class ViewPagerThread extends Thread{
        @Override
        public void run() {
            while (true){


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (position == viewPagerAdapter.getCount()){
                            position = 0;

                        }
                        viewPager.setCurrentItem(position++, true);
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // imageList 뿌려주는 스레드드
    class PlayImage extends Thread {
        @Override
        public void run() {
            for (int i = 0; i <= imageList.size(); i++) {
                int j = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this)
                                .load(imageList.get(j))
                                .transition(withCrossFade())
                                .into(iv);
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}