package ru.ivmiit.services.impl;

import ru.ivmiit.models.FileInfo;
import ru.ivmiit.repositories.FilesRepository;
import ru.ivmiit.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {

    private static final String path = "C:\\ptest\\";

    private final FilesRepository filesRepository;

    public FilesServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public FileInfo saveFileToStorage(InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.save(fileInfo);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return fileInfo;
    }

    @Override
    public void writeFileFromStorage(Long fileId, OutputStream outputStream) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
        FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new IllegalArgumentException("File not found"));

        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(Long fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("File not found"));
    }
}