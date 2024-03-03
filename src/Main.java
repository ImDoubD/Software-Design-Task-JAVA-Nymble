// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.lang.*;
import java.util.*;


// Activity class represents an activity available at a destination.
class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int currentCount;

    private List<Passenger> signedUpPassengers;  // List to keep track of passengers who have signed up for this activity.


    // Constructor initializes the activity with its details and an empty list for signed-up passengers.
    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.currentCount = 0;
        this.signedUpPassengers = new ArrayList<>();
    }

    // Method to check if the activity is available for sign-ups.
    public boolean isAvailable() {
        return currentCount < capacity;
    }

    public double getCost() {
        return cost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }
        this.cost = cost;
    }

    public void setCapacity(int capacity) {
        if (capacity < currentCount) {
            throw new IllegalArgumentException("Capacity cannot be less than current count");
        }
        this.capacity = capacity;
    }

    public void setCurrentCount(int currentCount) {
        if (currentCount > capacity) {
            throw new IllegalArgumentException("Current count cannot exceed capacity");
        }
        this.currentCount = currentCount;
    }

    //Method to sign up a passenger for the activity, if the activity is available and the passenger is not already signed up.
    public boolean signUp(Passenger passenger) {
        if (isAvailable() && !signedUpPassengers.contains(passenger)) {
            currentCount++;
            signedUpPassengers.add(passenger);
            return true;
        }
        return false;
    }

    // Method to print details of the activity.
    public void printActivityDetails() {
        System.out.println("Activity Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Cost: " + cost);
        System.out.println("Capacity: " + capacity);
        System.out.println("Current Count: " + currentCount);
    }
}

// Destination class represents a destination in the travel package itinerary.
class Destination {
    private String name;
    private List<Activity> activities;  // List of activities available at this destination.

    // Constructor initializes the destination with its name and an empty list for activities.
    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }


    // Method to add an activity to the destination.
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Destination destination = (Destination) obj;
        return Objects.equals(name, destination.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    // Method to print the itinerary of the destination, including details of all activities.
    public void printItinerary() {
        System.out.println("Destination Name: " + name);
        System.out.println("Activities:");
        for (Activity activity : activities) {
            activity.printActivityDetails();
        }
    }

}

// Passenger class is an abstract class representing a passenger.
abstract class Passenger {
    private String name;
    private String passengerNumber;

    // Constructor initializes the passenger with their name and passenger number.
    public Passenger(String name, String passengerNumber) {
        this.name = name;
        this.passengerNumber = passengerNumber;
    }


    // Abstract method to sign up for an activity. This method will be implemented by subclasses.
    public abstract boolean signUpForActivity(Activity activity);

    // Getters
    public String getName() {
        return name;
    }

    public String getPassengerNumber() {
        return passengerNumber;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassengerNumber(String passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) obj;
        return Objects.equals(passengerNumber, passenger.passengerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerNumber);
    }


    // Method to print details of the passenger.
    public void printPassengerDetails() {
        System.out.println("Name: " + name);
        System.out.println("Passenger Number: " + passengerNumber);
    }

}

// StandardPassenger class represents a standard passenger.
class StandardPassenger extends Passenger {
    private double balance;

    // Constructor initializes the standard passenger with their name, passenger number, and balance.
    public StandardPassenger(String name, String passengerNumber, double balance) {
        super(name, passengerNumber);
        this.balance = balance;
    }

    // Implementation of the abstract method to sign up for an activity.
    @Override
    public boolean signUpForActivity(Activity activity) {
        if (activity.isAvailable() && balance >= activity.getCost()  && activity.signUp(this)) {
            balance -= activity.getCost();
            return true;
        }
        return false;
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    // Setters
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    // Overridden method to print details of the standard passenger, including their balance.
    @Override
    public void printPassengerDetails() {
        super.printPassengerDetails();
        System.out.println("Balance: " + balance);
    }

}

// GoldPassenger class represents a gold passenger.
class GoldPassenger extends Passenger {
    private double balance;

    // Constructor initializes the gold passenger with their name, passenger number, and balance.
    public GoldPassenger(String name, String passengerNumber, double balance) {
        super(name, passengerNumber);
        this.balance = balance;
    }

