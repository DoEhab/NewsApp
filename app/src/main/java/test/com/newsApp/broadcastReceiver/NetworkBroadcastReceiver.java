package test.com.newsApp.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import test.com.newsApp.utils.ConnectivityHelper;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private final ConnectivityCallback callback;
    public NetworkBroadcastReceiver(ConnectivityCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ConnectivityHelper.isConnected(context)) {
            callback.getConnectionStatus(false);
        } else {
            callback.getConnectionStatus(true);
        }
    }

    public interface ConnectivityCallback {
        void getConnectionStatus(boolean isConnected);
    }
}
