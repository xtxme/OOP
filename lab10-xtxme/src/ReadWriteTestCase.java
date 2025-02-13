import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteTest {

    @Test
    void testCalculation_ValidExpressions() {
        assertEquals(124, readWrite.calculation("99 + 26 - 1"));
        assertEquals(350, readWrite.calculation("100 * 7 / 2"));
        assertEquals(30, readWrite.calculation("100 % 30 + 20"));
        assertEquals(293, readWrite.calculation("23 + 45 * 6"));
        assertEquals(44, readWrite.calculation("89 / 2"));
        assertEquals(1, readWrite.calculation("89 % 2 % 3"));
    }

    @Test
    void testCalculation_InvalidExpressions() {
        assertThrows(IllegalArgumentException.class, () -> readWrite.calculation("2 3 +"));
        assertThrows(IllegalArgumentException.class, () -> readWrite.calculation("5 + "));
        assertThrows(IllegalArgumentException.class, () -> readWrite.calculation("5 + - 10"));
    }

    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class,
                () -> readWrite.calculation("10 / 0"));
        assertEquals("Division by zero", exception.getMessage());

        Exception exception1 = assertThrows(ArithmeticException.class,
                () -> readWrite.calculation("5 / 0 % 0  "));
        assertEquals("Division by zero", exception1.getMessage());
    }


    @Test
    void testModuloByZero() {
        Exception exception = assertThrows(ArithmeticException.class,
                () -> readWrite.calculation("5 % 0"));
        assertEquals("Modulo by zero", exception.getMessage());
    }

    //เมื่อพบตัวเลขที่เป็นทศนิยม
    @Test
    void testFloatingPointToken() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> readWrite.calculation("5 + 10.8"));
        assertEquals("Invalid token: 10.8", exception.getMessage());
    }

    @Test
    void testInvalidToken() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> readWrite.calculation("Hello"));
        assertEquals("Invalid token: Hello", exception.getMessage());
    }


    @Test
    void testReadFile_ValidFile() {
        String inputFile = "input.txt";
        createTestFile(inputFile);
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            fail("Failed to read the input file: " + e.getMessage());
        }

        assertFalse(content.toString().isEmpty(), "The file content should not be empty.");
        assertTrue(content.toString().contains("99 + 26 - 1"), "The file should contain the expression '99 + 26 - 1'.");
        assertTrue(content.toString().contains("100 * 7 / 2"), "The file should contain the expression '100 * 7 / 2'.");
        assertTrue(content.toString().contains("10 / 2"), "The file should contain the expression '10 / 2'.");
    }

    private void createTestFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("99 + 26 - 1");
            writer.newLine();
            writer.write("100 * 7 / 2");
            writer.newLine();
            writer.write("10 / 2");
            writer.newLine();
        } catch (IOException e) {
            fail("Failed to create the input file: " + e.getMessage());
        }
    }

    @Test
    void testWriteFile() {
        String testFile = "testOutput.txt";
        String content = "Test write content.";
        try {
            Files.write(Paths.get(testFile), content.getBytes());

            String fileContent = Files.readString(Paths.get(testFile));
            assertEquals(content, fileContent);

        } catch (IOException e) {
            fail("Unexpected IOException: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(Paths.get(testFile));
            } catch (IOException ignored) {
            }
        }
    }
}
