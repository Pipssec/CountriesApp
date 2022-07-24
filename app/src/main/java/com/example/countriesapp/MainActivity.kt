package com.example.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.countriesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val countryName = binding.countryNameEditText.text.toString()

            lifecycleScope.launch{
                try {
                    val countries = restCountriesApi.getCountryByName(countryName)
                    val country = countries[0]
                    binding.countryNameTextView.text = country.name
                    binding.capitalTextView.text = country.capital
                    binding.populationTextView.text = numberFormat(country.population)
                    binding.areaTextView.text = numberFormat(country.area)
                    binding.languageTextView.text = languagesToString(country.languages)

                    binding.resultLayout.visibility = View.VISIBLE
                    binding.statusLayout.visibility = View.INVISIBLE
                }catch (e: Exception){
                    binding.statusTextView.text =  "Страна не найдена"
                    binding.statusImageView.setImageResource(R.drawable.ic_baseline_error_24)
                    binding.resultLayout.visibility = View.INVISIBLE
                    binding.statusLayout.visibility = View.VISIBLE
                }
            }


        }
    }

}

fun languagesToString(languages: List<Languages>): String {
    var result = ""

    for ((index, language) in languages.withIndex())
        if (index != languages.lastIndex)
            result += "${language.name}, "
        else
            result += language.name
    return result
}

fun numberFormat(number: Long): String{
    val string = NumberFormat.getInstance().format(number)
    return string
}

