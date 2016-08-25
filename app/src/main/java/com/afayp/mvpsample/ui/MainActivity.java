package com.afayp.mvpsample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afayp.mvpsample.R;
import com.afayp.mvpsample.imageloader.ImageLoader;
import com.afayp.mvpsample.imageloader.ImageLoaderUtil;
import com.afayp.mvpsample.mvp.main.MainModel;
import com.afayp.mvpsample.mvp.main.MainPresenter;
import com.afayp.mvpsample.mvp.main.MainView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * MainView接口抽取了MainActivity要实现的业务方法
 * MainActivity首先实现MainView接口，然后把自身作为view层注入到P层
 * 这样p层就持有了view层，当然onDestory里要让p层移除对view的引用
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.iv)
    ImageView imageView;
    @Bind(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.loadData("101010100");
        ImageLoader imageLoader = new ImageLoader.Builder()
                .url("imgUrl")
                .imgView(imageView)
                .build();
        ImageLoaderUtil.getInstance().loadImage(mActivity,imageLoader);
    }

    @Override
    protected MainPresenter createPrestener() {
        return new MainPresenter(this);
    }

    @Override
    public void getDataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = weatherinfo.getCity()+" "
                + weatherinfo.getWD()+" "
                + weatherinfo.getWS()+" "
                + weatherinfo.getTime();
        text.setText(showData);

    }

    @Override
    public void getDataFail(String msg) {
        toastShow("网络不给力");

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);

    }
}
