package swing;

import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    private static LanguageManager instance;
    private String currentLanguage = "ENGLISH";
    private Map<String, Map<String, String>> translations;

    private LanguageManager() {
        translations = new HashMap<>();
        initializeTranslations();
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    private void initializeTranslations() {
        // English translations
        Map<String, String> english = new HashMap<>();
        english.put("PLAY", "PLAY");
        english.put("LOAD_GAME", "LOAD GAME");
        english.put("OPTIONS", "OPTIONS");
        english.put("EXIT", "EXIT");
        english.put("LANGUAGE", "LANGUAGE");
        english.put("SELECT_LANGUAGE", "Select Language");
        english.put("ENGLISH", "English");
        english.put("SPANISH", "Spanish");
        english.put("SAVE", "Save");
        english.put("CANCEL", "Cancel");
        english.put("GAME_TITLE", "PROJECT NULL");
        translations.put("ENGLISH", english);

        // Spanish translations
        Map<String, String> spanish = new HashMap<>();
        spanish.put("PLAY", "JUGAR");
        spanish.put("LOAD_GAME", "CARGAR PARTIDA");
        spanish.put("OPTIONS", "OPCIONES");
        spanish.put("EXIT", "SALIR");
        spanish.put("LANGUAGE", "IDIOMA");
        spanish.put("SELECT_LANGUAGE", "Seleccionar Idioma");
        spanish.put("ENGLISH", "Inglés");
        spanish.put("SPANISH", "Español");
        spanish.put("SAVE", "Guardar");
        spanish.put("CANCEL", "Cancelar");
        spanish.put("GAME_TITLE", "PROYECTO NULL");
        translations.put("SPANISH", spanish);
    }

    public void setLanguage(String language) {
        if (translations.containsKey(language)) {
            this.currentLanguage = language;
        }
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public String getText(String key) {
        Map<String, String> currentTranslations = translations.get(currentLanguage);
        if (currentTranslations != null && currentTranslations.containsKey(key)) {
            return currentTranslations.get(key);
        }
        return key; // Return the key if translation is not found
    }
} 