    // Implementation of the abstract method to sign up for an activity with a 10% discount.
    @Override
    public boolean signUpForActivity(Activity activity) {
        double discountedCost = activity.getCost() * 0.9; // Apply a 10% discount
        if (activity.isAvailable() && balance >= discountedCost && activity.signUp(this)) {
            balance -= discountedCost;
            return true;
        }
        return false;
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    // Setters
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    // Overridden method to print details of the gold passenger, including their balance.
    @Override
    public void printPassengerDetails() {
        super.printPassengerDetails();
        System.out.println("Balance: " + balance);
    }

}

// PremiumPassenger class represents a premium passenger.
class PremiumPassenger extends Passenger {

    // Constructor initializes the premium passenger with their name and passenger number.
    public PremiumPassenger(String name, String passengerNumber) {
        super(name, passengerNumber);
    }

    // Implementation of the abstract method to sign up for an activity for free.
    @Override
    public boolean signUpForActivity(Activity activity) {
        // Premium passengers can sign up for activities for free
        return activity.signUp(this);
    }


    // Overridden method to print details of the premium passenger.
    @Override
    public void printPassengerDetails() {
        super.printPassengerDetails();
        // No additional details to print for premium passengers
    }

}


// TravelPackage class represents a travel package.
class TravelPackage {
    private String name;
    private int capacity;
    private List<Destination> itinerary;   // List of destinations in the travel package itinerary.
    private List<Passenger> passengers;   // List of passengers in the travel package.


    // Constructor initializes the travel package with its name, capacity, and empty lists for itinerary and passengers.
    public TravelPackage(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.itinerary = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }


    // Methods to add a destination or a passenger to the travel package.
    public void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    public boolean addPassenger(Passenger passenger) {
        if (!passengers.contains(passenger) && passengers.size() < capacity) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }


    // Getters
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Destination> getItinerary() {
        return itinerary;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        if (capacity < passengers.size()) {
            throw new IllegalArgumentException("Capacity cannot be less than the number of passengers");
        }
        this.capacity = capacity;
    }

    public boolean removeDestination(Destination destination) {
        return itinerary.remove(destination);
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }


    // Methods to print the passenger list and the details of all the activities that still have spaces available.
    public void printPassengerList() {
        System.out.println("Travel Package Name: " + name);
        System.out.println("Passenger Capacity: " + capacity);
        System.out.println("Number of Passengers: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    public void printAvailableActivities() {
        for (Destination destination : itinerary) {
            for (Activity activity : destination.getActivities()) {
                if (activity.isAvailable()) {
                    System.out.println("Destination: " + destination.getName());
                    activity.printActivityDetails();
                    System.out.println("Spaces Available: " + (activity.getCapacity() - activity.getCurrentCount()));
                }
            }
        }
    }

}

// Main class to demonstrate the functionalities with dummy data.
public class Main {
    public static void main(String[] args) {

        // Create dummy data for travel packages, destinations, activities, and passengers.

        // Create Travel Package 1
        TravelPackage travelPackage1 = new TravelPackage("Trip to Bali", 10);
        Destination bali = new Destination("Bali");
        Activity diving = new Activity("Diving", "Scuba diving at the Great Barrier Reef", 100.0, 5);
        Activity surfing = new Activity("Surfing", "Surfing at Bali's beaches", 50.0, 10);
        bali.addActivity(diving);
        bali.addActivity(surfing);
        travelPackage1.addDestination(bali);

        // Create Travel Package 2
        TravelPackage travelPackage2 = new TravelPackage("Trip to Hawaii", 15);
        Destination hawaii = new Destination("Hawaii");
        Activity snorkeling = new Activity("Snorkeling", "Snorkeling at the Hawaiian reef", 80.0, 8);
        Activity hiking = new Activity("Hiking", "Hiking in Hawaii", 60.0, 12);
        hawaii.addActivity(snorkeling);
        hawaii.addActivity(hiking);
        travelPackage2.addDestination(hawaii);

        // Sign up passengers for activities and print the itinerary, passenger list, and available activities for each travel package.

        // Add passengers to Travel Package 1
        Passenger passenger1 = new StandardPassenger("John Doe", "P123", 500.0);
        Passenger passenger2 = new GoldPassenger("Jane Doe", "P456", 700.0);
        Passenger passenger3 = new PremiumPassenger("Steve Smith", "P789");
        travelPackage1.addPassenger(passenger1);
        travelPackage1.addPassenger(passenger2);
        travelPackage1.addPassenger(passenger3);

        // Sign up passengers for activities in Travel Package 1
        passenger1.signUpForActivity(diving); // Standard passenger signs up for an activity
        passenger2.signUpForActivity(surfing); // Gold passenger signs up for an activity
        passenger3.signUpForActivity(diving); // Premium passenger signs up for an activity for free

        // Add passengers to Travel Package 2
        Passenger passenger4 = new StandardPassenger("Mary Johnson", "P321", 400.0);
        Passenger passenger5 = new GoldPassenger("Bob Brown", "P654", 500.0);
        Passenger passenger6 = new PremiumPassenger("Alice Green", "P987");
        travelPackage2.addPassenger(passenger4);
        travelPackage2.addPassenger(passenger5);
        travelPackage2.addPassenger(passenger6);

        // Sign up passengers for activities in Travel Package 2
        passenger4.signUpForActivity(snorkeling);
        passenger5.signUpForActivity(hiking);
        passenger6.signUpForActivity(snorkeling);

        // Print itinerary of Travel Package 1
        System.out.println("\nItinerary of Travel Package 1:");
        bali.printItinerary();

        // Print passenger list of Travel Package 1
        System.out.println("\nPassenger List of Travel Package 1:");
        travelPackage1.printPassengerList();

        // Print available activities of Travel Package 1
        System.out.println("\nAvailable Activities in Travel Package 1:");
        travelPackage1.printAvailableActivities();

        // Print itinerary of Travel Package 2
        System.out.println("\nItinerary of Travel Package 2:");
        hawaii.printItinerary();

        // Print passenger list of Travel Package 2
        System.out.println("\nPassenger List of Travel Package 2:");
        travelPackage2.printPassengerList();

        // Print available activities of Travel Package 2
        System.out.println("\nAvailable Activities in Travel Package 2:");
        travelPackage2.printAvailableActivities();

        // Print individual passenger details
        System.out.println("\nPassenger Details:");
        passenger1.printPassengerDetails();
        passenger2.printPassengerDetails();
        passenger3.printPassengerDetails();
        passenger4.printPassengerDetails();
        passenger5.printPassengerDetails();
        passenger6.printPassengerDetails();
    }
}



//below are the possible test cases encountered,
// but implementation shows output for the data given in the main class above.

//separate testing classes


//1. Travel Package Testing class
class TravelPackageTest {

