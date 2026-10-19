
public class TemperatureConverter {

    // Celsius → Fahrenheit
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    // Celsius → Kelvin
    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    // Fahrenheit → Celsius
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    // Fahrenheit → Kelvin
    public static double fahrenheitToKelvin(double fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9) + 273.15;
    }

    // Kelvin → Celsius
    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    // Kelvin → Fahrenheit
    public static double kelvinToFahrenheit(double kelvin) {
        return ((kelvin - 273.15) * 9 / 5) + 32;
    }
}