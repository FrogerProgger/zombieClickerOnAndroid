package com.example.zombieclicker.ui.slideshow;

import static android.content.Context.MODE_PRIVATE;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zombieclicker.R;
import com.example.zombieclicker.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private SharedPreferences preferences;
    ImageButton killButton;
    ValueAnimator animatorZombie;
    MediaPlayer mediaPlayer;
    TextView researchStatistic, zombieStatistic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        killButton = root.findViewById(R.id.killButton);
        researchStatistic = root.findViewById(R.id.textResearchStatistic);
        zombieStatistic = root.findViewById(R.id.textZombieStatistic);
        preferences = root.getContext().getSharedPreferences("ALL_INFO", MODE_PRIVATE);

        mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.krikzombi);
        killButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorZombie.start();
                mediaPlayer.start();
                Save("countOfZombie", 0);
                Save("countOfResearch", 0);
                Save("researchIncome", 0);
                Save("zombieIncome", 0);
                zombieStatistic.setText(getString(R.string.zombieIncome) + " " + "0");
                researchStatistic.setText(getString(R.string.researchIncome) + " " + "0");
            }
        });

        animatorZombie = ValueAnimator.ofFloat(0.7f, 1);
        animatorZombie.setDuration(500L);
        animatorZombie.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                killButton.setScaleX((Float) valueAnimator.getAnimatedValue());
                killButton.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void Save(String category, int integer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(category, integer);

        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        String incomeResearch = Integer.toString(preferences.getInt("researchIncome", 0));
        String incomeZombie = Integer.toString(preferences.getInt("zombieIncome", 0));

        zombieStatistic.setText(getString(R.string.zombieIncome) + " " + incomeZombie);
        researchStatistic.setText(getString(R.string.researchIncome) + " " + incomeResearch);
    }
}