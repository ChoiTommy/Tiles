package com.kaito.tiles
//https://www.youtube.com/watch?v=tXHWyt8g5jc
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast

class PanelTileService : TileService() {

    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notify("Authentication error: $errString")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notify("Your fingerprint data will be sent back to my secret server. Good luck!")
                    qsTile.state = if (qsTile.state == Tile.STATE_ACTIVE) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
                    qsTile.updateTile()
                }
            }

    override fun onClick() {
        val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Give me your fingerprint")
                .setDescription("Place your finger on the scanner.")
                .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener{ _, _ ->
                    notify("Authentication cancelled.")
                }).build()
        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
    }

    override fun onTileAdded() {
        super.onTileAdded()
        checkBiometricSupport()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    private fun getCancellationSignal(): CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notify("Authentication was cancelled by the user.")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport(): Boolean{
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure){
            notify("Biometric authentication has not been enabled in settings.")
            return false
        }

        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notify("Biometric authentication permission is not enabled.")
            return false
        }*/
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) true else true
    }

    private fun notify(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
