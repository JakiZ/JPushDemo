package com.jaki.jpushdemo.utils;

import android.content.Context;


import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.DefaultPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.TagAliasCallback;

/**
 * usage:极光推送帮助类
 * created at 2017/9/8 16:13 by Jaki
 * email:654641423@qq.com
 *
 * 集成极光推送可以采用两种方法，本处介绍其中一种：
 *      1.在工程的build.gradle中确认有如下代码：
 *            repositories {
                jcenter()
              }

             allprojects {
                 repositories {
                    jcenter()
                 }
             }
         2.在app模块下的build.gradle中配置如下内容：

         defaultConfig {

             ndk {
                 //选择要添加的对应cpu类型的.so库。
                 abiFilters 'armeabi', 'armeabi-v7a', 'armeabi-v8a','x86', 'x86_64', 'mips', 'mips64'
             }
             manifestPlaceholders = [
                 JPUSH_PKGNAME : applicationId,
                 JPUSH_APPKEY : "123456789adaddasd", //JPush上注册的包名对应的appkey.
                 JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
             ]
         }
         dependencies {
             compile 'cn.jiguang.sdk:jpush:3.0.8'
             compile 'cn.jiguang.sdk:jcore:1.1.6'
         }

 3.App的入口类，如MyApplication类中，初始化极光推送，添加如下代码：
     //极光推送Log打印,在发布时，应该设置为false
     JPushInterface.setDebugMode(true);
     //初始化极光推送
     JPushInterface.init(getApplicationContext());


 4.自定义广播接收器JReceiver extends BroadcastReceiver
 5.AndroidManifest.xml添加权限和注册service，broadcast
         <!-- 极光推送 Required  start-->
         <uses-permission android:name="com.jyzqsz.stock.permission.JPUSH_MESSAGE" />
         <uses-permission android:name="android.permission.RECEIVE_USER_PRESENT" />
         <!--<uses-permission android:name="android.permission.INTERNET" />-->
         <uses-permission android:name="android.permission.WAKE_LOCK" />
         <uses-permission android:name="android.permission.READ_PHONE_STATE" />
         <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
         <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
         <!-- 权限：震动 -->
         <uses-permission android:name="android.permission.VIBRATE" />
         <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
         <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
         <uses-permission android:name="android.permission.WRITE_SETTINGS" />
         <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

         <!-- Optional. Required for location feature -->
         <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /> <!-- 用于开启 debug 版本的应用在6.0 系统上 层叠窗口权限 -->
         <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
         <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
         <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
         <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />
         <!-- 权限：网络状态改变 -->
         <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
         <uses-permission android:name="android.permission.GET_TASKS" />
         <!-- 极光推送 Required  start-->

         <!--极光推送 服务-->
         manifest节点添加： xmlns:tools="http://schemas.android.com/tools"

         <service android:name="cn.jpush.android.service.PushService"
         android:process=":multiprocess"
         tools:node="replace" >
         </service>

         <!--极光推送 广播接收器-->
         <receiver
         android:name=".function.jpush.JReceiver"
         android:enabled="true">
         <intent-filter>
         <action android:name="cn.jpush.android.intent.REGISTRATION" />
         <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" />
         <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" />
         <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" />
         <action android:name="cn.jpush.android.intent.NOTIFICATION_CLICK_ACTION" />
         <action android:name="cn.jpush.android.intent.CONNECTION" />
         <category android:name="com.jyzqsz.stock" />
         </intent-filter>
         </receiver>

    关于极光推送，经过试验，有以下结论：
        1）当app注册了极光推送，那么他可以在推送保留时间内可以收到的离线推送
        2）app只能接收到注册时所在分组的推送，当它切换到另一个分组时，不会收到切换后所在分组的消息
        3) app中设置标签的的频率不可太高，至少保持间隔3s，设置太频繁一般都不以最后一个标签为准，一般以第一个标签为准
        4）setTags是覆盖原标签，原标签全部清除，addTags是在原有标签基础上添加标签
  */
public class JUtils {

    /**
     * 初始化极光推送
     * @param context
     */
    public static void init(Context context){
        JPushInterface.init(context);
    }

