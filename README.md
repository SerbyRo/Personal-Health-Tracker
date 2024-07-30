Overview

The Personal Health Tracker is an Android application designed to help users manage and track their health-related activities. The application includes functionalities for showing geographic locations, choosing favorite cities to visit, monitoring environmental sensors like the accelerometer and light sensor, and tracking temperature data.
Feature improvements: After the application will be deployed, the step counter feature will be included in the list of developed feature because we can't count steps on a virtual emulator.
Features

    Show Location: Allows users to enter a location and view it on a map.
    Choose City: Provides a selection of popular cities using a RecyclerView, enabling users to choose a city and display its image.
    Image Display: Shows the selected city’s image on the main screen.
    Sensor Monitoring: Includes activities to display data from the device’s accelerometer, light sensor, and temperature sensor.
    Navigation: Includes buttons to navigate between different activities.

Activities
MainActivity

    Description: The main activity that provides navigation to other activities.
    Components:
        Buttons to navigate to NfcGeoActivity, AccelerometerActivity, LightSensorActivity, and TemperatureActivity.

NfcGeoActivity

    Description: Activity for showing geographic locations and selecting cities.
    Components:
        EditText: For entering the location.
        Button (Show map): To show the entered location on a map.
        Button (Choose City): To open the city chooser activity.
        Button (Back to Main): To navigate back to the main activity.
        ImageView: To display the selected city’s image.

ChooserActivity

    Description: Activity for selecting a city from a list of popular cities.
    Components:
        RecyclerView: Displays a list of cities to choose from.
        Each city item includes an image and a name.

AccelerometerActivity

    Description: Activity for displaying data from the device’s accelerometer sensor.
    Components:
        TextView: To display the accelerometer data.
        Button (Transfer Data): To send accelerometer data to LightSensorActivity.
        Button: To navigate back to the main activity.
        Animation: TextView displaying accelerometer data has a rotation animation.

LightSensorActivity

    Description: Activity for displaying data from the device’s light sensor.
    Components:
        TextView: To display the light sensor data.
        Button (Transfer Data): To send light sensor data to TemperatureActivity.
        Button: To navigate back to the main activity.
        Animation: TextView displaying light sensor data has a rotation animation.

TemperatureActivity

    Description: Activity for displaying temperature data.
    Components:
        TextView: To display temperature data.
        Button: To navigate back to the main activity.

XML Layouts
activity_main.xml

Defines the layout for MainActivity, including buttons to navigate to other activities.
activity_nfc_geo.xml

Defines the layout for NfcGeoActivity, including the toolbar, EditText, buttons, and ImageView.
activity_chooser.xml

Defines the layout for ChooserActivity, including a RecyclerView for displaying the list of cities.
activity_accelerometer.xml

Defines the layout for AccelerometerActivity, including TextView, buttons, and animations.
activity_light_sensor.xml

Defines the layout for LightSensorActivity, including TextView, buttons, and animations.
activity_temperature.xml

Defines the layout for TemperatureActivity, including TextView and a button.
