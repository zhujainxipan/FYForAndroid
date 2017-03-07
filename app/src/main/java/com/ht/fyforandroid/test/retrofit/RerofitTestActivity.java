package com.ht.fyforandroid.test.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ht.fyforandroid.R;
import com.ht.fyforandroid.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by niehongtao on 17/3/7.
 */
public class RerofitTestActivity extends BaseActivity {
    @InjectView(R.id.btn_hello)
    Button mBtnHello;
    @InjectView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mBtnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    private void query() {
        Call<WeatherEntity> call = Retrofit.getRetrofit().getService().getMenuById("guangzhou", "json", "B95329fb7fdda1e32ba3e3a245193146");
        call.enqueue(new Callback<WeatherEntity>() {
            @Override
            public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                if (response.isSuccessful()) {
                    WeatherEntity result = response.body();//关键
                    if (result != null) {
                        String msg = result.getDate();
                        mTvContent.setText("message=" + msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherEntity> call, Throwable t) {

            }
        });

    }
}
