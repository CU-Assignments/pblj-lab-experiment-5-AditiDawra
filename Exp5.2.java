import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', gpa=" + gpa + "}";
    }
}

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
        
        // Serialization and Deserialization Example
        Student student = new Student(1, "John Doe", 3.8);
        serializeStudent(student);
        Student deserializedStudent = deserializeStudent();
        System.out.println("Deserialized Student: " + deserializedStudent);
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
    
    public static void serializeStudent(Student student) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            oos.writeObject(student);
            System.out.println("Student serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Student deserializeStudent() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"))) {
            return (Student) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
        }
        return null;
    }
}

