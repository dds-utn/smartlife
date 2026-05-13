package ar.edu.utn.frba.ddsi.common.smartlife.logging.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final String DEFAULT_CONFIG_FILE = "smartlife-logging.properties";
    private static final String SYSTEM_PROPERTY_KEY = "smartlife.logging.config";

    private final String configFile;

    public ConfigReader() {
        String override = System.getProperty(SYSTEM_PROPERTY_KEY);
        this.configFile = (override != null && !override.isBlank()) ? override : DEFAULT_CONFIG_FILE;
    }

    public Properties getProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new IOException("No se encontró el archivo de configuración en el classpath: " + configFile);
            }
            props.load(input);
        }
        return props;
    }

    public String getProperty(String key) throws IOException {
        return getProperties().getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        try {
            String value = getProperty(key);
            return (value != null) ? value : defaultValue;
        } catch (IOException e) {
            return defaultValue;
        }
    }
}
