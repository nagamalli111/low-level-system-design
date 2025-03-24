package google_drive;

public class FileSystemSearch {
    public FileSystemComponent search(FileSystemComponent component, String name) {
        if (component.getName().equals(name))
            return component;

        if (component instanceof Folder) {
            for (FileSystemComponent comp : ((Folder) component).getChildern().values()) {
                FileSystemComponent found = search(comp, name);
                if (found != null)
                    return found;
            }
        }
        
        return null;
    }
}
