package com.example.zombieclicker;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<CourseModel> courseModelArrayList;

    // Constructor
    public CourseAdapter(Context context, ArrayList<CourseModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcard, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CourseModel model = courseModelArrayList.get(position);
        holder.courseEffect.setText(model.getEffect().toString());
        holder.coursePrice.setText(model.getPrice().toString());
        holder.courseMinus.setImageResource(model.getMinusIcon());
        holder.coursePlus.setImageResource(model.getPlusIcon());
        holder.courseDescription.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SharedPreferences preferences;
        private final TextView coursePrice;
        private final TextView courseEffect;
        private final ImageView courseMinus;
        private final ImageView coursePlus;
        private final TextView courseDescription;
        ValueAnimator animatorZombie;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            preferences = itemView.getContext().getSharedPreferences("ALL_INFO", Context.MODE_PRIVATE);

            coursePrice = itemView.findViewById(R.id.priceView);
            courseEffect = itemView.findViewById(R.id.effectView);
            courseMinus = itemView.findViewById(R.id.iconMinus);
            coursePlus = itemView.findViewById(R.id.iconPlus);
            courseDescription = itemView.findViewById(R.id.descroption);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int price = Integer.parseInt(coursePrice.getText().toString());
                    int effect = Integer.parseInt(courseEffect.getText().toString());

                    int countOfZombie = preferences.getInt("countOfZombie", 0);
                    int countOfResearch = preferences.getInt("countOfResearch", 0);

                    int zombieIncome = preferences.getInt("zombieIncome", 0);
                    int researchIncome = preferences.getInt("researchIncome", 0);

                    int allIncome = zombieIncome + researchIncome;

                    animatorZombie.start();

                    Drawable drawable = courseMinus.getDrawable();
                    if (drawable.getConstantState().equals(context.getResources().getDrawable(R.drawable.zombieicon).getConstantState())) {
                        if (countOfZombie >= price) {
                            countOfZombie -= price;
                            int newPrice = Integer.parseInt(Integer.toString((int) Math.ceil(allIncome % 100 - price/156) + price));
                            int incomeResearch = preferences.getInt("researchIncome", 0);
                            coursePrice.setText(Integer.toString(newPrice));
                            Save("researchIncome", incomeResearch + effect);
                            Save("countOfZombie", countOfZombie);
                        } else {
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.setText("Вам не хватает ЗОМБИ!");
                            toast.show();
                        }
                    } else {
                        if (countOfResearch >= price) {
                            countOfResearch -= price;
                            int newPrice = Integer.parseInt(Integer.toString((int) Math.ceil(allIncome % 100 - price/156) + price));
                            coursePrice.setText(Integer.toString(newPrice));
                            int incomeZombie = preferences.getInt("zombieIncome", 0);
                            Save("zombieIncome", incomeZombie + effect);
                            Save("countOfResearch", countOfResearch);
                        } else {
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.setText("Вам не хватает УЧЕНЫХ!");
                            toast.show();
                        }
                    }
                }
            });

            animatorZombie = ValueAnimator.ofFloat(0.7f, 1);
            animatorZombie.setDuration(500L);
            animatorZombie.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    itemView.setScaleX((Float) valueAnimator.getAnimatedValue());
                    itemView.setScaleY((Float) valueAnimator.getAnimatedValue());
                }
            });
        }

        private void Save(String category, int integer) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(category, integer);

            editor.apply();
        }
    }
}