    /**
     * 是否输出极光推送日志
     * @param isDebug
     *
     * 该接口需在init接口之前调用，避免出现部分日志没打印的情况。多进程情况下建议在自定义的Application中onCreate中调用
     */
    public static void setDebugMode(boolean isDebug){
        JPushInterface.setDebugMode(true);
    }

    /**
     * 是否接收推送
     * @param context 上下文
     * @return true 停止接受推送
     *          false 正在接受推送
     *
     *
     */
    public static boolean isPushStopped(Context context){
        return JPushInterface.isPushStopped(context);
    }

    /**
     * 接受极光推送
     * @param context 上下文
     *
     * 当执行这个方法时，会再次接收到停止接受推送期间没有接收到的推送消息
     */
    public static void receivePush(Context context){
        if (isPushStopped(context)){
            JPushInterface.resumePush(context);
        }
    }

    /**
     * 不接受推送
     * @param context 上下文
     */
    public static void stopPush(Context context){
        if (isPushStopped(context)){
            JPushInterface.stopPush(context);
        }
    }


    /**
     * 设置别名
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     * @param alias     设置的别名
     *                      tips:
     *                          1.每次调用设置有效的别名，覆盖之前的设置
     *                          2.有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|
     *                          3.限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     */
    public static void setAlias(Context context,int sequence,String alias){
        JPushInterface.setAlias(context,sequence,alias);
    }

    /**
     * 设置别名
     * @param context           上下文
     * @param alias             设置的别名
     * @param tagAliasCallbace  设置的别名回调
     *           设置的别名回调
     *              responseCode 返回码
     *                           0 表示调用成功
     *                           6002 设置超时，建议重试
     *                           6003 alias 字符串不合法
     *                           6004 alias超长。最多 40个字节，中文 UTF-8 是 3 个字节
     *                          -996 网络连接断开
     *                          -994 网络连接超时
     *             alias 原设置的别名
     *             tags  原设置的标签
     *         public void gotResult(int responseCode, String alias, Set<String> tags)
     *
     */
    public static void setAlias(Context context,String alias,TagAliasCallback tagAliasCallbace){
        JPushInterface.setAlias(context,alias,tagAliasCallbace);
    }


    /**
     * 删除别名
     * @param context   上下文
     * @param sequenc   用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void deleteAlias(Context context,int sequenc){
        JPushInterface.deleteAlias(context,sequenc);
    }

    /**
     * 查询别名
     * @param context   上下文
     * @param sequenc   用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void getAlias(Context context,int sequenc){
        JPushInterface.getAlias(context,sequenc);
    }


    /**
     * 设置标签 这个api是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置。
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, ，同操作结果一起返回用来标识一次操作的唯一性
     * @param tags      要设置的tag set集合
     *                      tipster：
     *                          1.每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     *                          2.有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符@!#$&*+=.|
     *                          3.限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，且单次操作总长度不得超过5000字节。（判断长度需采用UTF-8编码）
     *                          4.单个设备最多支持设置 1000 个 tag，App 全局 tag 数量无限制
     */
    public static void setTags(Context context, int sequence,Set<String> tags){
        JPushInterface.setTags(context,sequence,tags);
    }


    /**
     * 增加标签
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     * @param tags      要设置的tag set集合 说明参看方法：void setTags(Context context, int sequence,Set<String> tags)
     */
    public static void addTags(Context context, int sequence,Set<String> tags){
        JPushInterface.addTags(context,sequence,tags);
    }

    /**
     * 删除标签
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     * @param tags      要设置的tag set集合 说明参看方法：void setTags(Context context, int sequence,Set<String> tags)
     */
    public static void deleteTags(Context context, int sequence,Set<String> tags){
        JPushInterface.deleteTags(context,sequence,tags);
    }

    /**
     * 清除所有标签
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void cleanTags(Context context, int sequence){
        JPushInterface.cleanTags(context,sequence);
    }

    /**
     * 获取所有标签
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     */
    public static void getAllTags(Context context, int sequence){
        JPushInterface.getAllTags(context,sequence);
    }



    /**
     * 查询指定tag与当前用户绑定的状态
     * @param context   上下文
     * @param sequence  用户自定义的操作序列号, 同操作结果一起返回，用来标识一次操作的唯一性
     * @param tag       要检查的tag 说明参看方法：void setTags(Context context, int sequence,Set<String> tags)
     */
    public static void checkTagBindState(Context context,int sequence,String tag){
        JPushInterface.checkTagBindState(context,sequence,tag);
    }

