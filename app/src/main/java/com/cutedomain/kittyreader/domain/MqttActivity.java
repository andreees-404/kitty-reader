package com.cutedomain.kittyreader.domain;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cutedomain.kittyreader.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;

public class MqttActivity extends AppCompatActivity {

    private String TAG = "MqttActivity";


    private String clientId = "";
    // token -> BXn84riG9h8pSwgF
    // link -> mqtt://kittyreader:BXn84riG9h8pSwgF@kittyreader.cloud.shiftr.io
    private static final String mHost = "tcp://kittyreader.cloud.shiftr.io";
    private static String mUser = "kittyreader";
    private static String mPass = "BXn84riG9h8pSwgF";


    private MqttAndroidClient client;
    private MqttConnectOptions options;

    private static String topic = "led";
    private static String topicMsgOn = "Turn on";
    private static String topicMsgOff = "Turn off";


    private boolean pubishPermsission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);

        Button btnOn = findViewById(R.id.btnOn);
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(topic, topicMsgOn);
            }
        });

        Button btnOff = findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(topic, topicMsgOff);
            }
        });

        /* Obtener datos del cliente mqtt */
        getClientName();
        connectBroker();
    }

    private Boolean checkConnection() {
        if (!client.isConnected()) {
            connectBroker();
        }
        return client.isConnected();

    }


    private void connectBroker() {
        this.client = new MqttAndroidClient(this.getApplicationContext(), mHost, clientId);
        this.options = new MqttConnectOptions();
        this.options.setUserName(mUser);
        this.options.setPassword(mPass.toCharArray());
        try {
            IMqttToken token = this.client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MqttActivity.this, "Connected!", Toast.LENGTH_SHORT).show();
                    subTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MqttActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "connectBroker: " + e.getMessage());
        }
    }

    private void getClientName() {
        String manufacturer = Build.MANUFACTURER;
        String modelName = Build.MODEL;
        clientId = manufacturer + " " + modelName;

        TextView txtClientId = findViewById(R.id.txtClientId);
        txtClientId.setText(clientId);
    }

    private void sendMessage(String topic, String msg) {
        if (checkConnection()) {
            try {
                int qos = 0;
                client.publish(topic, msg.getBytes(), qos, false);
                Toast.makeText(this, topic + ": " + msg, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "sendMessage: " + e.getMessage());
            }
        }
    }


    private void subTopic() {
        try {
            client.subscribe(topic, 0);
        } catch (MqttSecurityException e) {
            throw new RuntimeException(e);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(MqttActivity.this, "Server desconected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                TextView txtInfo = findViewById(R.id.txtInfo);
                if (topic.matches(topic)){
                    String msg  = new String(message.getPayload());
                    if (msg.matches(topicMsgOn)){
                        txtInfo.setText(msg); txtInfo.setBackgroundColor(GREEN);
                    }
                    else if (msg.matches(topicMsgOff)){ txtInfo.setText(msg); txtInfo.setBackgroundColor(RED);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Toast.makeText(MqttActivity.this, "Message sended!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}