package com.uesvaz.pr14_comunicacionsambwebsockets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MainActivity : AppCompatActivity() { // Ejecutar el servidor
    //mvn exec:java "-Dexec.mainClass=com.uesvaz.servidor.ServidorWebSocket"

    private lateinit var wsclient: MyWebSocketClient
    private lateinit var messagesView: TextView
    private lateinit var messagesScroll: ScrollView
    private lateinit var messageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messagesView = findViewById(R.id.messagesView)
        messagesScroll = findViewById(R.id.messagesScroll)
        messageInput = findViewById(R.id.messageInput)
        val username = intent.getStringExtra("username") //Recibimos el username de la otra vista

        findViewById<Button>(R.id.sendButton).setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty() && ::wsclient.isInitialized && wsclient.isOpen) {
                wsclient.send(username + ": " + message)
                messageInput.text.clear()
            } else if (message.isNotEmpty()) {
                Toast.makeText(this, "No hay conexión con el servidor", Toast.LENGTH_SHORT).show()
            }
        }

        connectaWS()
    }

    private inner class MyWebSocketClient(uri: URI) : WebSocketClient(uri) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            runOnUiThread {
                messagesView.append("Conectado al servidor\n")
            }
        }

        override fun onMessage(message: String?) {
            runOnUiThread {
                messagesView.append("$message\n")
                messagesScroll.post {
                    messagesScroll.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            runOnUiThread {
                messagesView.append("Conexión cerrada\n")
            }
        }

        override fun onError(ex: Exception?) {
            runOnUiThread {
                messagesView.append("Error: ${ex?.message}\n")
            }
        }
    }

    private fun connectaWS() {
        wsclient = MyWebSocketClient(URI("ws://10.0.2.2:8887"))
        wsclient.connect()
    }

    override fun onDestroy() {
        if (::wsclient.isInitialized) {
            wsclient.close()
        }
        super.onDestroy()
    }
}