package test.com.newsApp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import test.com.newsApp.R;
import test.com.newsApp.broadcastReceiver.NetworkBroadcastReceiver;
import test.com.newsApp.utils.AppDataHolder;
import test.com.newsApp.utils.ConnectivityHelper;


public class NewsAppBaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
        progressDialog.setCancelable(false);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        AppDataHolder.getInstance().setConnectedToInternet(cm.getActiveNetworkInfo() != null);
    }

    protected void showProgressDialog() {
        if(progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void dismissProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void setToolBarWithBackBtn(@StringRes int titleRes) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(titleRes);
        }
    }
    protected void hideToolbarWithBackBtn() {
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().hide();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initSnackBar() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        snackbar = Snackbar.make(viewGroup, getString(R.string.internet_required), Snackbar.LENGTH_INDEFINITE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        initSnackBar();
        networkBroadcastReceiver = new NetworkBroadcastReceiver(this::handleInternetConnectionLoss);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(networkBroadcastReceiver, filter);
        handleInternetConnectionLoss(ConnectivityHelper.isConnected(this));
    }

    private void handleInternetConnectionLoss(boolean isConnected) {
        if (snackbar != null) {
            if (isConnected && snackbar.isShown()) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                snackbar.dismiss();
                AppDataHolder.getInstance().setConnectedToInternet(true);
            } else if (!isConnected) {
                //To make the screen not touchable
               // getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                AppDataHolder.getInstance().setConnectedToInternet(false);
                snackbar.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void clearBackStack() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i <= manager.getBackStackEntryCount(); ++i) {
            manager.popBackStack();
        }
    }


}
