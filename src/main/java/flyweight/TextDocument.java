//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextDocument {

    private final List<StyledCharacter> characters;
    private final CharacterStyleFactory factory;

    public TextDocument(CharacterStyleFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }

        this.factory = factory;
        this.characters = new ArrayList<>();
    }

    public void addText(String text, String font, int size, String color) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        CharacterStyle style = factory.getStyle(font, size, color);

        for (char character : text.toCharArray()) {
            characters.add(new StyledCharacter(character, style));
        }
    }

    public void editCharacter(
            int index,
            char newCharacter,
            String font,
            int size,
            String color) {

        if (index < 0 || index >= characters.size()) {
            throw new IndexOutOfBoundsException("Invalid character index");
        }

        CharacterStyle style = factory.getStyle(font, size, color);
        characters.set(index, new StyledCharacter(newCharacter, style));
    }

    public String getText() {
        StringBuilder text = new StringBuilder();

        for (StyledCharacter character : characters) {
            text.append(character.getCharacter());
        }

        return text.toString();
    }

    public void displayDocument() {
        System.out.println("Document: " + getText());
        System.out.println("Character formatting:");

        for (int i = 0; i < characters.size(); i++) {
            StyledCharacter character = characters.get(i);
            CharacterStyle style = character.getStyle();

            System.out.println(
                    character.getCharacter()
                            + " | Font: " + style.getFont()
                            + " | Size: " + style.getSize()
                            + " | Color: " + style.getColor()
            );
        }
    }

    public int getCharacterCount() {
        return characters.size();
    }

    public int getUniqueStyleCount() {
        return factory.getStyleCount();
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of(fileName))) {

            for (StyledCharacter character : characters) {
                CharacterStyle style = character.getStyle();

                writer.write(
                        (int) character.getCharacter()
                                + "|" + style.getFont()
                                + "|" + style.getSize()
                                + "|" + style.getColor()
                );

                writer.newLine();
            }
        }
    }

    public static TextDocument loadFromFile(
            String fileName,
            CharacterStyleFactory factory) throws IOException {

        TextDocument document = new TextDocument(factory);

        try (BufferedReader reader =
                     Files.newBufferedReader(Path.of(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);

                if (parts.length != 4) {
                    throw new IOException("Invalid document format");
                }

                try {
                    char character = (char) Integer.parseInt(parts[0]);
                    String font = parts[1];
                    int size = Integer.parseInt(parts[2]);
                    String color = parts[3];

                    CharacterStyle style = factory.getStyle(font, size, color);

                    document.characters.add(new StyledCharacter(character, style));

                } catch (NumberFormatException e) {
                    throw new IOException("Invalid character or size in file", e);
                }
            }
        }

        return document;
    }
}
