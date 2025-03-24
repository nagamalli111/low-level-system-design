package google_drive;

public interface FileSystemComponent {
    String getName();
    void setName(String name);
    void display(String indent);
    PermissionDecorator getPermissions();
}
