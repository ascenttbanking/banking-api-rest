package com.ascentt.bankingservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileStorageServiceTest {

    @InjectMocks
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileStorageService = new FileStorageService();
    }

    @Test
    public void testStoreFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        String storedFilePath = fileStorageService.storeFile(mockFile);

        Path path = Path.of(storedFilePath);
        assertTrue(Files.exists(path));
        Files.delete(path); // Limpiar el archivo almacenado después de la prueba
    }
}
