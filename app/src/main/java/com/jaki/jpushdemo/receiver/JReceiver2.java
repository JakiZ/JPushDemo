package com.jaki.jpushdemo.receiver;

import android.content.Context;

import java.util.Set;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by Administrator on 2017/12/20.
 */

public class JReceiver2 extends JPushMessageReceiver {


    /**
     * tag增删查改的操作会在此方法中回调结果
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);

        Set<String> tags = jPushMessage.getTags();
        for (String tag : tags) {

        }


    }


    /**
     *查询某个tag与当前用户的绑定状态的操作会在此方法中回调结果。
     * @param context
     * @param jPushMessage
     */

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);


    }


    /**
     *
     * alias相关的操作会在此方法中回调结果。
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);


    }
}
