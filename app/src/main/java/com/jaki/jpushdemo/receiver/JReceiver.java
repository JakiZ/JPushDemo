package com.jaki.jpushdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;


/**
 * usage:极光推送接收器
 * created at 2017/12/19 8:21 by Jaki
 * email:654641423@qq.com
 */

public class JReceiver extends BroadcastReceiver {
    private static final String TAG = "JReceiver";
    private String action;
    @Override
    public void onReceive(Context context, Intent intent) {

        JPushMessage j =  new JPushMessage();
        for (String tag: j.getTags()) {
            Log.e(TAG,"new tag = "+tag);
        }


        action = intent.getAction();
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(action)) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "接收 Registration Id : " + regId);
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) { // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            Log.e(TAG, "收到了自定义消息");
//            Bundle bundle3 = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);//消息的标题。
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);//消息内容。
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段。这是个 JSON 字符串。
            String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);//唯一标识消息的 ID, 可用于上报统计等。
            Log.e(TAG,"自定义消息 title = "+title);
            Log.e(TAG,"自定义消息 message = "+message);
            Log.e(TAG,"自定义消息 extras = "+extras);
            Log.e(TAG,"自定义消息 msgId = "+msgId);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)) {
            Log.e(TAG, "收到了通知");
//            Bundle bundle2 = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//通知的标题
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);//通知内容
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段。这是个 JSON 字符串。
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知栏的Notification ID，可以用于清除Notification
            String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);//富媒体通知推送下载的HTML的文件路径,用于展现WebView
            String fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);//富媒体通知推送下载的图片资源的文件名,多个文件名用 “，” 分开。 与 “JPushInterface.EXTRA_RICHPUSH_HTML_PATH” 位于同一个路径。
            String bigPicPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH);//可支持本地图片的路径，或者填网络图片地址,大图片通知样式中大图片的路径/地址。
            String inboxJson = bundle.getString(JPushInterface.EXTRA_INBOX);//获取的是一个 JSONObject，json 的每个 key 对应的 value 会被当作文本条目逐条展示。收件箱通知样式中收件箱的内容。
            String priority = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);//通知的优先级。默认为0，范围为 -2～2
            String category = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);//完全依赖 rom 厂商对每个 category 的处理策略，比如通知栏的排序。通知分类。
            Log.e(TAG,"通知 title = "+title);
            Log.e(TAG,"通知 content = "+content);
            Log.e(TAG,"通知 extras = "+extras);
            Log.e(TAG,"通知 msgId = "+msgId);
            Log.e(TAG,"通知 notificationId = "+notificationId);
            Log.e(TAG,"通知 fileHtml = "+fileHtml);
            Log.e(TAG,"通知 fileStr = "+fileStr);
            Log.e(TAG,"通知 bigPicPath = "+bigPicPath);
            Log.e(TAG,"通知 inboxJson = "+inboxJson);
            Log.e(TAG,"通知 priority = "+priority);
            Log.e(TAG,"通知 category = "+category);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {  // 用户点开通知栏的推送，在这里可以做些统计，或者做些其他工作
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//通知的标题。
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);//通知内容。
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知栏的Notification ID，可以用于清除Notification
            String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);//唯一标识调整消息的 ID, 可用于上报统计等。
            Log.e(TAG,"点开通知栏 title = "+title);
            Log.e(TAG,"点开通知栏 content = "+content);
            Log.e(TAG,"点开通知栏 extras = "+extras);
            Log.e(TAG,"点开通知栏 notificationId = "+notificationId);
            Log.e(TAG,"点开通知栏 msgId = "+msgId);
        }else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(action)){ //极光推送连接状态变化
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "连接状态 isConnected == "  + connected );
        } else {
            Log.e(TAG, "Unhandled intent - " + action);
        }


    }
}
