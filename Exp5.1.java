import java.util.*; 
public class IntegerSumCalculator { 
public static void main(String[] args) { 
List<Integer> numbers = new ArrayList<>(); 
String[] inputs = {"10", "20", "30", "40", "50", "invalid"}; 
for (String input : inputs) { 
Integer num = parseStringToInteger(input); 
if (num != null) { 
numbers.add(num); // Autoboxing: int -> Integer 
} 
} 
int sum = calculateSum(numbers); 
System.out.println("The sum of the list is: " + sum); 
} 
public static Integer parseStringToInteger(String str) { 
try { 
return Integer.parseInt(str); // Parsing string to Integer 
} catch (NumberFormatException e) {
System.out.println("Invalid number format: " + str); 
return null; 
} 
} 
public static int calculateSum(List<Integer> numbers) { 
int sum = 0; 
for (Integer num : numbers) { 
sum += num; // Unboxing: Integer -> int 
} 
return sum; 
}
