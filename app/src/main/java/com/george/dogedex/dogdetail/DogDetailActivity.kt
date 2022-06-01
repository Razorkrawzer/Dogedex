package com.george.dogedex.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import coil.load
import com.george.dogedex.Dog
import com.george.dogedex.R
import com.george.dogedex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object{
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)

        if (dog == null){
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dog = dog
        binding.dogImage.load(dog.imageUrl)
        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}