package com.afayp.mvpsample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.afayp.mvpsample.mvp.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by afayp on 2016/8/24.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    private Activity mActivity;
    protected P mPresenter;
    private CompositeSubscription mCompositeSubscription;


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
    }

//    public Toolbar initToolBar(View view, String title) {
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        toolbar_title.setText(title);
//        return toolbar;
//    }


    protected abstract P createPresenter();

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
        mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
