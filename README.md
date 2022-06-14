# simple_weather_view
This is a simple app to see the weather where you live.
The application uses 2 APIs:
  - developer.mapquest to get the longitude and latitude according to the city value you enter
- openweathermap to get weather results
You can also get weather results by city name from openweathermap api. However, on the homepage, they don't recommend doing this and they will soon remove this api (why you can go to their homepage to see it...)
The returned result is Vietnamese. You can go to the Weather class, edit the toString method to display the results in the desired language
