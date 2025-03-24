package google_drive;

public class Main {
    public static void main(String[] args) {
        User admin = new User("admin", "pass1", User.Role.ADMIN);
        User user1 = new User("user1", "pass1", User.Role.USER);
        User user2 = new User("user2", "pass1", User.Role.USER);

        FileSystemManager manager = FileSystemManager.getInstance();
        Folder root = manager.getRoot();
        root.getPermissions().setPermission(admin, PermissionDecorator.Permission.WRITE);

        root.getPermissions().setPermission(user1, PermissionDecorator.Permission.WRITE);





    }
}
