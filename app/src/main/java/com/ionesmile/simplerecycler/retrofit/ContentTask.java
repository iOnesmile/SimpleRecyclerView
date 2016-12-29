package com.ionesmile.simplerecycler.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ContentTask implements Callback<ResponseBody> {

    private static final String TAG = ContentTask.class.getSimpleName();
    private Context mContext;
    private boolean isShowDialog = false;
    private ProgressDialog mProgressDialog;

    public ContentTask(Context context) {
        this.mContext = context;
    }

    public ContentTask(Context context, boolean isShowDialog) {
        this.mContext = context;
        this.isShowDialog = isShowDialog;
    }

    public void exec() {
        Call<ResponseBody> call = getCall(NetManager.getInstance().getNetHelper());
        call.enqueue(this);

        onHttpStart();
    }

    protected void onHttpStart() {
        if (isShowDialog) {
            mProgressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setMessage("loading...");
            mProgressDialog.show();
        }
    }

    public abstract Call<ResponseBody> getCall(CloudMusicHandle handle);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        cancelProgressDialog();
        try {
            ResponseBody body = response.body();
            if (body != null){
                String result = body.source().readUtf8();
                Log.v(TAG, "onResponse() " + result);
                onRequestSuccess(result);
            } else {
                onFailure(call, new NullPointerException("onResponse().body() is Null"));
            }
        } catch (Exception e) {
            onFailure(call, e);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(TAG, "onFailure()", t);
        cancelProgressDialog();
    }

    protected void cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected abstract void onRequestSuccess(String content);
}
