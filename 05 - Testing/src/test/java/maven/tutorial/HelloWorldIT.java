package maven.tutorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldIT {
    @Test
    void getHelloIT() {
        System.out.println("IT test.");
        var hello = new HelloWorld();
        assertEquals("Hello World!", hello.getHello());
    }
}