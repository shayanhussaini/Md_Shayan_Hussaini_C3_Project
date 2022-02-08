import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    @BeforeEach
    public  void TestDataSetup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        String restaurantName = "Amelie's cafe";
        assertEquals(restaurantName,service.findRestaurantByName(restaurantName).getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        assertThrows(restaurantNotFoundException.class,()->
        {

            service.findRestaurantByName("hotel").getName();
        });
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){        
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    //>>>>>>>>>>>>>>>>>>>>>>GET ORDER TOTAL <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void get_order_total_for_selected_items() throws itemNotFoundException, restaurantNotFoundException 
    {
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("Sweet corn soup");
        selectedItemList.add("Vegetable lasagne");
        assertEquals(388.0, service.getOrderTotal("Amelie's cafe", selectedItemList));
    }

    @Test
    public void get_order_total_for_no_selected_items() throws itemNotFoundException, restaurantNotFoundException
    {
        List<String> selectedItemList = new ArrayList<String>();
        assertEquals(0.0, service.getOrderTotal("Amelie's cafe", selectedItemList));
    }

    @Test
    public void get_order_total_for_item_does_not_exist_should_throw_exception()throws itemNotFoundException, restaurantNotFoundException
    {
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("Coke");
        assertThrows(itemNotFoundException.class,()->service.getOrderTotal("Amelie's cafe", selectedItemList));
    }

    @Test
    public void get_order_total_for_no_items_exist_in_menu_at_restaurant_should_throw_exception()throws itemNotFoundException, restaurantNotFoundException
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Prince Cafe","Hyderarabad",openingTime,closingTime);
        List<String> selectedItemList = new ArrayList<String>();
        selectedItemList.add("Juice");
        assertThrows(itemNotFoundException.class,()->service.getOrderTotal("Prince Cafe", selectedItemList));
    }
    //>>>>>>>>>>>>>>>>>>>>>>GET OREER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}