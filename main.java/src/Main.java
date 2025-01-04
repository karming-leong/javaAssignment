import java.util.*;

// Base Class
class Task {
    private int id;
    private String description;
    private double priority;
    private String status;

    public Task(int id, String description, double priority, String status) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Description: " + description + ", Priority: " + priority + ", Status: " + status;
    }
}

// Inherited Class
class DetailedTask extends Task {
    private String dueDate;

    public DetailedTask(int id, String description, double priority, String status, String dueDate) {
        super(id, description, priority, status);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return super.toString() + ", Due Date: " + dueDate;
    }
}

// Interface
interface TaskOperations {
    void createTask();
    void readTasks();
    void updateTask(int id);
    void deleteTask(int id);
}

// Implementation Class
class TaskManager implements TaskOperations {
    private List<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void createTask() {
        System.out.print("Enter Task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Priority (0.0 - 5.0): ");
        double priority = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Status (incomplete/completed): ");
        String status = scanner.nextLine();

        System.out.print("Is this a detailed task? (yes/no): ");
        String isDetailed = scanner.nextLine();

        if (isDetailed.equalsIgnoreCase("yes")) {
            System.out.print("Enter Due Date (dd/mm/yy): ");
            String dueDate = scanner.nextLine();
            tasks.add(new DetailedTask(id, description, priority, status, dueDate));
        } else {
            tasks.add(new Task(id, description, priority, status));
        }

        System.out.println("Task created successfully!");
    }

    @Override
    public void readTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            tasks.forEach(System.out::println);
        }
        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine();  // This will consume the Enter key press
    }

    @Override
    public void updateTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                System.out.print("Enter new Description: ");
//                scanner.nextLine(); // Consume newline
                String description = scanner.nextLine();
                task.setDescription(description);

                System.out.print("Enter new Priority (0.0 - 5.0): ");
                double priority = scanner.nextDouble();
                scanner.nextLine();
                task.setPriority(priority);

                System.out.print("Enter new Status (incomplete/completed): ");
                String status = scanner.nextLine();
                task.setStatus(status);

                if (task instanceof DetailedTask) {
                    System.out.print("Enter new Due Date (dd/mm/yy): ");
                    String dueDate = scanner.nextLine();
                    ((DetailedTask) task).setDueDate(dueDate);
                }

                System.out.println("Task updated successfully!");
                return;
            }
        }

        System.out.println("Task ID not found.");
    }

    @Override
    public void deleteTask(int id) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                System.out.println("Task deleted successfully!");
                return;
            }
        }

        System.out.println("Task ID not found.");
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTask Manager");
            System.out.println("1. Create Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.createTask();
                    break;
                case 2:
                    manager.readTasks();
                    break;
                case 3:
                    System.out.print("Enter Task ID to update: ");
                    int updateId = scanner.nextInt();
                    manager.updateTask(updateId);
                    break;
                case 4:
                    System.out.print("Enter Task ID to delete: ");
                    int deleteId = scanner.nextInt();
                    manager.deleteTask(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
