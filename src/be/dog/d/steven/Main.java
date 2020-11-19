package be.dog.d.steven;

import be.dog.d.steven.placeholders.FooClass;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        /** Java 9 **/
        /** PROCESS API **/
        // More control / methods for operating-system processes
        try {
            Process p = Runtime.getRuntime().exec("help");
            Future<Integer> exitValue = p.onExit().thenApply(Process::exitValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** COLLECTION UTILS **/
        List<Integer> list = List.of(1, 2, 3);
        Set<String> set = Set.of("test1", "test2");
        Map<Integer, String> map = Map.of(1,"test",2,"test");

        /** OPTIONAL::STREAM **/
        List<Optional<String>> listOfOptionals = Arrays.asList(
                Optional.empty(), Optional.of("test"), Optional.empty(), Optional.of("test"));

        // OLD
        List<String> filteredList = listOfOptionals.stream()
                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                .collect(Collectors.toList());
        System.out.println(filteredList);

        // NEW
        filteredList = listOfOptionals.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        System.out.println(filteredList);

        /** DIAMOND OPERATOR EXTENSION **/
        FooClass<Integer> fc = new FooClass<>(1) {
            // anonymous inner class
        };

        FooClass<? extends Integer> fc0 = new FooClass<>(1) {
            // anonymous inner class
        };

        FooClass<?> fc1 = new FooClass<>(1) {
            // anonymous inner class
        };

        /** INTERFACE PRIVATE METHOD **/
        interface InterfaceWithPrivateMethods {

            private static String staticPrivate() {
                return "static private";
            }

            private String instancePrivate() {
                return "instance private";
            }

            default void check() {
                String result = staticPrivate();
                InterfaceWithPrivateMethods pvt = new InterfaceWithPrivateMethods() {
                    // anonymous class
                };
                result = pvt.instancePrivate();
            }
        }


        /** Java 10 **/
        /** COPY OF **/
        List<Integer> copyList = List.copyOf(list);
        // copyList.add(4); // Immutable

        /** TO UNMODIFIABLE **/
        List<Integer> evenList = list.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toUnmodifiableList());
        // evenList.add(4); // Immutable

        /** OR ELSE THROW **/
        Integer firstEven = list.stream()
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow();
        System.out.println(firstEven);


        /** Java 11 **/
        /** STRINGS **/

        var example1 = "    testing...";
        // Repeat
        System.out.println(example1.repeat(3));
        // Strip (unicode standard) ~ Trim
        System.out.println(example1.strip().repeat(3));
        // Blank check
        System.out.println(example1.isBlank());

        var example2 = "Tests:\ntesting...\rtesting...\n...";
        // Lines iterator
        System.out.println(example2.lines().count());
        example2.lines().filter(l -> !l.contains("T")).forEach(System.out::println);


        /** LAMBDAS WITH ANNOTATIONS **/

        Consumer<BigDecimal> moneyConsumer = (@NotNull var money) -> System.out.printf("I have %.2f $\n", money);


        /** HTTP CLIENT **/

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.google.com"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;
        System.out.println(response.body());


        /** UNICODE 10 **/
        System.out.println("\u20BF");


        /** Java 12 **/

        /** STRINGS **/
        // Indent
        System.out.println("Java 12".indent(30));


        /** SWITCH STATEMENT **/

        // OLD
        String test = "c";
        String answer;

        switch (test){
            case "a":
                answer = "The variable was 'a'";
                break;
            case "b":
                answer = "The variable was 'b'";
                break;
            case "c":
                answer = "The variable was 'c'";
                break;
            default:
                answer = "The variable was neither a, b or c";
        }
        System.out.println(answer);


        // NEW
        answer = switch(test){
          case "a" -> "The variable was 'a'";
          case "b" -> "The variable was 'b'";
          case "c" -> "The variable was 'c'";
          default -> "The variable was neither a, b or c";
        };
        System.out.println(answer);


        /** Java 13 **/
        /** SWITCH STATEMENT **/

        // NEW
        answer = switch (test) {
            case "a" -> {
                System.out.println("I am not just yielding!");
                yield "The variable was 'a'";
            }
            case "b" -> {
                System.out.println("Me neither.");
                yield "The variable was 'b'";
            }
            case "c" -> {
                System.out.println("Me neither.");
                yield "The variable was 'c'";
            }
            default -> {
                System.out.println("OK");
                yield "The variable was neither a, b or c";
            }
        };
        System.out.println(answer);


        /** PATTERN MATCHING **/

        // OLD
        Object obj = "Java 13";
        if (obj instanceof String){
            String string = (String) obj;
            System.out.println(string.toUpperCase());
        }

        //NEW
        if(obj instanceof String s){
            System.out.println(s.toUpperCase());
        }


        /** TEXT BLOCKS **/

        // OLD
        String textBlock =
                "This is how\n" +
                "TEXT BLOCKS\n" +
                "had to be concatenated\n" +
                "before this feature";
        System.out.println(textBlock);

        // NEW
        textBlock = """
            This is how we can
                create TEXT BLOCKS
            using this new \
            feature.""";
        System.out.println(textBlock);


        /** Java 14 **/
        /** Records **/

        // OLD
        class Student{
            private String first;
            private String last;
            private int age;

            public Student(String first, String last, int age) {
                this.first = first;
                this.last = last;
                this.age = age;
            }

            public int getAge() {
                return age;
            }

            public String getFirst() {
                return first;
            }

            public String getLast() {
                return last;
            }
        }

        Student s = new Student("Steven", "D'Hondt", 31);

        System.out.println(s.first);
        System.out.println(s.getFirst());

        // NEW
        record Programmer(String first, String last, int age){}
        Programmer p = new Programmer("Steven", "D'Hondt",31);

        System.out.println(p.first);
        System.out.println(p.first());


        /** Java 15 **/
    }
    /** SEALED CLASSES **/
    sealed interface Command permits LoginCommand, LogoutCommand{
        //...

    }

    public final class LoginCommand implements Command{

    }

    public final class LogoutCommand implements Command{

    }
}


