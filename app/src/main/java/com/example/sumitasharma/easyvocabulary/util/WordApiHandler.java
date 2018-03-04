package com.example.sumitasharma.easyvocabulary.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WordApiHandler {

//    public static URL buildUrl() {
//        Uri builtUri = Uri.parse(WORD_PRACTICE_URL).buildUpon()
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }

    // Connecting to Internet
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

//    // Convert JSON string to MovieDetails for SortBy returning MovieDetails[] array for Main Activity
//    public static ArrayList<Recipe> convertJsonToRecipeObjects() throws JSONException {
//        //Convert fullJsonMoviesData to JsonObject
//        String urlResponse = null;
//        URL jsonURL = buildUrl();
//        try {
//            urlResponse = getResponseFromHttpUrl(jsonURL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        JSONArray bakingDataArray = new JSONArray(urlResponse);
//        ArrayList<Recipe> results = new ArrayList<>();
//        for (int i = 0; i < bakingDataArray.length(); i++) {
//            Recipe recipe = new Recipe();
//            recipe.setId(bakingDataArray.getJSONObject(i).getInt(ID));
//            recipe.setName(bakingDataArray.getJSONObject(i).getString(NAME));
//            recipe.setImage(getDefaultorActualImage(bakingDataArray.getJSONObject(i).getString(IMAGE)));
//            recipe.setServings(bakingDataArray.getJSONObject(i).getString(SERVINGS));
//            JSONArray ingredientsJSONArray = bakingDataArray.getJSONObject(i).getJSONArray(INGREDIENTS);
//            ArrayList<Ingredient> ingredientsArray = new ArrayList<>();
//            for (int j = 0; j < ingredientsJSONArray.length(); j++) {
//                Ingredient ingredient = new Ingredient();
//                ingredient.setIngredient(ingredientsJSONArray.getJSONObject(j).getString(INGREDIENT));
//                ingredient.setMeasure(ingredientsJSONArray.getJSONObject(j).getString(MEASURE));
//                ingredient.setQuantity(ingredientsJSONArray.getJSONObject(j).getString(QUANTITY));
//                ingredientsArray.add(ingredient);
//            }
//            recipe.setIngredients(ingredientsArray);
//            JSONArray stepJSONArray = bakingDataArray.getJSONObject(i).getJSONArray(STEPS);
//            ArrayList<Step> stepArray = new ArrayList<>();
//            for (int k = 0; k < stepJSONArray.length(); k++) {
//                Step step = new Step();
//                step.setId(stepJSONArray.getJSONObject(k).getString(STEP_ID));
//                step.setDescription(stepJSONArray.getJSONObject(k).getString(DESCRIPTION));
//                step.setShortDescription(stepJSONArray.getJSONObject(k).getString(SHORT_DESCRIPTION));
//                step.setThumbnailURL(stepJSONArray.getJSONObject(k).getString(thumbnailURL));
//                step.setVideoURL(stepJSONArray.getJSONObject(k).getString(videoURL));
//                stepArray.add(step);
//            }
//            recipe.setSteps(stepArray);
//            results.add(recipe);
//        }
//        return results;
//    }
//
//    private static String getDefaultorActualImage(String imagePath) {
//        String returnedImage = imagePath;
//        if (TextUtils.isEmpty(returnedImage)) {
//            // if (returnedImage.isEmpty()) {
//            returnedImage = DEFAULT_BAKING_IMAGE_PATH;
//        }
//        return returnedImage;
//    }

    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else
            return false;
    }
}
