import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RestaurantTest {
    @Mock
    Restaurant restaurant;

    @BeforeEach
    public void inti(){
        MockitoAnnotations.openMocks(this);
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Veg Fried Rice", 399);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = spy(new Restaurant("Indiana Waters", "Mumbai", LocalTime.parse( "12:30:00" ), LocalTime.parse( "23:30:00" )));
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("13:30:00"), LocalTime.parse("16:30:00"), LocalTime.parse("22:30:00"));
        assertTrue(restaurant.isRestaurantOpen());
        assertTrue(restaurant.isRestaurantOpen());
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant = spy(new Restaurant("Indiana Waters", "Mumbai", LocalTime.parse( "12:30:00" ), LocalTime.parse( "23:30:00" )));
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("12:30:00"), LocalTime.parse("04:30:00"), LocalTime.parse("00:30:00"));
        assertFalse(restaurant.isRestaurantOpen());
        assertFalse(restaurant.isRestaurantOpen());
        assertFalse(restaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculate_total_cost_based_on_the_items_selected() throws itemNotFoundException {
        List<String> items = new ArrayList<>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");;
        items.add("Veg Fried Rice");
        //expected value for assertion is set as a sum of all the item values set before each test case
        assertEquals(787, restaurant.calculateTotalCost(items));
    }

}