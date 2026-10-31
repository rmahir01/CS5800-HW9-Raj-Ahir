//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

import java.util.HashMap;
import java.util.Map;

public class CharacterStyleFactory {

    private final Map<CharacterStyle, CharacterStyle> styles = new HashMap<>();

    public CharacterStyle getStyle(String font, int size, String color) {
        CharacterStyle requested = new CharacterStyle(font, size, color);
        CharacterStyle existing = styles.get(requested);

        if (existing != null) {
            return existing;
        }

        styles.put(requested, requested);
        System.out.println("Created new style: " + font + ", " + size + ", " + color);
        return requested;
    }

    public int getStyleCount() {
        return styles.size();
    }
}

