package edu.ccrm.util;

import java.nio.file.*;
import java.io.IOException;

/**
 * A utility class for performing directory backups. It can recursively copy
 * files and subdirectories from a source to a destination.
 * This is pretty crucial for data safety, you know?
 */
public class BackupUtility {

    /**
     * Backs up the contents of a source directory to a destination directory.
     * This operation is recursive, meaning it will copy all files and subdirectories.
     * @param sourcePath The path to the directory that needs to be backed up.
     * @param destinationPath The path to the directory where the backup should be stored.
     * @throws IOException If any I/O error occurs during the backup process.
     */
    public static void backupDirectory(Path sourcePath, Path destinationPath) throws IOException {
        // First things first, let's make sure the source directory actually exists and is a directory.
        if (!Files.exists(sourcePath) || !Files.isDirectory(sourcePath))
            throw new IOException("Source not found or not a directory: " + sourcePath);

        // We need to make sure our target backup directory exists. If not, we'll create it.
        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }

        // Now, let's walk through the source directory, copying everything over.
        // This uses a stream to process each file and directory found.
        Files.walk(sourcePath)
            .forEach(source -> {
                try {
                    // Calculate the relative path of the current item from the source root.
                    Path relativePath = sourcePath.relativize(source);
                    // Construct the full path for the item in the destination.
                    Path destination = destinationPath.resolve(relativePath);

                    // If it's a directory, we just need to make sure it exists in the destination.
                    if (Files.isDirectory(source)) {
                        if (!Files.exists(destination))
                            Files.createDirectories(destination);
                    } else {
                        // If it's a file, we copy it over, replacing it if it already exists.
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    // If something goes wrong during copying, we'll print an error, but try to continue.
                    System.err.println("Oh no! Failed to copy: " + source + " - " + e.getMessage());
                }
            });
    }
}
