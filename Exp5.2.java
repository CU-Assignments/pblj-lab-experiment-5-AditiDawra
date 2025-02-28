import java.util.*;

class TicketBookingSystem {
    private boolean[] seats;

    public TicketBookingSystem(int numberOfSeats) {
        seats = new boolean[numberOfSeats];
    }

    public synchronized boolean bookSeat(int seatNumber, String user) {
        if (seatNumber < 0 || seatNumber >= seats.length) {
            System.out.println(user + " attempted to book an invalid seat: " + seatNumber);
            return false;
        }
        if (!seats[seatNumber]) {
            seats[seatNumber] = true;
            System.out.println(user + " successfully booked seat " + seatNumber);
            return true;
        } else {
            System.out.println(user + " attempted to book an already booked seat: " + seatNumber);
            return false;
        }
    }
}

class User extends Thread {
    private TicketBookingSystem system;
    private int seatNumber;
    private String userType;

    public User(TicketBookingSystem system, int seatNumber, String userType, int priority) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.userType = userType;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, userType);
    }
}

public class TicketBooking {
    public static void main(String[] args) {
        int numberOfSeats = 10;
        TicketBookingSystem system = new TicketBookingSystem(numberOfSeats);
        
        List<Thread> users = new ArrayList<>();
        users.add(new User(system, 2, "VIP User 1", Thread.MAX_PRIORITY));
        users.add(new User(system, 5, "VIP User 2", Thread.MAX_PRIORITY));
        users.add(new User(system, 2, "Regular User 1", Thread.NORM_PRIORITY));
        users.add(new User(system, 5, "Regular User 2", Thread.NORM_PRIORITY));
        users.add(new User(system, 7, "Regular User 3", Thread.MIN_PRIORITY));
        
        for (Thread user : users) {
            user.start();
        }
        
        for (Thread user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Booking process completed.");
        
        // Autoboxing and Unboxing Example
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = calculateSum(numbers);
        System.out.println("Sum of numbers: " + sum);
    }
    
    public static int parseStringToInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + number);
            return 0;
        }
    }
    
    public static int calculateSum(List<Integer> numbers) {
        int sum = 0;
        for (int num : numbers) { // Unboxing
            sum += num;
        }
        return sum;
    }
}
