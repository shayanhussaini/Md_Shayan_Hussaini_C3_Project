import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName){
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException(restaurantName);
        //DELETE ABOVE STATEMENT AND WRITE CODE HERE
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public Double getOrderTotal(String restaurantName, List<String> selectedItemList) throws itemNotFoundException, restaurantNotFoundException
    {
        Double totalAmount = 0.0;
        Restaurant restaurant = findRestaurantByName(restaurantName);
        if(null == restaurant)
            throw new restaurantNotFoundException(restaurantName +" does not exists");
        List<Item> menuList = restaurant.getMenu();
        if(menuList.isEmpty())
            throw new itemNotFoundException("No items are there in restaurant");
        for(String selectedItem: selectedItemList)
        {
            boolean selectedItemFound = false;
            for(Item item: menuList)
            {
                if (selectedItem.contains(item.getName())) {
                    totalAmount += item.getPrice();
                    selectedItemFound = true;
                    break;
                }
            }
            if(!selectedItemFound)
            {
                throw new itemNotFoundException(selectedItem + "is not found at " + restaurantName);
            }
        }
        return totalAmount;
    }
}
