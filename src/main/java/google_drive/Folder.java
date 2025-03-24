package google_drive;

import java.util.HashMap;
import java.util.Map;

public class Folder implements FileSystemComponent {
    private String name;
    private String content;
    private Map<String, FileSystemComponent> childern;
    private PermissionDecorator permissions;


    public Folder(String name) {
        this.name = name;
        this.content = "";
        this.childern = new HashMap<>();
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent child) {
        this.getChildern().put(child.getName(), child);
    }

    public void remove(FileSystemComponent child) {
        this.getChildern().remove(child.getName());
    }

    public Map<String, FileSystemComponent> getChildern() {
        return childern;
    }

    public PermissionDecorator getPermissions() {
        return permissions;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Folder: " + this.getName());
        for (FileSystemComponent child : this.getChildern().values()) {
            child.display(indent + "     ");
        }
    }
}
