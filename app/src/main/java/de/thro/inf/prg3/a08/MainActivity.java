package de.thro.inf.prg3.a08;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.thro.inf.prg3.a08.api.OpenMensaAPI;
import de.thro.inf.prg3.a08.filtering.MealsFilter;
import de.thro.inf.prg3.a08.filtering.MealsFilterFactory;
import de.thro.inf.prg3.a08.model.Meal;
import de.thro.inf.prg3.a08.service.OpenMensaAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final SimpleDateFormat dateFormat;
    private final OpenMensaAPI openMensaAPI;
    private ArrayAdapter<Meal> mealsArrayAdapter;

    public MainActivity() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        OpenMensaAPIService apiService = OpenMensaAPIService.getInstance();
        openMensaAPI = apiService.getApi();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this will inflate the layout from res/layout/activity_main.xml
        setContentView(R.layout.activity_main);


        /* create the ArrayAdapter without an given list instance */
        mealsArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.meal_entry
        );

        /* create an ArrayAdapter to fill the spinner */
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.meals_filter_items,
                R.layout.support_simple_spinner_dropdown_item
        );

        /* 'caching' the reference to the ListView */
        final ListView mealsListView = findViewById(R.id.mealsList);
        mealsListView.setAdapter(mealsArrayAdapter);

        final Spinner filterSpinner = findViewById(R.id.filterSpinner);
        filterSpinner.setAdapter(spinnerAdapter);

        /* you may keep most of the business logic *
         * register click handler */
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* execute call to the OpenMensaAPI
                 * note that the .enqueue(...) method is used instead of .execute() */
                openMensaAPI.getMeals(dateFormat.format(new Date())).enqueue(new Callback<List<Meal>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Meal>> call, @NotNull Response<List<Meal>> response) {
                        /* check if response code was 2xx */
                        if (response.isSuccessful()) {
                            /* remove elements from the adapter */
                            mealsArrayAdapter.clear();
                            /* unwrap the retrieved meals */
                            List<Meal> retrievedMeals = response.body();

                            String key = spinnerAdapter.getItem(position).toString();
                            MealsFilter strategy = MealsFilterFactory.getStrategy(key);

                            mealsArrayAdapter.addAll(strategy.filter(retrievedMeals));

                        } else {
                            /* display an error message if response code was not 2xx */
                            Toast.makeText(
                                    MainActivity.this,
                                    R.string.api_failure_toast,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Meal>> call, @NotNull Throwable t) {
                        /* display an error message if call failed */
                        Toast.makeText(
                                MainActivity.this,
                                R.string.api_failure_toast,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