    public static void main(String[] args) {
        testConstructor();
        testAddDestination();
        testAddPassengerWithSpace();
        testAddPassengerNoSpace();
        testRemoveDestination();
        testRemovePassenger();
        testGetters();
        testSetters();
        testSetCapacityLessThanPassengers();
    }

    public static void testConstructor() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            if ("Summer Adventure".equals(travelPackage.getName()) &&
                    travelPackage.getCapacity() == 10 &&
                    travelPackage.getItinerary().isEmpty() &&
                    travelPackage.getPassengers().isEmpty()) {
                System.out.println("testConstructor: Passed");
            } else {
                System.out.println("testConstructor: Failed");
            }
        } catch (Exception e) {
            System.out.println("testConstructor: Failed with exception: " + e.getMessage());
        }
    }

    public static void testAddDestination() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            Destination destination = new Destination("Paris");
            travelPackage.addDestination(destination);
            if (travelPackage.getItinerary().contains(destination)) {
                System.out.println("testAddDestination: Passed");
            } else {
                System.out.println("testAddDestination: Failed");
            }
        } catch (Exception e) {
            System.out.println("testAddDestination: Failed with exception: " + e.getMessage());
        }
    }

    public static void testRemoveDestination() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            Destination destination = new Destination("Paris");
            travelPackage.addDestination(destination);
            if (travelPackage.removeDestination(destination) &&
                    !travelPackage.getItinerary().contains(destination)) {
                System.out.println("testRemoveDestination: Passed");
            } else {
                System.out.println("testRemoveDestination: Failed");
            }
        } catch (Exception e) {
            System.out.println("testRemoveDestination: Failed with exception: " + e.getMessage());
        }
    }


    public static void testGetters() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            if ("Summer Adventure".equals(travelPackage.getName()) &&
                    travelPackage.getCapacity() == 10 &&
                    travelPackage.getItinerary().isEmpty() &&
                    travelPackage.getPassengers().isEmpty()) {
                System.out.println("testGetters: Passed");
            } else {
                System.out.println("testGetters: Failed");
            }
        } catch (Exception e) {
            System.out.println("testGetters: Failed with exception: " + e.getMessage());
        }
    }

    public static void testSetters() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            travelPackage.setName("Winter Wonderland");
            travelPackage.setCapacity(5);
            if ("Winter Wonderland".equals(travelPackage.getName()) &&
                    travelPackage.getCapacity() == 5) {
                System.out.println("testSetters: Passed");
            } else {
                System.out.println("testSetters: Failed");
            }
        } catch (Exception e) {
            System.out.println("testSetters: Failed with exception: " + e.getMessage());
        }
    }

    public static void testRemovePassenger() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            // Use StandardPassenger instead of Passenger
            StandardPassenger passenger = new StandardPassenger("John Doe", "12345", 1000.0);
            travelPackage.addPassenger(passenger);
            if (travelPackage.removePassenger(passenger) &&
                    !travelPackage.getPassengers().contains(passenger)) {
                System.out.println("testRemovePassenger: Passed");
            } else {
                System.out.println("testRemovePassenger: Failed");
            }
        } catch (Exception e) {
            System.out.println("testRemovePassenger: Failed with exception: " + e.getMessage());
        }
    }

    public static void testSetCapacityLessThanPassengers() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            // Use GoldPassenger and PremiumPassenger for demonstration
            GoldPassenger passenger1 = new GoldPassenger("John Doe", "12345", 1000.0);
            PremiumPassenger passenger2 = new PremiumPassenger("Jane Doe", "67890");
            travelPackage.addPassenger(passenger1);
            travelPackage.addPassenger(passenger2);
            // Attempt to set capacity to less than the number of passengers
            travelPackage.setCapacity(1); // This should throw an IllegalArgumentException
            System.out.println("testSetCapacityLessThanPassengers: Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetCapacityLessThanPassengers: Passed");
        } catch (Exception e) {
            System.out.println("testSetCapacityLessThanPassengers: Failed with exception: " + e.getMessage());
        }
    }


    public static void testAddPassengerWithSpace() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 10);
            // Use StandardPassenger instead of Passenger
            StandardPassenger passenger = new StandardPassenger("John Doe", "12345", 1000.0);
            if (travelPackage.addPassenger(passenger) &&
                    travelPackage.getPassengers().contains(passenger)) {
                System.out.println("testAddPassengerWithSpace: Passed");
            } else {
                System.out.println("testAddPassengerWithSpace: Failed");
            }
        } catch (Exception e) {
            System.out.println("testAddPassengerWithSpace: Failed with exception: " + e.getMessage());
        }
    }

    public static void testAddPassengerNoSpace() {
        try {
            TravelPackage travelPackage = new TravelPackage("Summer Adventure", 1);
            // Use GoldPassenger instead of Passenger
            GoldPassenger passenger1 = new GoldPassenger("John Doe", "12345", 1000.0);
            // Use PremiumPassenger instead of Passenger
            PremiumPassenger passenger2 = new PremiumPassenger("Jane Doe", "67890");
            travelPackage.addPassenger(passenger1);
            if (!travelPackage.addPassenger(passenger2)) {
                System.out.println("testAddPassengerNoSpace: Passed");
            } else {
                System.out.println("testAddPassengerNoSpace: Failed");
            }
        } catch (Exception e) {
            System.out.println("testAddPassengerNoSpace: Failed with exception: " + e.getMessage());
        }
    }
}



