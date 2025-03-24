package google_drive;

public class FileSystemProxy implements FileSystemComponent {
    private FileSystemComponent realComponent;
    private User user;

    public FileSystemProxy(FileSystemComponent fileSystemComponent, User user) {
        this.realComponent = fileSystemComponent;
        this.user = user;
    }
    @Override
    public String getName() {
        return this.realComponent.getName();
    }

    @Override
    public void setName(String name) {
        if (hasWritePermission())
            realComponent.setName(name);
    }

    @Override
    public void display(String indent) {
        if (hasReadPermission())
            realComponent.display(indent);
    }

    @Override
    public PermissionDecorator getPermissions() {
        return realComponent.getPermissions();
    }

    private boolean hasReadPermission() {
        PermissionDecorator permissions = getPermissions();
        return permissions.getPermission(this.user) == PermissionDecorator.Permission.READ;
    }

    private boolean hasWritePermission() {
        PermissionDecorator permissions = getPermissions();
        return permissions.getPermission(this.user) == PermissionDecorator.Permission.WRITE;
    }
}
