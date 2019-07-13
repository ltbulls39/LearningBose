package com.hernandez.learningbose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.ArraySet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bose.blecore.BluetoothManager
import com.bose.blecore.ScanError
import com.bose.blecore.Session
import com.bose.bosewearableui.DeviceConnectorActivity
import com.bose.wearable.sensordata.GestureIntent
import com.bose.wearable.sensordata.SensorIntent
import com.bose.wearable.services.wearablesensor.GestureType
import com.bose.wearable.services.wearablesensor.SamplePeriod
import com.bose.wearable.services.wearablesensor.SensorType
import java.util.*
import com.bose.wearable.wearabledevice.WearableDevice
import com.bose.wearable.BoseWearable



class HomeFragment : Fragment() {
    val REQUEST_CODE_CONNECTOR: Int = 1
    val AUTO_CONNECT_TIMEOUT: Int = 5
    val PREF_AUTO_CONNECT_ENABLED: String = "auto-connect-enabled"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val connectButton: Button = view.findViewById(R.id.connectButton)

        connectButton.setOnClickListener { onConnectClicked() }

    }

    fun onConnectClicked() {
        val sensorIntent: SensorIntent = SensorIntent(Collections.singleton(SensorType.ROTATION_VECTOR), Collections.singleton(SamplePeriod._20_MS))
        val gestureIntent: GestureIntent = GestureIntent.EMPTY

        val intent: Intent = DeviceConnectorActivity.newIntent(requireContext(), AUTO_CONNECT_TIMEOUT, sensorIntent, gestureIntent)
        startActivityForResult(intent, REQUEST_CODE_CONNECTOR)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CONNECTOR) {
            say("Request Code Connector")
            if (resultCode == Activity.RESULT_OK) {
                say("Result OK")
                val deviceAddress: String? = data?.getStringExtra(DeviceConnectorActivity.CONNECTED_DEVICE)
                if (deviceAddress == null) {
                    showNoDeviceError()
                } else {
                    // continue to new fragment with new information
                    val btManager: BluetoothManager = BoseWearable.getInstance().bluetoothManager()
                    val session: Session = btManager.session(btManager.deviceByAddress(deviceAddress)!!)
                    val wearableDevice: WearableDevice = session.device() as WearableDevice
                    showDeviceSuccess(deviceAddress)

                }



            } else if (resultCode == DeviceConnectorActivity.RESULT_SCAN_ERROR) {
                say("Scan Error")
                val scanError: ScanError = data?.getSerializableExtra(DeviceConnectorActivity.FAILURE_REASON) as ScanError

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showDeviceSuccess(device_info: String) {
        val context = context
        if (context != null) {
            Toast.makeText(context, device_info, Toast.LENGTH_SHORT).show()
        }
    }

    private fun say(code: String) {
        if (context != null) {
            Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNoDeviceError() {
        val context = context
        if (context != null) {
            Toast.makeText(
                context, "Can not continue without Bose AR, please select a device",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }


}