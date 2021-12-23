package ru.ivmiit.repositories;

import ru.ivmiit.models.FileInfo;

import java.util.Optional;

public interface FilesRepository {
    public FileInfo save(FileInfo entity);
    public void update(Long id, FileInfo item);
    public Optional<FileInfo> findById(Long id);
}