    /**
     *  设置别名与标签
     * @param context               上下文
     * @param alias                 要设置的alias
     * @param tags                  要设置的tag set集合
     * @param tagAliasCallback      设置回调 参看setAlias(Context context,String alias,TagAliasCallback tagAliasCallbace)
     */
    public static void setAliasAndTags(Context context, String alias, Set<String> tags, TagAliasCallback tagAliasCallback){
        JPushInterface.setAliasAndTags(context,alias,tags,tagAliasCallback);
    }

    /**
     * 清空所有通知
     * @param context   上下文
     */
    public static void clearAllNotifications(Context context){
        JPushInterface.clearAllNotifications(context);
    }

    /**
     * 按照通知id清除通知
     * @param context           上下文
     * @param notificationId    通知id
     */
    public static void clearNotificationById(Context context, int notificationId){
        JPushInterface.clearNotificationById(context,notificationId);
    }

    /**
     * 设置允许推送的时间, 默认情况下用户在任何时间都允许推送,如果不在该时间段内收到消息，当前的行为是：推送到的通知会被扔掉
     * @param context       上下文
     * @param weekDays      0表示星期天，1表示星期一，以此类推。 （7天制，Set集合里面的int范围为0到6）
     * @param startHour     允许推送的开始时间 （24小时制：startHour的范围为0到23）
     * @param endHour       允许推送的结束时间 （24小时制：endHour的范围为0到23）
     */
    public static void setPushTime(Context context, Set<Integer> weekDays, int startHour, int endHour){
        JPushInterface.setPushTime(context,weekDays,startHour,endHour);
    }

    /**
     * 设置静音时段,默认情况下用户在收到推送通知时，客户端可能会有震动，响铃等提示。但用户在睡觉、开会等时间点希望为 "免打扰" 模式，也是静音时段的概念
     * @param context       上下文
     * @param startHour     开始时间，小时：0-23
     * @param startMinute   开始时间，分：0-59
     * @param endHour       结束时间，小时：0-23
     * @param endMinute     结束时间，分：0-59
     */
    public static void setSilenceTime(Context context, int startHour, int startMinute, int endHour, int endMinute){
        JPushInterface.setSilenceTime(context,startHour,startMinute,endHour,endMinute);
    }

    /**
     * 定制默认的通知栏样式，如果不调用此方法定制，则极光Push SDK 默认的通知栏样式是：Android标准的通知栏提示
     * @param builder   默认通知栏样式
     *                      BasicPushNotificationBuilder  用于定制 Android Notification 里的 defaults / flags / icon 等基础样式（行为）
     *                      CustomPushNotificationBuilder 继承 BasicPushNotificationBuilder 进一步让开发者定制 Notification Layout
     *                      MultiActionsNotificationBuilder 继承 DefaultPushNotificationBuilder 进一步让开发者定制 Notification Layout
     */
    public static void setDefaultPushNotificationBuilder(DefaultPushNotificationBuilder builder){
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    /**
     * 设置多个通知栏构建类,当开发者需要为不同的通知，指定不同的通知栏样式（行为）时，则需要调用此方法
     * @param notificationBuilderId 开发者自己维护 notificationBuilderId 这个编号，
     *                              下发通知时使用 n_builder_id 指定该编号，从而 Push SDK 会调用开发者应用程序里设置过的指定编号的通知栏构建类，来定制通知栏样式。
     * @param builder   通知栏样式 参见void setDefaultPushNotificationBuilder(DefaultPushNotificationBuilder builder)
     */
    public static void setPushNotificationBuilder(Integer notificationBuilderId, BasicPushNotificationBuilder builder){
        JPushInterface.setPushNotificationBuilder(notificationBuilderId,builder);
    }


    /**
     * 限制保留的通知条数,默认为保留最近 5 条通知
     * @param context   应用的 ApplicationContext
     * @param maxNum    最多显示通知的条数
     */
    public static void setLatestNotificationNumber(Context context, int maxNum){
        JPushInterface.setLatestNotificationNumber(context,maxNum);
    }
}
