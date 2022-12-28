package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var downloadID: Long =0
    private var URL =""
    private var CLICKED="Hello"
    private var CHECK ="Failed"
    private val CHANNEL_ID = "channelId"
    private val notfication_ID = 1

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createNotificationChannel()// make channel
        Download_btn.setOnClickListener {
            download()
            stepCircle()
            stepBar()
        }


    }

     fun stepCircle(){
        GlobalScope.launch {
            for(i in 1.. 18) {
                delay(100)
                circle.step +=20
            }
            circle.step =0
        }
    }
    fun stepBar(){
        GlobalScope.launch {
            for(i in 1.. 20) {
                delay(100)
                bar.step +=55
            }
            bar.step=0
        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.glid_link ->
                    if (checked) {
                        URL = "https://github.com/bumptech/glide"
                        Toast.makeText(this,"Glide Clicked",Toast.LENGTH_SHORT).show()
                        CLICKED = "Glide"
                    }
                R.id.loadApp_link ->
                    if (checked) {
                        URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"
                        Toast.makeText(this,"LoadApp Clicked",Toast.LENGTH_SHORT).show()
                        CLICKED = "LoadApp"
                    }
                R.id.retrofit_link ->
                    if (checked) {
                        URL = "https://github.com/square/retrofit"
                        Toast.makeText(this,"Retrofit Clicked",Toast.LENGTH_SHORT).show()
                        CLICKED = "Retrofit"
                    }
            }
        }
    }
    
    //ask
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    // download url
    private fun download() {
        try {
            if(URL==""){
                Toast.makeText(this,"Please select the file to download",Toast.LENGTH_SHORT).show()
            }
            val request =
                DownloadManager.Request(Uri.parse(URL))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                downloadManager.enqueue(request)// enqueue puts the download request in the queue.

            CHECK = "Successful"
            sendNotfcation()//send notification
        }catch (e:Exception){
            CHECK ="Failed"
            sendNotfcation()
            print("error exists when trying to download "+e)
        }


    }

    // create channel
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val describtionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = describtionText
            }
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    //send the notification
    private fun sendNotfcation(){
        val intent  = Intent(this,DetailActivity::class.java).also {
            it.putExtra("name", CLICKED)
            it.putExtra("check", CHECK)
        }

        val pendingIntent:PendingIntent = PendingIntent.getActivity(this,notfication_ID,intent,0)

        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Udacity: Android Kotlin Nanodegree")
//            .setLargeIcon(bitmapLarge)// right large
            .setStyle(NotificationCompat.BigTextStyle().bigText("Project 3 repository is downloaded"))
//            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notfication_ID,builder.build())
        }
    }

}
