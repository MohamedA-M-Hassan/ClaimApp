import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mamohamed on 3/10/2018.
 */

public class WeatherModel {
    private String mCity;
    private String mIcon;
    private String mTemprature;
    private int mCondition;


    public static  WeatherModel fromJson(JSONObject jsonObject){
        try {
            WeatherModel weatherData = new WeatherModel();

            weatherData.mCity = jsonObject.getString("name");
            weatherData.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mIcon = updateWeatherIcon(weatherData.mCondition);

            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);

            weatherData.mTemprature = Integer.toString(roundedValue);

            return weatherData;

        }
        catch (JSONException e){
            e.printStackTrace();
            return  null;


        }


    }
    // Get the weather image name from OpenWeatherMap's condition (marked by a number code)
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

}
