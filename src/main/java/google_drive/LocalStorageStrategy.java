package google_drive;

public class LocalStorageStrategy implements StorageStrategy {
    @Override
    public void saveFile(File file) {
        System.out.println("saved file locally!");
    }

    @Override
    public File loadFile(String fileName) {
        return new File(fileName);
    }
}