//2. Activity Test Class
class ActivityTest {
    public static void main(String[] args) {
        // Testing constructor and getters
        Activity activity1 = new Activity("Diving", "Scuba diving at the Great Barrier Reef", 100.0, 5);
        if (activity1.getName().equals("Diving") && activity1.getDescription().equals("Scuba diving at the Great Barrier Reef") &&
                activity1.getCost() == 100.0 && activity1.getCapacity() == 5 && activity1.getCurrentCount() == 0) {
            System.out.println("Activity constructor and getters test 1 passed.");
        } else {
            System.out.println("Activity constructor and getters test 1 failed.");
        }

        // Testing isAvailable method
        if (activity1.isAvailable()) {
            System.out.println("Activity isAvailable test 1 passed.");
        } else {
            System.out.println("Activity isAvailable test 1 failed.");
        }

        // Testing signUp method with available activity
        Passenger passenger1 = new StandardPassenger("John Doe", "P123", 500.0);
        if (activity1.signUp(passenger1)) {
            System.out.println("Activity signUp test 1 passed.");
        } else {
            System.out.println("Activity signUp test 1 failed.");
        }

        // Testing signUp method with full activity
        Activity activity2 = new Activity("Surfing", "Surfing at Bali's beaches", 50.0, 1);
        activity2.signUp(passenger1);
        Passenger passenger2 = new StandardPassenger("Jane Doe", "P456", 500.0);
        if (!activity2.signUp(passenger2)) {
            System.out.println("Activity signUp test 2 passed.");
        } else {
            System.out.println("Activity signUp test 2 failed.");
        }

        // Testing setters and getters
        activity1.setName("Snorkeling");
        activity1.setDescription("Snorkeling at the Hawaiian reef");
        activity1.setCost(80.0);
        activity1.setCapacity(10);
        activity1.setCurrentCount(5);
        if (activity1.getName().equals("Snorkeling") && activity1.getDescription().equals("Snorkeling at the Hawaiian reef") &&
                activity1.getCost() == 80.0 && activity1.getCapacity() == 10 && activity1.getCurrentCount() == 5) {
            System.out.println("Activity setters and getters test passed.");
        } else {
            System.out.println("Activity setters and getters test failed.");
        }

        try {
            activity1.setCost(-100.0);
            System.out.println("Activity setCost with negative value test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Activity setCost with negative value test passed.");
        }

        try {
            activity1.setCapacity(-5);
            System.out.println("Activity setCapacity with negative value test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Activity setCapacity with negative value test passed.");
        }

        try {
            activity1.setCurrentCount(10);
            System.out.println("Activity setCurrentCount exceeding capacity test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Activity setCurrentCount exceeding capacity test passed.");
        }

        //manual verification of the console output.
        activity1.printActivityDetails();
        System.out.println("Activity printActivityDetails test passed.");

        if (!activity1.signUp(passenger1)) {
            System.out.println("Activity signUp with already signed up passenger test passed.");
        } else {
            System.out.println("Activity signUp with already signed up passenger test failed.");
        }

    }
}


