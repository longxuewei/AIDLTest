package cn.lxw.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.lxw.aidltest.ITestAidl;

public class MainActivity extends AppCompatActivity {

    private TextView tv_result;
    private EditText num1, num2;
    private Button bt_add;
    private ITestAidl iTestAidl;
    private ServiceConnection conn = new ServiceConnection() {

        /**
         * 绑定服务
         * @param componentName
         * @param iBinder
         */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iTestAidl = ITestAidl.Stub.asInterface(iBinder);
        }

        /**
         * 释放资源
         * @param componentName
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            iTestAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int i = Integer.parseInt(num1.getText().toString());
                    int i1 = Integer.parseInt(num2.getText().toString());
                    int add = iTestAidl.add(i, i1);
                    tv_result.setText(add + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "错误了！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        /**
         * Android 5.0不支持 隐式意图，所以必须要写明
         * 报名，以及全类名。
         */
        intent.setComponent(new ComponentName("cn.lxw.aidltest", "cn.lxw.aidltest.IRemoteService"));

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        tv_result = (TextView) findViewById(R.id.tv_result);
        num1 = (EditText) findViewById(R.id.et_num1);
        num2 = (EditText) findViewById(R.id.et_num2);
        bt_add = (Button) findViewById(R.id.bt_add);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
