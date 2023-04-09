package depedro.hines.n01455125;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class n01455125Weather extends Fragment {

    public Spinner citySpinner;
    public TextView lonTextView, latTextView, countryTextView, humidityTextView, nameTextView, descriptionTextView, tempTextView;
    public RadioButton celsiusRadioButton, fahrenheitRadioButton;

    // API key for OpenWeatherMap
    private final String API_KEY = "4e7addb030e344b4a4ba99832f35d481";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.n01455125_weather, container, false);

        // Initialize UI elements
        citySpinner = rootView.findViewById(R.id.city_spinner);
        lonTextView = rootView.findViewById(R.id.lon_textview);
        latTextView = rootView.findViewById(R.id.lat_textview);
        countryTextView = rootView.findViewById(R.id.city_textview);
        humidityTextView = rootView.findViewById(R.id.humidity_textview);
        nameTextView = rootView.findViewById(R.id.name_textview);
        descriptionTextView = rootView.findViewById(R.id.description_textview);
        tempTextView = rootView.findViewById(R.id.temp_textview);
        celsiusRadioButton = rootView.findViewById(R.id.celsius_radio_button);
        fahrenheitRadioButton = rootView.findViewById(R.id.fahrenheit_radio_button);

        String[] city = getResources().getStringArray(R.array.Cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, city);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Extract the selected city's name and its corresponding latitude and longitude values
                String city = adapterView.getItemAtPosition(position).toString();
                double[] latLong = getLatLong(city, getContext());

                // Construct the URL for the OpenWeatherMap API call
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latLong[0] + "&lon=" + latLong[1] + "&appid=" + API_KEY;

                // Call the API and retrieve the weather data in a background task
                new WeatherTask().execute(apiUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        return rootView;
    }

    // Helper method to get the latitude and longitude values for a given city
    private double[] getLatLong(String city, Context context) {
        double[] latLong = new double[2];
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addresses = geocoder.getFromLocationName(city, 1);
            if (addresses != null && addresses.size() > 0) {
                latLong[0] = addresses.get(0).getLatitude();
                latLong[1] = addresses.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLong;
    }

    // Background task to retrieve weather data from OpenWeatherMap API
    private class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    response += scanner.nextLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                // Extract the temperature, humidity, and description from the API response
                JSONObject mainObject = jsonObject.getJSONObject("main");
                double temperature = mainObject.getDouble("temp");
                int humidity = mainObject.getInt("humidity");

                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String description = weatherObject.getString("description");

                // Update the UI with the retrieved data
                updateWeatherUI(temperature, humidity, description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to update the UI with weather data
    private void updateWeatherUI(double temperature, int humidity, String description) {
        // Display the temperature in Celsius or Fahrenheit based on the selected radio button
        double temp = temperature;
        if (fahrenheitRadioButton.isChecked()) {
            temp = temperature * 1.8 + 32;
        }
        tempTextView.setText(String.format("%.2f °C", temp));

        // Update the humidity and description
        humidityTextView.setText(String.format("Humidity: %d%%", humidity));
        descriptionTextView.setText(description);

        // Get the selected city's name
        String cityName = citySpinner.getSelectedItem().toString();
        nameTextView.setText(cityName);

        // Get the selected city's country name
        String countryName = getCountryName(cityName, getContext());
        countryTextView.setText(countryName);

        // Get the latitude and longitude values for the selected city
        double[] latLong = getLatLong(cityName, getContext());

        // Display the latitude and longitude values
        lonTextView.setText(String.format("Longitude: %.2f", latLong[1]));
        latTextView.setText(String.format("Latitude: %.2f", latLong[0]));
    }
    // Helper method to get the country name for a given city
    private String getCountryName(String city, Context context) {
        String countryName = "";
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocationName(city, 1);
            if (addresses != null && addresses.size() > 0) {
                countryName = addresses.get(0).getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryName;
    }


}
