import java.util.List;

// File: Main.java
public class TaskTrackerCLI{
    public static void main(String[] args) {

        if (args[0] == null){
            System.out.println("Please enter a command:" +
                    "Add\n" +
                    "Update\n" +
                    "Delete\n" +
                    "Mark-in-progress\n" +
                    "Mark-done\n" +
                    "list\n");

            throw new AssertionError();
        }
        String command = (args[0]).toLowerCase();

        String filename = "tasks.json";
        String path = TaskFileManager.createFileIfNotExists(filename); //creates the json file if it does not exist
        if (path == null){
            System.out.println("The file could not be created and found, exiting program...");
            System.exit(1);
        }

        // take the json and parse it into an array with objects
        List<String> taskList = TaskFileManager.getFileContent(path);
        switch (command){
            case "add": {
                String taskDescription = args[1];
                if (taskDescription == ""){
                    System.out.println("Please enter a task description with add <task description>");
                }

                TaskFileManager.addFileContent(taskList, taskDescription);
                // parse it back into string
                // write back into the json file
                break;
            }

            case "delete": {
                int taskId = Integer.parseInt(args[1]);
                TaskFileManager.deleteFileContent(taskList, taskId);
                break;
            }

            case "update": {
                int updateId = Integer.parseInt(args[1]);
                String updateDescription = args[2];
                TaskFileManager.updateFileContent(taskList, updateId, updateDescription);
                break;
            }

            case "mark-in-progress": {
                int id = Integer.parseInt(args[1]);
                String status = "In-Progress";
                TaskFileManager.updateStatus(taskList, id, status);
                break;
            }
            case "mark-done": {
                int id = Integer.parseInt(args[1]);
                String status = "Done";
                TaskFileManager.updateStatus(taskList, id, status);
                break;
            }

            case "list": {

                if (args.length < 2) {
                    // no second argument was given
                    TaskFileManager.listAllTask(taskList);
                    System.out.println("You can filter the list by passing the statuses of each task: \n" +
                            "list todo\n" +
                            "list in-progress\n" +
                            "list done");
                } else {
                    // second argument exists
                    String command2 = args[1];

                    TaskFileManager.listAllTaskSecondary(taskList, command2);
                }

                break;
            }
            default:
                System.out.println("Please enter a valid command: \n" +
                        "add <task description>\n" +
                        "update <task id> <new task description> \n" +
                        "delete <taskid> \n" +
                        "mark-in-progress <task id>\n" +
                        "mark-done <task id> \n" +
                        "list \n" +
                        "list done\n" +
                        "list todo\n" +
                        "list in-progress");

        }

    }
}


