package com.example.zombieclicker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("ALL_INFO", MODE_PRIVATE);
        name = preferences.getString("nickname", "");
        //name = ;
    }

    public void onStartButtonClick(View view) {
        if (Objects.equals(name, "")) {
            createDialog();
        } else
        {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        }
    }

    private void nicknameSave(String savedName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname", savedName);

        editor.apply();
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.inputYourNickname);

        final EditText nameInput = new EditText(MainActivity.this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(nameInput);

        builder.setPositiveButton("Готово", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!nameInput.getText().toString().isEmpty()) {
                    name = nameInput.getText().toString();
                    Toast toast = new Toast(MainActivity.this);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setText("Ваш никнейм : " + name);
                    toast.show();
                    nicknameSave(name);
                } else {
                    Toast toast = new Toast(MainActivity.this);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setText("Ошибка! Введите имя! ");
                    toast.show();
                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = "";
            }
        });

        // Create the AlertDialog object and return it
        builder.show();
    }
}