//3. destination test class
class DestinationTest {
    public static void main(String[] args) {
        // Test constructor and getters
        Destination destination = new Destination("Test Destination");
        if (destination.getName().equals("Test Destination") && destination.getActivities().size() == 0) {
            System.out.println("Destination constructor and getters test passed.");
        } else {
            System.out.println("Destination constructor and getters test failed.");
        }

        // Test addActivity
        Activity activity1 = new Activity("Test Activity 1", "Test Description 1", 100.0, 5);
        destination.addActivity(activity1);
        if (destination.getActivities().size() == 1 && destination.getActivities().contains(activity1)) {
            System.out.println("Destination addActivity test passed.");
        } else {
            System.out.println("Destination addActivity test failed.");
        }

        // Test equals and hashCode
        Destination destination2 = new Destination("Test Destination");
        if (destination.equals(destination2) && destination.hashCode() == destination2.hashCode()) {
            System.out.println("Destination equals and hashCode test passed.");
        } else {
            System.out.println("Destination equals and hashCode test failed.");
        }

        // Test printItinerary (manual verification of console output)
        destination.printItinerary();
        System.out.println("Destination printItinerary test passed."); // Manual verification required

        Destination destination3 = new Destination("");
        if (destination3.getName().equals("")) {
            System.out.println("Destination constructor with empty name test passed.");
        } else {
            System.out.println("Destination constructor with empty name test failed.");
        }

        Activity activity2 = new Activity("Test Activity 2", "Test Description 2", 150.0, 10);
        destination.addActivity(activity2);
        if (destination.getActivities().size() == 2 && destination.getActivities().contains(activity2)) {
            System.out.println("Destination add multiple activities test passed.");
        } else {
            System.out.println("Destination add multiple activities test failed.");
        }

        destination.addActivity(activity1);
        if (destination.getActivities().size() == 2) {
            System.out.println("Destination add duplicate activity test passed.");
        } else {
            System.out.println("Destination add duplicate activity test failed.");
        }

        Destination destination4 = new Destination("Different Destination");
        if (!destination.equals(destination4)) {
            System.out.println("Destination equals with different names test passed.");
        } else {
            System.out.println("Destination equals with different names test failed.");
        }

        if (!destination.equals(null)) {
            System.out.println("Destination equals with null test passed.");
        } else {
            System.out.println("Destination equals with null test failed.");
        }

        if (!destination.equals(new Object())) {
            System.out.println("Destination equals with different type test passed.");
        } else {
            System.out.println("Destination equals with different type test failed.");
        }

    }
}


//4. passenger test classes

//standard passenger
class StandardPassengerTest {

    public static void main(String[] args) {
        testConstructor();
        testSignUpForActivity();
        testBalanceManagement();
    }

