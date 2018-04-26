package com.arunditti.android.sandwichclub.utils;

import com.arunditti.android.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arunditti on 4/24/18.
 */

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        //These are the names of the JSON objects that need to be extracted
        final String SC_NAME = "name";
        final String SC_MAIN_NAME = "mainName";
        final String SC_ALSO_KNOWN_AS = "alsoKnownAs";
        final String SC_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String SC_DESCRIPTION = "description";
        final String SC_IMAGE = "image";
        final String SC_INGREDIENTS = "ingredients";

        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObjectName   = jsonObject.getJSONObject(SC_NAME);
            mainName = jsonObjectName.optString(SC_MAIN_NAME);
            placeOfOrigin = jsonObject.optString(SC_PLACE_OF_ORIGIN);
            description = jsonObject.optString(SC_DESCRIPTION);
            image = jsonObject.optString(SC_IMAGE);

            JSONArray alsoKnownAsArray = jsonObjectName.getJSONArray(SC_ALSO_KNOWN_AS);
            for(int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.optString(i));
            }

            JSONArray ingredientsArray = jsonObject.getJSONArray(SC_INGREDIENTS);
            for(int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.optString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
