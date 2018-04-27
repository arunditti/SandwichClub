package com.arunditti.android.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.arunditti.android.sandwichclub.model.Sandwich;
import com.arunditti.android.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arunditti on 4/24/18.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

//    ImageView ingredientsIv;
//    TextView also_known_as;
//    TextView place_of_origin;
//    TextView description;
//    TextView ingredients;

    // “Butterknife” is a lightweight library that can be used to inject views into Android components in an easier way

    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView also_known_as;
    @BindView(R.id.origin_tv) TextView place_of_origin;
    @BindView(R.id.description_tv) TextView description;
    @BindView(R.id.ingredients_tv) TextView ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        ingredientsIv = findViewById(R.id.image_iv);
//        also_known_as = findViewById(R.id.also_known_tv);
//        place_of_origin = findViewById(R.id.origin_tv);
//        description = findViewById(R.id.description_tv);
//        ingredients = findViewById(R.id.ingredients_tv);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);


        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            also_known_as.setText(R.string.also_known_as_not_found);
        } else {
            List<String> alsoKnownAsString = sandwich.getAlsoKnownAs();
            //also_known_as.append(alsoKnownAsString + "");
            also_known_as.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            place_of_origin.setText(R.string.place_of_origin_not_found);
        } else {
            place_of_origin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()) {
            description.setText(R.string.description_not_found);
        } else {
            description.setText(sandwich.getDescription());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredients.setText(R.string.ingredients_not_found);
        } else {
            List<String> ingredientsString = sandwich.getIngredients();
            //ingredients.append(ingredientsString + "");
            ingredients.setText(TextUtils.join(", ", ingredientsString));
        }
    }
}


