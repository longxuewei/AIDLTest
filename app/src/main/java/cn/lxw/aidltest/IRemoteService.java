package cn.lxw.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 猿代码：Lxw
 * 时间：2016/7/3
 * 伊妹儿：china2021@126.com
 */
public class IRemoteService extends Service {

    private static final String TAG = "IRemoteService";
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new ITestAidl.Stub(){
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d(TAG, "add: 收到远程请求。输入参数1："+num1+"输入参数2："+num2);
            return 0;
        }
    };
}
