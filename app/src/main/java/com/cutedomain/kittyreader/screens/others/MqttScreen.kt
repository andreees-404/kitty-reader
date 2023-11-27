package com.cutedomain.kittyreader.screens.others

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.cutedomain.kittyreader.domain.MqttActivity

@Composable
fun MqttScreen() {
    Log.d("MqttScreen", "MqttScreen: Intent to Mqtt Activity...")

    val context = LocalContext.current
    Intent(context, MqttActivity::class.java).also {
        context.startActivity(it)
    }
}