package com.example.rssreader

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

//　チャンネルID
private const val CHANNEL_ID = "update_channel"
// 通知ID
private const val NOTIFICATION_ID = 1
// PendingIntent用のリクエストコード
private const val REQUEST_CODE = 1

// 通知チャンネルを作成する
fun createChannel(context: Context) {

    //通知チャンネルを作成する
    val channel = NotificationChannel(
        CHANNEL_ID, // チャンネルID
        "新規記事",//チャンネル名
        NotificationManager.IMPORTANCE_DEFAULT // チャンネルの重要度
    ).apply {
        enableLights(true) // LED光らせるか
        enableVibration(true) // バイブレーションを行うか
        setShowBadge(true) // アイコンにバッジをつけるか
    }

    //端末にチャンネルを登録する
    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(channel)
}

//最新通知を行う
fun notifyUpdate(context: Context) {
    //通知ををタップした時に起動する画面
    val intent = Intent(context, MainActivity::class.java)
    //通知に設定するために、PendingIntentにする
    val pendingIntent = PendingIntent.getActivity(
        context, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT)

    //通知を製作する
    val notification =  NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("記事が更新されました")
        .setContentText("新しい記事をチェックしましょう")
        .setContentIntent(pendingIntent) //通知をタップ時に起動するIntent
        .setSmallIcon(R.drawable.ic_notification)
        .setAutoCancel(true) //通知をタップしたら、その通知を消す
        .build()

    //通知する
    NotificationManagerCompat.from(context).notify(
        NOTIFICATION_ID, notification)
}