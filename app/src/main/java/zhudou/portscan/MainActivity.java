package zhudou.portscan;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public String localHost_ip="";

    SimpleAdapter simpleAdapter=null;//适配器
    List<Map<String, Object>> result = new ArrayList<>(); //列表
    ListView list;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Message message = handler.obtainMessage();
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    //
                    String ip=data.getString("ip");
                    String port=data.getString("port");
                    String text=ip+":"+port;
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("text",text);
                    result.add(item);
                    simpleAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    //

                    (Toast.makeText(getBaseContext(),"正在扫描 "+data.getString("ip")+":"+data.getString("port"),Toast.LENGTH_SHORT)).show();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ip ip=new Ip();
        localHost_ip=ip.getLocalIP();
        //Alert.msg(this,localHost_ip);


        list=(ListView)findViewById(R.id.ips);
        Message message= handler.obtainMessage();
        message.what=1;
        message.sendToTarget();

        //
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("text","");
        result.add(item);
        simpleAdapter = new SimpleAdapter(getBaseContext(), result,
                R.layout.layout_ip, new String[] {"text"},
                new int[] { R.id.ip });

        list.setAdapter(simpleAdapter);

        //

        Button button_scan=(Button)findViewById(R.id.button_scan);
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始扫描


                int ports[]={21,22,80,8080,3306};

                for(int i=0;i<ports.length;i++){
                    final int finalI = ports[i];
                    (new Thread(new Runnable(){
                        @Override
                        public void run() {
                            Scan s=new Scan(handler);
                            s.scanLocalHostNet(finalI);
                        }
                    })).start();
                }
            }
        });
    }

    @Override
    //禁止返回按键
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
