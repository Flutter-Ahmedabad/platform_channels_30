package com.example.platform_channels_30
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import io.flutter.plugin.common.MethodChannel

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import java.time.LocalDateTime
import java.util.*

class MainActivity: FlutterActivity() {
      private val CHANNEL = "method_channel_1/messageChannel"
      private val  COUNTER_CHANNEL = "EventChannel/counter";

  override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
    super.configureFlutterEngine(flutterEngine)
    MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
      call, result ->
      Log.i("MESSAGE_TAG","The message")
     if (call.method == "displayMessage"){
      var resultValue =  processMessage(call.arguments<String>())
       result.success(resultValue);
     }

    }

      EventChannel(flutterEngine.dartExecutor,COUNTER_CHANNEL).setStreamHandler(
              object : StreamHandler{
                  @RequiresApi(Build.VERSION_CODES.O)
                  override fun onListen(arguments: Any?, events: EventSink?) {
//                      TODO("Not yet implemented")


                      val timer = object: CountDownTimer(20000, 1000) {
                          override fun onTick(millisUntilFinished: Long) {
                              events?.success(millisUntilFinished)
                          }

                          override fun onFinish() {

                          }
                      }
                      timer.start()


                  }

                  override fun onCancel(arguments: Any?) {
                      TODO("Not yet implemented")
                  }

              }

      )
  }

  fun processMessage(msg: String): Boolean {
    Log.i("MESSAGE_TAG",msg)
    return true
  }


}
