package google_drive;

public abstract class FileOperation {
    public final void execute() {
        if (validate()) {
            performOperation();
            logOperation();
        } else {
            System.out.println("Operation validation failed. Aborting operation!");
        }
    }

    protected abstract boolean validate();
    protected abstract void performOperation();
    protected void logOperation() {
        System.out.println("Operation performed! " + this.getClass().getSimpleName());
    }
}
