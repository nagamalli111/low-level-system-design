package google_drive;

public interface StorageStrategy {
    void saveFile(File file);
    File loadFile(String fileName);
}
