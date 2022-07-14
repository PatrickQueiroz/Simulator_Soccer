package me.dio.simulator.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.Random;

import me.dio.simulator.R;
import me.dio.simulator.data.MatchesApi;
import me.dio.simulator.databinding.ActivityMainBinding;
import me.dio.simulator.domain.Match;
import me.dio.simulator.ui.adapter.Matchesadapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private Matchesadapter matchesAdapter = new Matchesadapter(Collections.emptyList());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setUpMatchesList();
        setUpMatchesRefresh();
        setUpFloatingActionButton();
    }

    private void setupHttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://patrickqueiroz.github.io/matches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesApi = retrofit.create(MatchesApi.class);
    }

    private void setUpMatchesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMatches.setAdapter(matchesAdapter);
        findMatchesFromApi();
    }

    private void setUpMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi);
    }

    private void setUpFloatingActionButton() {
        binding.fabSimulate.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animation){
                    Random random = new Random();
                    for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                        Match match = matchesAdapter.getMatches().get(0);
                        match.getHometeam().setScore(random.nextInt(match.getHometeam().getStars() + 1));
                        match.getAwayteam().setScore(random.nextInt(match.getAwayteam().getStars() + 1));
                        matchesAdapter.notifyItemChanged(0);

                    }
        };
                {
                    Random random = new Random();
                    for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                        Match match = matchesAdapter.getMatches().get(1);
                        match.getHometeam().setScore(random.nextInt(match.getHometeam().getStars() + 1));
                        match.getAwayteam().setScore(random.nextInt(match.getAwayteam().getStars() + 1));
                        matchesAdapter.notifyItemChanged(1);

                    }
                };
                {
                    Random random = new Random();
                    for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                        Match match = matchesAdapter.getMatches().get(2);
                        match.getHometeam().setScore(random.nextInt(match.getHometeam().getStars() + 1));
                        match.getAwayteam().setScore(random.nextInt(match.getAwayteam().getStars() + 1));
                        matchesAdapter.notifyItemChanged(2);

                    }
                }
            });
        });
    }

    private void findMatchesFromApi() {
        binding.srlMatches.setRefreshing(true);
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();
                    matchesAdapter = new Matchesadapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showErrorMesage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMesage();
                binding.srlMatches.setRefreshing(false);
            }

        });
    }

    private void showErrorMesage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }
}
