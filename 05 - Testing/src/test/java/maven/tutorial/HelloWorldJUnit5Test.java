package maven.tutorial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldJUnit5Test {
    @Test
    void getHelloTest() {
        var hello = new HelloWorld();
        assertEquals("Hello World!", hello.getHello());
    }

}