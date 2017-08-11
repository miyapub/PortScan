package zhudou.portscan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by morgan on 2017/8/5.
 */

public class Alert {
    public static void err(Activity activity,String text){
        TextView msgView=new TextView(activity);
        msgView.setText(text);
        (new AlertDialog.Builder(activity))
                .setTitle(text).setPositiveButton("确认",null)
                .show();
    }
    public static void msg(Activity activity,String text){
        TextView msgView=new TextView(activity);
        (new AlertDialog.Builder(activity))
                .setTitle(text).setPositiveButton("确认",null)
                .show();
    }
    public static void toDoMsg(Activity activity, String text, final Thread t){
        TextView msgView=new TextView(activity);
        (new AlertDialog.Builder(activity))
                .setTitle(text).setPositiveButton("确认",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                t.start();
            }
        }) .show();
    }
    public static void test(Activity activity,String text){
        TextView msgView=new TextView(activity);
        msgView.setText(text);
        (new AlertDialog.Builder(activity))
                .setTitle("test").setView(msgView).setNegativeButton("取消", null)
                .setPositiveButton("确认",null)
                .show();
    }
    public static void Alert(Activity activity, String title, String msg,final Message message){
        TextView msgView=new TextView(activity);
        msgView.setText(msg);
        (new AlertDialog.Builder(activity))
            .setTitle(title).setView(msgView).setNegativeButton("取消", null)
            .setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        message.sendToTarget();
                    }
                })
            .show();
    }
    public static void getAlert(Activity activity, String title,final Message message){
        final EditText input=new EditText(activity);

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(activity);
        alertDialog .setTitle(title);
        alertDialog.setView(input);
        alertDialog.setNegativeButton("取消", null);
        alertDialog.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String text=input.getText().toString();
                                message.obj=text;
                                message.sendToTarget();
                            }
                        });
        alertDialog.show();

    }
    public static void YesOrNo(Activity activity, String title,final Message message){
        final EditText input=new EditText(activity);

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(activity);
        alertDialog .setTitle(title);
        //alertDialog.setView(input);
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //String text=input.getText().toString();
                message.what=5;
                //message.obj=text;
                message.sendToTarget();
            }
        });
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        message.what=6;
                        message.sendToTarget();
                    }
                });
        alertDialog.show();

    }
}
