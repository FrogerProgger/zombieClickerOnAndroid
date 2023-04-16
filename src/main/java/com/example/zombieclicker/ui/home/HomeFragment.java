package com.example.zombieclicker.ui.home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zombieclicker.MyThread;
import com.example.zombieclicker.R;
import com.example.zombieclicker.databinding.FragmentHomeBinding;

import java.util.Random;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private SharedPreferences preferences;
    private TextView zombieCountTextView;
    private TextView researchCountTextView;
    private int zombieCount = 0;
    private int researchCount = 0;
    private ImageButton researchButton;
    private ImageButton zombieButton;
    private ImageButton brainButton;
    private int brainStrong = 1;
    private MediaPlayer mediaPlayerZombie;
    private MediaPlayer mediaPlayerResearch;
    private ValueAnimator animatorResearch;
    private ValueAnimator animatorZombie;
    MyThread myThread;
    private int countOfIncome;

    private int counterRes = 0;
    private int counterZom = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences = root.getContext().getSharedPreferences("ALL_INFO", Context.MODE_PRIVATE);
        zombieButton = root.findViewById(R.id.getZombie);
        zombieButton.setOnClickListener(zombieListener);
        zombieCount = preferences.getInt("countOfZombie", 0);
        zombieCountTextView = root.findViewById(R.id.countZombieText);
        zombieCountTextView.setText(Integer.toString(zombieCount));

        researchButton = root.findViewById(R.id.getResearch);
        researchButton.setOnClickListener(researchListener);
        researchCountTextView = root.findViewById(R.id.countResearchText);
        researchCount = preferences.getInt("countOfResearch", 0);
        researchCountTextView.setText(Integer.toString(researchCount));

        brainButton = root.findViewById(R.id.brain);
        brainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (researchCount >= 5000 * brainStrong) {
                    researchCountTextView.setText(Integer.parseInt(researchCountTextView.getText().toString()) - 5000 * brainStrong);
                    brainStrong++;
                } else {
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setText("Вам не хватает УЧЕНЫХ!");
                    toast.show();
                }
            }
        });

        mediaPlayerZombie = MediaPlayer.create(this.getActivity(), R.raw.zombiesound);
        mediaPlayerResearch = MediaPlayer.create(this.getActivity(), R.raw.research);


        animatorResearch = ValueAnimator.ofFloat(0.7f, 1);
        animatorResearch.setDuration(500L);
        animatorResearch.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                researchButton.setScaleX((Float) valueAnimator.getAnimatedValue());
                researchButton.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });

        animatorZombie = ValueAnimator.ofFloat(0.7f, 1);
        animatorZombie.setDuration(500L);
        animatorZombie.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                zombieButton.setScaleX((Float) valueAnimator.getAnimatedValue());
                zombieButton.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });

        countOfIncome++;
        if (countOfIncome == 1) {
            myThread = new MyThread(this.getActivity(), preferences, true);
            myThread.run();
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        myThread.stopTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        myThread.startTask();
    }


    private void Save(String category, int integer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(category, integer);

        editor.apply();
    }

    View.OnClickListener researchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counterRes++;
            animatorResearch.start();
            Random random = new Random();
            System.out.println("Counter Res = " + counterRes);
            if (counterRes >= random.nextInt(7) + 1) {
                mediaPlayerResearch.start();
                counterRes = 0;
            }
            researchCount += brainStrong;
            Save("countOfResearch", researchCount);
            researchCountTextView.setText(Integer.toString(researchCount));
        }
    };

    View.OnClickListener zombieListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counterZom++;
            animatorZombie.start();
            Random random = new Random();
            if (counterZom >= random.nextInt(10) + 1) {
                mediaPlayerZombie.start();
                counterZom = 0;
            }
            zombieCount += brainStrong;
            Save("countOfZombie", zombieCount);
            zombieCountTextView.setText(Integer.toString(zombieCount));
        }
    };


}