package com.android.sunnyweather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.android.sunnyweather.databinding.ForecastItemBinding
import com.android.sunnyweather.databinding.FragmentWeatherBinding
import com.android.sunnyweather.viewmodel.WeatherViewModel
import com.android.sunnyweather.viewmodel.WeatherViewModelFactory
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var mBinding: FragmentWeatherBinding
    private val args : WeatherFragmentArgs by navArgs()

    private val mViewModel : WeatherViewModel by lazy {
        WeatherViewModelFactory(args.placeName,args.lat,args.lng).create(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWeatherBinding.inflate(layoutInflater,container,false).run {
        mBinding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this
        mViewModel.refreshWeather()

        mViewModel.weather.observe(viewLifecycleOwner, Observer {
            //life index布局
            mBinding.lifeIndex.coldRiskStatus.text = it.daily.lifeIndex.coldRisk[0].desc
            mBinding.lifeIndex.dressStatus.text = it.daily.lifeIndex.dressing[0].desc
            mBinding.lifeIndex.carWashStatus.text = it.daily.lifeIndex.carWashing[0].desc
            mBinding.lifeIndex.ultravioletStatus.text = it.daily.lifeIndex.ultraviolet[0].desc
            //forecast
            val forecastContainer = mBinding.layoutForecast.forecastItemContainer
            val days = it.daily.skycon.size
            for (i in 0 until days){
                val itemBinding = ForecastItemBinding
                    .inflate(LayoutInflater.from(forecastContainer.context),forecastContainer,false)
                val simpleDateFormat = SimpleDateFormat("MM-dd", Locale.CHINA)
                itemBinding.forecastDate.text = simpleDateFormat.format(it.daily.skycon[i].date)
                Glide.with(this).load(getSky(it.daily.skycon[i].value).icon).into(itemBinding.forecastImage)
                itemBinding.forecastSkycon.text = getSky(it.daily.skycon[i].value).info
                val forecastTemperature = "${it.daily.temperature[i].min}~${it.daily.temperature[i].max}℃"
                itemBinding.forecastTemperature.text = forecastTemperature
                forecastContainer.addView(itemBinding.root)
            }
        })

        mViewModel.weatherFailure.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,"获取失败",Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onViewCreated: $it")
        })
    }

    companion object{
        const val TAG = "WeatherFragment"
    }

}