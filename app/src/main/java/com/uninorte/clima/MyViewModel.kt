package com.uninorte.clima
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uninorte.clima.data.Ciudad

class MyViewModel : ViewModel() {
    private val cityList = mutableListOf<Ciudad>()
    private val cities = MutableLiveData<List<Ciudad>>()

    init{
        cities.value = cityList
    }

    fun getCities(): MutableLiveData<List<Ciudad>>{
        return cities
    }


}


