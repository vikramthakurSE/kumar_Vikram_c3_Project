import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


class RestaurantServiceTest {

    @Mock
    RestaurantService restaurantService;
    @Mock
    Restaurant restaurant;
    List<Restaurant> restaurants = new ArrayList<>();

    @BeforeEach
    public void inti(){
        MockitoAnnotations.openMocks(this);
        restaurantService = spy(RestaurantService.class);

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = restaurantService.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurants.add(restaurant);
        restaurants.add(new Restaurant("BBC", "Mumbai", LocalTime.parse( "11:30:00" ), LocalTime.parse( "23:30:00" )));

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        when(restaurantService.getRestaurants()).thenReturn(restaurants);
        Restaurant restaurant = restaurantService.findRestaurantByName("BBC");
        assertEquals(restaurants.get(1),restaurant);
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        Throwable exception = assertThrows(restaurantNotFoundException.class, () -> restaurantService.findRestaurantByName("Taj"));
        assertEquals("Restaurant Not Found", exception.getMessage());
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = restaurantService.getRestaurants().size();
        restaurantService.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, restaurantService.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->restaurantService.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = restaurantService.getRestaurants().size();
        restaurantService.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,restaurantService.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

}