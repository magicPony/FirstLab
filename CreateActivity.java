package com.example.taras.firstlab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Taras on 12/12/2016.
 */

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        findViewById(R.id.btn_done_AC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etField = (EditText) findViewById(R.id.et_field_AC);
                String field = "";

                if (!TextUtils.isEmpty(etField.getText())) {
                    field = etField.getText().toString();
                }

                MainActivity
                        .contentResolver
                        .insert(ApiConst.CONTENT_URI, MagicCast.toContentValues(ApiConst.FIELD_KEY, field));

                Toast
                        .makeText(CreateActivity.this, getString(R.string.saved_successfuly), Toast.LENGTH_LONG)
                        .show();

                setResult(0);
                onBackPressed();
            }
        });
    }
}