    public static void testConstructor() {
        try {
            StandardPassenger passenger = new StandardPassenger("John Doe", "12345", 1000.0);
            if ("John Doe".equals(passenger.getName()) &&
                    "12345".equals(passenger.getPassengerNumber()) &&
                    passenger.getBalance() == 1000.0) {
                System.out.println("testConstructor: Passed");
            } else {
                System.out.println("testConstructor: Failed");
            }
        } catch (Exception e) {
            System.out.println("testConstructor: Failed with exception: " + e.getMessage());
        }
    }

    public static void testSignUpForActivity() {
        // Assuming Activity class and its methods are defined elsewhere
        try {
            StandardPassenger passenger = new StandardPassenger("John Doe", "12345", 1000.0);
            Activity activity = new Activity("Skiing", "Icy Skiing", 5, 1);
            if (passenger.signUpForActivity(activity)) {
                System.out.println("testSignUpForActivity: Passed");
            } else {
                System.out.println("testSignUpForActivity: Failed");
            }
        } catch (Exception e) {
            System.out.println("testSignUpForActivity: Failed with exception: " + e.getMessage());
        }
    }

    public static void testBalanceManagement() {
        try {
            StandardPassenger passenger = new StandardPassenger("John Doe", "12345", 1000.0);
            passenger.setBalance(-100); // Should throw an IllegalArgumentException
            System.out.println("testBalanceManagement: Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("testBalanceManagement: Passed");
        } catch (Exception e) {
            System.out.println("testBalanceManagement: Failed with exception: " + e.getMessage());
        }
    }
}


//gold passenger
class GoldPassengerTest {

    public static void main(String[] args) {
        testConstructor();
        testSignUpForActivity();
        testBalanceManagement();
    }

    public static void testConstructor() {
        try {
            GoldPassenger passenger = new GoldPassenger("Jane Doe", "67890", 1000.0);
            if ("Jane Doe".equals(passenger.getName()) &&
                    "67890".equals(passenger.getPassengerNumber()) &&
                    passenger.getBalance() == 1000.0) {
                System.out.println("testConstructor: Passed");
            } else {
                System.out.println("testConstructor: Failed");
            }
        } catch (Exception e) {
            System.out.println("testConstructor: Failed with exception: " + e.getMessage());
        }
    }

    public static void testSignUpForActivity() {
        // Assuming Activity class and its methods are defined elsewhere
        try {
            GoldPassenger passenger = new GoldPassenger("Jane Doe", "67890", 1000.0);
            Activity activity = new Activity("Skiing", "cy skiing", 190, 2);
            if (passenger.signUpForActivity(activity)) {
                System.out.println("testSignUpForActivity: Passed");
            } else {
                System.out.println("testSignUpForActivity: Failed");
            }
        } catch (Exception e) {
            System.out.println("testSignUpForActivity: Failed with exception: " + e.getMessage());
        }
    }

    public static void testBalanceManagement() {
        try {
            GoldPassenger passenger = new GoldPassenger("Jane Doe", "67890", 1000.0);
            passenger.setBalance(-100); // Should throw an IllegalArgumentException
            System.out.println("testBalanceManagement: Failed");
        } catch (IllegalArgumentException e) {
            System.out.println("testBalanceManagement: Passed");
        } catch (Exception e) {
            System.out.println("testBalanceManagement: Failed with exception: " + e.getMessage());
        }
    }
}


//premium passenger test
class PremiumPassengerTest {

    public static void main(String[] args) {
        testConstructor();
        testSignUpForActivity();
    }

    public static void testConstructor() {
        try {
            PremiumPassenger passenger = new PremiumPassenger("Jim Doe", "11121");
            if ("Jim Doe".equals(passenger.getName()) &&
                    "11121".equals(passenger.getPassengerNumber())) {
                System.out.println("testConstructor: Passed");
            } else {
                System.out.println("testConstructor: Failed");
            }
        } catch (Exception e) {
            System.out.println("testConstructor: Failed with exception: " + e.getMessage());
        }
    }

    public static void testSignUpForActivity() {
        // Assuming Activity class and its methods are defined elsewhere
        try {
            PremiumPassenger passenger = new PremiumPassenger("Jim Doe", "11121");
            Activity activity = new Activity("Skiing", "Icy skiing", 120, 1);
            if (passenger.signUpForActivity(activity)) {
                System.out.println("testSignUpForActivity: Passed");
            } else {
                System.out.println("testSignUpForActivity: Failed");
            }
        } catch (Exception e) {
            System.out.println("testSignUpForActivity: Failed with exception: " + e.getMessage());
        }
    }
}


