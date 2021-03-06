package model.storage;

import exception.FileAlreadyExistsException;
import lombok.Getter;
import lombok.Setter;
import model.Archive;

import java.io.File;
import java.util.List;

public class ArchivedStorage extends AbstractStorage {
    @Getter
    @Setter
    private Archive archive;

    public ArchivedStorage(Archive archive) {
        this.archive = archive;
    }

    @Override
    public void addFiles(File... addedFiles) {
        for(var file : addedFiles)
            if(exists(file))
                throw new FileAlreadyExistsException();
        archive.addFiles(addedFiles);
    }

    @Override
    public List<File> getFiles() {
        return archive.unarchive();
    }

    @Override
    public boolean deleteFile(File file) {
        return getFiles().remove(file);
    }
}
