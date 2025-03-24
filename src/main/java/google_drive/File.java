package google_drive;

import java.util.List;

public class File implements FileSystemComponent {

    private String name;
    private String content;
    private PermissionDecorator permissions;
    private List<FileVersion> versions;
    public File(String fileName) {
        this.name = fileName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + this.getName());
    }

    public PermissionDecorator getPermissions() {
        return permissions;
    }

    public void writeContent(String content) {
        this.content = content;
        versions.add(new FileVersion(content));
    }

    public List<FileVersion> getVersions() {
        return versions;
    }
}
