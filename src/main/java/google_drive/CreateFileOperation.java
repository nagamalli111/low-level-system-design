package google_drive;

public class CreateFileOperation extends FileOperation {

    private Folder folder;
    private String fileName;
    @Override
    protected boolean validate() {
        if (folder.getChildern().containsKey(fileName))
            return false;

        return true;
    }

    @Override
    protected void performOperation() {
        folder.add(new File(fileName));
    }
}
