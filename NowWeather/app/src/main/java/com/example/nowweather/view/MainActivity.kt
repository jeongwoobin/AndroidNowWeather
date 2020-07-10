package com.example.nowweather.view

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.nowweather.data.DayWeather
import com.example.nowweather.service.RetrofitService
import com.example.nowweather.service.RetrofitUtil
import android.location.Address
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.nowweather.R
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), LocationListener {
    private var locationManager: LocationManager? = null
    private var service: RetrofitService = RetrofitUtil.getService()
    private var loadingLocation: ProgressDialog? = null
    private var loadingApi: ProgressDialog? = null
    private var item: DayWeather? = null
    private var city = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DEBUG", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()

        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager != null) {
            Log.d("DEBUG", "LocationManager not-null")

            requestLocation()
        } else {
            Log.d("DEBUG", "LocationManager null")
        }

        tvDaily.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        tvWeekly.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey6))
        dailyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back)
        weeklyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back_not_selected)
        setButton()
    }

    private fun setViewPager() {
        val listener = object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> onDailyClicked()
                    1 -> onWeeklyClicked()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
        }

        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager, item, city)
        viewPager.removeOnPageChangeListener(listener)
        viewPager.addOnPageChangeListener(listener)
    }

    private fun setButton() {
        dailyTab.setOnClickListener {
            viewPager.currentItem = 0
        }

        weeklyTab.setOnClickListener {
            viewPager.currentItem = 1
        }
    }

    private fun onDailyClicked() {
        tvDaily.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        tvWeekly.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey6))
        dailyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back)
        weeklyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back_not_selected)
    }

    private fun onWeeklyClicked() {
        tvDaily.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey6))
        tvWeekly.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        dailyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back_not_selected)
        weeklyTab.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_back)
    }

    override fun onLocationChanged(location: Location) {
        /*현재 위치에서 위도경도 값을 받아온뒤 우리는 지속해서 위도 경도를 읽어올것이 아니니
        날씨 api에 위도경도 값을 넘겨주고 위치 정보 모니터링을 제거한다.*/
        Log.d("DEBUG", "onLocationChanged")

        val g = Geocoder(applicationContext)
        val addr: List<Address> = g.getFromLocation(location.latitude, location.longitude, 1)
        city = "${addr[0].adminArea} ${addr[0].thoroughfare}"
        Log.d("DEBUG", addr.toString())

        //날씨 가져오기 통신
        getWeather(location.latitude, location.longitude)
        //위치정보 모니터링 제거
        locationManager?.removeUpdates(this)
        loadingLocation?.dismiss()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }
    override fun onProviderEnabled(provider: String) {
//        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
//        super.onProviderDisabled(provider)
    }

    private fun requestLocation() {
        Log.d("DEBUG", "requestLocation")
        loadingLocation = ProgressDialog.show(this, "위치 확인중", "최초 실행 시 시간이 걸릴 수 있습니다.\n잠시만 기다려주세요")
        //사용자로 부터 위치정보 권한체크
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String?>(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else {
            Log.d("DEBUG", "Location updates")
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1f, this)
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1f, this)
        }
    }

    private fun getWeather(latitude: Double, longitude: Double) {
        Log.d("DEBUG", "getWeather")
        loadingApi = ProgressDialog.show(this, "API 호출중", "잠시만 기다려주세요")
        val call: retrofit2.Call<DayWeather>? = service.getHourly(RetrofitUtil.APPKEY, latitude, longitude, RetrofitUtil.UNITS, RetrofitUtil.EXCLUDE)
        call?.enqueue(object : Callback<DayWeather> {
            override fun onFailure(call: retrofit2.Call<DayWeather>, t: Throwable) {
                Log.d("Debug", t.toString())
                loadingApi?.dismiss()
            }

            override fun onResponse(call: retrofit2.Call<DayWeather>, response: Response<DayWeather>) {
                if (response.isSuccessful) {
                    //날씨데이터를 받아옴
                    val json: DayWeather? = response.body()
                    Log.d("DEBUG", response.body().toString())
                    item = json
                    setViewPager()
                    loadingApi?.dismiss()
                    if((item?.daily?.get(0)?.weather?.get(0)?.id?.toInt())?.div(100) == 3 ||
                            (item?.daily?.get(0)?.weather?.get(0)?.id?.toInt())?.div(100) == 5) {
                        RainDialog(this@MainActivity).show()
                    }
                }
                else {
                    loadingApi?.dismiss()
                }
            }
        })
    }


}