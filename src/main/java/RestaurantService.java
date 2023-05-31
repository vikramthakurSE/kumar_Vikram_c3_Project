import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {
        /*
         * Using java 8 features --> streams and optional classes
         * Streams is used to iterate though the list of restaurants and filter out the instance of restaurant that matches restaurantName
         * Since findFirst returns an optional, setting the value found to optional of type Restaurant
         * While returning we check if the restaurant object exists, we are returning that value or else we are throwing restaurantNotFoundException
         * */
        Optional<Restaurant> restaurantOptional = getRestaurants().stream().filter(restaurant -> restaurant.getName().equals(restaurantName)).findFirst();
        restaurantOptional.ifPresent(restaurant -> restaurant.displayDetails());
        return restaurantOptional.orElseThrow(() -> new restaurantNotFoundException("Restaurant Not Found"));
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant restaurantToAdd = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(restaurantToAdd);
        return restaurantToAdd;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

}
