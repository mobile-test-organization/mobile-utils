package br.com.spotsales.utils.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.spotsales.utils.SecurityUtils

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        Log.d("HASH TEST", "INICIAL")

        val a = SecurityUtils.aesEncode("cf2acfb6-747c-4870-b610-64d2914742ac+17629+18940+6.0+[1]+1577998118+-0300+f7474a66-5d01-43b7-88d6-f4f2c1972020+4", "1aqwsedrftgyhuji")

        Log.d("HASH TEST", a)

        val b = SecurityUtils.aesDecode(a.orEmpty(), "1aqwsedrftgyhuji")

        Log.d("HASH TEST", b)
    }
}
