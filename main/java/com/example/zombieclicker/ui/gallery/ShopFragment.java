package com.example.zombieclicker.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zombieclicker.CourseAdapter;
import com.example.zombieclicker.CourseModel;
import com.example.zombieclicker.R;
import com.example.zombieclicker.databinding.FragmentShopBinding;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentShopBinding binding;

    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel galleryViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.idRVCourse);
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();

        preferences = root.getContext().getSharedPreferences("ALL_INFO", Context.MODE_PRIVATE);
        int zombieIncome = preferences.getInt("zombieIncome", 0);
        int researchIncome = preferences.getInt("researchIncome", 0);

        int allIncome = zombieIncome + researchIncome;

        int[] prices = new int[]{100, 500, 700, 1200, 1500};
        String[] newPrices = new String[prices.length];
        for (int i = 0; i < prices.length; i++)
            newPrices[i] = Integer.toString((int) Math.ceil(allIncome % 100 - prices[i]/156) + prices[i]);


        courseModelArrayList.add(new CourseModel(newPrices[0], "1", R.drawable.sciensisticon, R.drawable.zombieicon, "Поймать ученого на улице"));
        courseModelArrayList.add(new CourseModel(newPrices[0], "1", R.drawable.zombieicon, R.drawable.sciensisticon, "Отправить практикантов на исследование зомби\nНадеюсь, что справятся"));
        courseModelArrayList.add(new CourseModel(newPrices[1], "10", R.drawable.zombieicon, R.drawable.sciensisticon, "Дать ученым 'попить'"));
        courseModelArrayList.add(new CourseModel(newPrices[1], "10", R.drawable.sciensisticon, R.drawable.zombieicon, "Отправить отряд зомби на вечеринку ученых"));
        courseModelArrayList.add(new CourseModel(newPrices[2], "25", R.drawable.zombieicon, R.drawable.sciensisticon, "Дать ученым 'попить'"));
        courseModelArrayList.add(new CourseModel(newPrices[3], "50", R.drawable.sciensisticon, R.drawable.zombieicon, "Отправить орду зомби на исследовательскую базу"));
        courseModelArrayList.add(new CourseModel(newPrices[4], "50", R.drawable.zombieicon, R.drawable.sciensisticon, "Устроить показательную казнь, чтобы ученые работали эффективнее"));

        CourseAdapter courseAdapter = new CourseAdapter(this.getActivity(), courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(courseAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}