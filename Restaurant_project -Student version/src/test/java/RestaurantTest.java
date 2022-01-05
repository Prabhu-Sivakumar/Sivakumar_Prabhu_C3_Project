import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    public LocalTime getOpeningTime(){
        return LocalTime.parse("10:30:00");
    }

    public LocalTime getClosingTime(){
        return LocalTime.parse("22:00:00");
    }
    @BeforeEach()
    public void createAndAddRestaurantWithMenus(){
        LocalTime openingTime = this.getOpeningTime();
        LocalTime closingTime = this.getClosingTime();
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @AfterEach()
    public void destoryRestaurant(){
        restaurant = null;
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //spied restaurant
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        //when current time is invoked, it returns time in between opening and closing
        doReturn(LocalTime.parse("14:00:00")).when(mockedRestaurant).getCurrentTime();
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //spied restaurant
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        //when current time is invoked, it returns opening time, closing time, time outside them
        doReturn(this.getOpeningTime(),LocalTime.parse("23:00:00"), this.getClosingTime()).when(mockedRestaurant).getCurrentTime();
        assertFalse(mockedRestaurant.isRestaurantOpen());
        assertFalse(mockedRestaurant.isRestaurantOpen());
        assertFalse(mockedRestaurant.isRestaurantOpen());
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

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void getting_order_value_should_return_zero_when_the_selected_menu_names_passed_is_empty(){
        List<String> selectedMenus = Arrays.asList();
        int orderValue = restaurant.getOrderValue(selectedMenus);
        assertThat(orderValue, equalTo(0));
    }

    @Test
    public void getting_order_value_should_return_sum_of_prices_of_selected_menu_names_passed(){
        List<String> selectedMenus = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int orderValue = restaurant.getOrderValue(selectedMenus);
        assertThat(orderValue, equalTo(388));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}