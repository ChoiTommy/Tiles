package com.kaito.tiles

//https://github.com/stedi-akk/PowerOffClick
import android.app.Activity
import android.media.AudioManager
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread(Runnable {
            /*try {
                startActivity(packageManager.getLaunchIntentForPackage("org.thunderdog.challegram"))
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Telegram X not installed.\n$e", Toast.LENGTH_SHORT).show()
            }

            try {
                val process = Runtime.getRuntime().exec("su")
                val os = DataOutputStream(process.outputStream)
                os.writeBytes("input keyevent 26" + "\n")
                os.writeBytes("exit\n")
                os.flush() //https://stackoverflow.com/questions/33529933/how-to-run-root-commands-from-android-application
            } catch (e: Exception) {
                val toast = Toast.makeText(this, "failed", Toast.LENGTH_LONG)
                toast.show()
            }*/
            val am = this.getSystemService(AUDIO_SERVICE) as AudioManager
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
        }).start()
        Toast.makeText(this, "Here to protect your hardware volume buttons", Toast.LENGTH_LONG).show()
        finish()
    }
}