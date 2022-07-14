package me.dio.simulator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import me.dio.simulator.databinding.ActivityDetailBinding
import me.dio.simulator.domain.Match

class DetailActivity : AppCompatActivity() {

    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name

            binding.tvDescription.text = it.description

            Glide.with(this).load(it.hometeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.hometeam.name
            binding.rbHomeTeamStars.rating = it.hometeam.stars.toFloat()
            if (it.hometeam.score != null) {
                binding.tvHomeTeamScore.text = it.hometeam.score.toString()
            }

            Glide.with(this).load(it.awayteam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.awayteam.name
            binding.rbAwayTeamStars.rating = it.awayteam.stars.toFloat()
            if (it.awayteam.score != null) {
                binding.tvAwayTeamScore.text = it.awayteam.score.toString()
            }



        }
    }
}