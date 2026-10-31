//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

import java.io.IOException;

public class FlyweightDriver {

    public static void main(String[] args) {

        CharacterStyleFactory factory = new CharacterStyleFactory();
        TextDocument document = new TextDocument(factory);

        document.addText("Hello", "Arial", 12, "Red");
        document.addText("World", "Calibri", 14, "Blue");
        document.addText("CS", "Verdana", 16, "Black");
        document.addText("5800", "Arial", 14, "Blue");

        System.out.println("=== ORIGINAL DOCUMENT ===");
        document.displayDocument();

        System.out.println("\nDocument text: " + document.getText());
        System.out.println("Total characters: " + document.getCharacterCount());
        System.out.println("Unique styles: " + document.getUniqueStyleCount());

        document.editCharacter(
                0, 'h', "Verdana", 16, "Black" );

        System.out.println("\n=== AFTER EDITING ===");
        System.out.println(document.getText());

        String fileName = "HelloWorldCS5800.txt";

        try {
            document.saveToFile(fileName);
            System.out.println("\nDocument saved successfully.");

            CharacterStyleFactory loadedFactory = new CharacterStyleFactory();

            TextDocument loadedDocument =
                    TextDocument.loadFromFile(
                            fileName, loadedFactory
                    );

            System.out.println("\n=== LOADED DOCUMENT ===");
            loadedDocument.displayDocument();

            System.out.println("\nLoaded text: " + loadedDocument.getText());
            System.out.println("Unique loaded styles: " + loadedDocument.getUniqueStyleCount());

        } catch (IOException e) {
            System.out.println("Error saving or loading document: " + e.getMessage());
        }
    }
}
