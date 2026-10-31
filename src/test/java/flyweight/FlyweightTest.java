//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FlyweightTest {

    @TempDir
    Path tempDir;


    @Test
    void testCharacterStyleConstructorAndGetters() {
        CharacterStyle style = new CharacterStyle("Arial", 12, "Red");
        assertEquals("Arial", style.getFont());
        assertEquals(12, style.getSize());
        assertEquals("Red", style.getColor());
    }

    @Test
    void testCharacterStyleEquals() {
        CharacterStyle style1 = new CharacterStyle("Arial", 12, "Red");
        CharacterStyle style2 = new CharacterStyle("Arial", 12, "Red");
        assertEquals(style1, style2);
    }

    @Test
    void testCharacterStyleHashCode() {
        CharacterStyle style1 = new CharacterStyle("Arial", 12, "Red");
        CharacterStyle style2 = new CharacterStyle("Arial", 12, "Red");
        assertEquals(style1.hashCode(), style2.hashCode());
    }

    @Test
    void testDifferentStylesAreNotEqual() {
        CharacterStyle style1 = new CharacterStyle("Arial", 12, "Red");
        CharacterStyle style2 = new CharacterStyle("Calibri", 14, "Blue");
        assertNotEquals(style1, style2);
    }

    @Test
    void testGetStyle() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        CharacterStyle style = factory.getStyle("Arial", 12, "Red");
        assertNotNull(style);
        assertEquals("Arial", style.getFont());
    }

    @Test
    void testStylesAreShared() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        CharacterStyle first = factory.getStyle("Arial", 12, "Red");
        CharacterStyle second = factory.getStyle("Arial", 12, "Red");
        assertSame(first, second);
    }

    @Test
    void testDifferentStylesAreNotShared() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        CharacterStyle first = factory.getStyle("Arial", 12, "Red");
        CharacterStyle second = factory.getStyle("Calibri", 14, "Blue");
        assertNotSame(first, second);
    }

    @Test
    void testGetStyleCount() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        factory.getStyle("Arial", 12, "Red");
        factory.getStyle("Arial", 12, "Red");
        factory.getStyle("Calibri", 14, "Blue");
        assertEquals(2, factory.getStyleCount());
    }

    @Test
    void testStyledCharacterConstructorAndGetters() {
        CharacterStyle style = new CharacterStyle("Arial", 12, "Red");
        StyledCharacter character = new StyledCharacter('A', style);
        assertEquals('A', character.getCharacter());
        assertSame(style, character.getStyle());
    }

    @Test
    void testStyledCharacterToString() {
        CharacterStyle style = new CharacterStyle("Arial", 12, "Red");
        StyledCharacter character = new StyledCharacter('A', style);
        assertNotNull(character.toString());
        assertTrue(character.toString().contains("A"));
    }

    @Test
    void testTextDocumentConstructor() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        assertEquals(0, document.getCharacterCount());
    }

    @Test
    void testConstructorRejectsNullFactory() {
        assertThrows(IllegalArgumentException.class, () -> new TextDocument(null));
    }

    @Test
    void testAddTextAndGetText() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");
        assertEquals("Hello", document.getText());
        assertEquals(5, document.getCharacterCount());
    }

    @Test
    void testAddTextRejectsNull() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        assertThrows(IllegalArgumentException.class,
                () -> document.addText(null, "Arial", 12, "Red"));
    }

    @Test
    void testEditCharacter() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");
        document.editCharacter(0, 'Y', "Calibri", 14, "Blue");
        assertEquals("Yello", document.getText());
    }

    @Test
    void testEditCharacterRejectsInvalidIndex() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");
        assertThrows(IndexOutOfBoundsException.class, () -> document.editCharacter(
                        10, 'Y', "Calibri", 14, "Blue"));
    }

    @Test
    void testGetCharacterCount() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");
        assertEquals(5, document.getCharacterCount());
    }

    @Test
    void testGetUniqueStyleCount() {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");
        document.addText("World", "Calibri", 14, "Blue");
        assertEquals(2, document.getUniqueStyleCount());
    }

    @Test
    void testDisplayDocument() {
        CharacterStyleFactory factory = new CharacterStyleFactory();

        TextDocument document = new TextDocument(factory);
        document.addText("Hi", "Arial", 12, "Red");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        try {
            System.setOut(new PrintStream(output));
            document.displayDocument();
        } finally {
            System.setOut(originalOutput);
        }
        assertTrue(output.toString().contains("Hi"));
        assertTrue(output.toString().contains("Arial"));
    }

    @Test
    void testSaveToFile() throws IOException {
        CharacterStyleFactory factory = new CharacterStyleFactory();

        TextDocument document = new TextDocument(factory);
        document.addText("Hello", "Arial", 12, "Red");

        Path file = tempDir.resolve("test-document.txt");
        document.saveToFile(file.toString());

        assertTrue(Files.exists(file));
        assertTrue(Files.size(file) > 0);
    }

    @Test
    void testLoadFromFile() throws IOException {
        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument original = new TextDocument(factory);

        original.addText("Hello", "Arial", 12, "Red");
        original.addText("World", "Calibri", 14, "Blue");

        Path file = tempDir.resolve("test-document.txt");
        original.saveToFile(file.toString());

        CharacterStyleFactory newFactory = new CharacterStyleFactory();
        TextDocument loaded = TextDocument.loadFromFile(file.toString(), newFactory);

        assertEquals("HelloWorld", loaded.getText());
        assertEquals(10, loaded.getCharacterCount());
        assertEquals(2, loaded.getUniqueStyleCount());
    }
}

