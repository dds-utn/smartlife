package ar.edu.utn.ba.ddsi.smartlife.logging;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.Logger;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggerFactory;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.DatabaseAdapter;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.ConsoleErrorLoggingStrategy;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.DatabaseErrorLoggingStrategy;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.ErrorLoggingStrategy;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.utils.LoggedErrorToMapConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LoggerTests {

    private static final String SERVICE_NAME = "test-service";
    private static final Exception TEST_EXCEPTION =
            new IllegalArgumentException("valor inválido");

    private Logger consoleLogger;

    @BeforeEach
    void setUp() {
        consoleLogger = LoggerFactory.createConsoleLogger(SERVICE_NAME);
    }

    @Test
    void log_sinNombreDeServicio_usaElNombreConfigurado() {
        assertDoesNotThrow(() -> consoleLogger.log(TEST_EXCEPTION));
    }

    @Test
    void log_conNombreDeServicioExplicito_sobreescribeElDefault() {
        assertDoesNotThrow(() -> consoleLogger.log(TEST_EXCEPTION, "otro-servicio"));
    }

    @Test
    void loggedError_capturaInformacionCompleta() {
        LoggedError error = LoggedError.of(TEST_EXCEPTION, SERVICE_NAME);

        assertNotNull(error.timestamp());
        assertEquals("java.lang.IllegalArgumentException", error.exceptionType());
        assertEquals("valor inválido", error.message());
        assertNotNull(error.stackTrace());
        assertFalse(error.stackTrace().isBlank());
        assertEquals(SERVICE_NAME, error.serviceName());
    }

    @Test
    void loggedError_conMensajeNulo_usaCadenaVacia() {
        Exception sinMensaje = new RuntimeException();
        LoggedError error = LoggedError.of(sinMensaje, SERVICE_NAME);
        assertEquals("", error.message());
    }

    @Test
    void logWithDBStrategy_llamaConnectInsertDisconnect() {
        DatabaseAdapter mockAdapter = mock(DatabaseAdapter.class);
        Logger dbLogger = LoggerFactory.createDatabaseLogger(SERVICE_NAME, mockAdapter);

        dbLogger.log(TEST_EXCEPTION);

        verify(mockAdapter, times(1)).connect();
        verify(mockAdapter, times(1)).insert(
                eq(DatabaseErrorLoggingStrategy.TABLE_OR_COLLECTION),
                any(Map.class)
        );
        verify(mockAdapter, times(1)).disconnect();
    }

    @Test
    void logWithDBStrategy_lasClavesDelmapaSonLasEsperadas() {
        LoggedError error = LoggedError.of(TEST_EXCEPTION, SERVICE_NAME);
        Map<String, Object> map = LoggedErrorToMapConverter.toMap(error);

        assertTrue(map.containsKey("timestamp"));
        assertTrue(map.containsKey("exceptionType"));
        assertTrue(map.containsKey("message"));
        assertTrue(map.containsKey("stackTrace"));
        assertTrue(map.containsKey("serviceName"));
    }

    @Test
    void logWithDBStrategy_siAdapterFalla_propagaExcepcion() {
        DatabaseAdapter mockAdapter = mock(DatabaseAdapter.class);
        doThrow(new RuntimeException("BD no disponible")).when(mockAdapter).connect();
        Logger dbLogger = LoggerFactory.createDatabaseLogger(SERVICE_NAME, mockAdapter);

        assertThrows(RuntimeException.class, () -> dbLogger.log(TEST_EXCEPTION));
    }

    @Test
    void logToFile_creaArchivoSiNoExiste(@TempDir Path tempDir) throws IOException {
        Path logFile = tempDir.resolve("errors.log");
        assertFalse(Files.exists(logFile));

        Logger fileLogger = LoggerFactory.createFileLogger(SERVICE_NAME, logFile.toString());
        fileLogger.log(TEST_EXCEPTION);

        assertTrue(Files.exists(logFile));
    }

    @Test
    void logToFile_appendaSinSobrescribir(@TempDir Path tempDir) throws IOException {
        Path logFile = tempDir.resolve("errors.log");
        Logger fileLogger = LoggerFactory.createFileLogger(SERVICE_NAME, logFile.toString());

        fileLogger.log(TEST_EXCEPTION);
        fileLogger.log(new RuntimeException("segundo error"));

        String contenido = Files.readString(logFile);
        assertTrue(contenido.contains("IllegalArgumentException"));
        assertTrue(contenido.contains("RuntimeException"));
        assertTrue(contenido.contains("segundo error"));
    }

    @Test
    void logToFile_contieneInformacionObligatoria(@TempDir Path tempDir) throws IOException {
        Path logFile = tempDir.resolve("errors.log");
        Logger fileLogger = LoggerFactory.createFileLogger(SERVICE_NAME, logFile.toString());

        fileLogger.log(TEST_EXCEPTION);

        String contenido = Files.readString(logFile);
        assertTrue(contenido.contains("timestamp"));
        assertTrue(contenido.contains("service"));
        assertTrue(contenido.contains("type"));
        assertTrue(contenido.contains("message"));
        assertTrue(contenido.contains("stack trace"));
    }

    @Test
    void consoleStrategy_noLanzaExcepcion() {
        ErrorLoggingStrategy consoleStrategy = new ConsoleErrorLoggingStrategy();
        LoggedError error = LoggedError.of(TEST_EXCEPTION, SERVICE_NAME);
        assertDoesNotThrow(() -> consoleStrategy.logError(error));
    }
}
