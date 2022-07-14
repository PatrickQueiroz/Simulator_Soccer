package me.dio.simulator.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.dio.simulator.databinding.MatchItemBinding;
import me.dio.simulator.domain.Match;
import me.dio.simulator.ui.DetailActivity;

public class Matchesadapter extends RecyclerView.Adapter<Matchesadapter.ViewHolder> {

    private final List<Match> matches;

    public Matchesadapter(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Match match = matches.get(position);

        // Adapta os dados da partida (recuperada da API) para nosso layout.
        Glide.with(context).load(match.getHometeam().getImage()).circleCrop().into(holder.binding.ivHomeTeam);
        holder.binding.tvHomeTeamName.setText(match.getHometeam().getName());
        if (match.getHometeam().getScore() != null) {
            holder.binding.tvHomeTeamScore.setText(String.valueOf(match.getHometeam().getScore()));
        }
        Glide.with(context).load(match.getAwayteam().getImage()).circleCrop().into(holder.binding.ivAwayTeam);
        holder.binding.tvAwayTeamName.setText(match.getAwayteam().getName());
        if (match.getAwayteam().getScore() != null) {
            holder.binding.tvAwayTeamScore.setText(String.valueOf(match.getAwayteam().getScore()));
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.Extras.MATCH, match);
            context.startActivity(intent);
        });
        }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}