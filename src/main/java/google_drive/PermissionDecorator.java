package google_drive;

import java.util.HashMap;
import java.util.Map;

public class PermissionDecorator {
    private Map<User, Permission> permissions;

    public enum Permission{
        READ, WRITE, NONE
    }

    public PermissionDecorator(){
        permissions = new HashMap<>();
    }

    public void setPermission(User user, Permission permission) {
        permissions.put(user, permission);
    }

    public Permission getPermission(User user) {
        return permissions.getOrDefault(user, Permission.NONE);
    }

}
