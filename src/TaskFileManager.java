import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This class will do Add, Update, Delete and List
public class TaskFileManager {
    private static final Path FILE_PATH = Path.of("tasks.json");
    private List<Task> tasks; // tasks is a list that contains multiple 'Task' classes

    //CREATE THE FILE IF IT DOESNT EXIST
    public static String createFileIfNotExists(String filename){
        // static means we don't need to have a actual object of the class to use this method
        File file = new File(filename);
        String filePath = null;
        if (!file.exists()){
            // if file don't exist
            try{
                boolean response = file.createNewFile();
                // write a default value for the file when created
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
                filePath = file.getAbsolutePath();
                if (response){
                    System.out.print("Created File Successfully at: " + filePath);
                    return filePath;
                }
            } catch (IOException e){
                System.out.println("Failed to create file");
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            // file already exists, return the filepath
            Path path = Path.of("tasks.json");
            filePath = path.toAbsolutePath().toString();
            return filePath;

        }

        return null;
    }

    // GET FILE CONTENT
    public static List<String> getFileContent(String filePath){
        try{
            // read the file
            String jsonContent = Files.readString(Path.of(filePath));
            // remove the brackets and split based on closing curly bracket and comma
            List<String> taskList = Arrays.asList(
                    jsonContent.replace("[", "")
                    .replace("]", "")
                    .split("},"));
            if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())){
                // do this if only there is something in taskList
                for (int i = 0 ; i < taskList.size() ; i++){
                    // add back the closing bracket, because .split removes it
                    String task = taskList.get(i);
                    if(!task.endsWith("}")){
                        task = task + "}";
                        taskList.set(i,task);
                    }
                }
            }

            return taskList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // CREATE TASKS
    public static void addFileContent(List<String> taskList, String newTaskDescription){
        int maxId = 0;
        boolean response = false;
        List<Task> newTaskList = new ArrayList<>();

        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())){
            for (String taskJson : taskList){
                // for each task (string), parse it manually into objects
                Task task = Task.fromJsonToObject(taskJson);
                // add it into the new taskList
                newTaskList.add(task);
                if (task.getId() > maxId){
                    // get the highest number of id
                    maxId = task.getId();
                }
            }
            maxId = maxId + 1;
        } else {
            // else if taskList is empty, this is the first task
            maxId = 1;
        }



        Task newTask = new Task(maxId, newTaskDescription); // 'new' creates a new instance of the method
        newTaskList.add(newTask);

        if (TaskFileManager.writeToFile(newTaskList)){
            System.out.println("Task added successfully");
        } else {
            System.out.println("Failed to add task");
        }
    }

    // DELETE TASKS
    public static void deleteFileContent(List<String> taskList, int deleteId){
        // if there is something inside the tasklist ...
        boolean flag = false;
        List<Task> newList = new ArrayList<>();
        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())) {
            for (String s : taskList) {
                Task task = Task.fromJsonToObject(s);
                if (task.getId() != deleteId) {
                    // add it to new list that does NOT contain the removed Id
                    newList.add(task);
                } else {
                    flag = true;
                }
            }
        }

        if(TaskFileManager.writeToFile(newList) && flag){
            System.out.println("Task deleted successfully");
        } else {
            System.out.println("Task could not be deleted, please check your task id");
        }
    }

    // UPDATE TASK
    public static void updateFileContent(List<String> taskList, int updateId, String updateDescription) {
        // similar to delete, convert json into object
        List<Task> newList = new ArrayList<>();
        boolean flag = false;

        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())) {
            for (String s : taskList) {
                Task task = Task.fromJsonToObject(s);
                if (task.getId() == updateId) {
                    // add it to new list that does NOT contain the removed Id
                    task.setDescription(updateDescription);
                    flag = true;
                }
                newList.add(task);
            }
        } else {
            System.out.println("The task list is empty");
        }

        // reconstruct the entire list and write to file using writetofile method
        TaskFileManager.writeToFile(newList);
        if (flag){
            System.out.println("The description has been updated");
        } else {
            System.out.println("The task id could not be found");
        }

    }

    public static void updateStatus(List<String> taskList, int id, String status){
        List<Task> newList = new ArrayList<>();
        boolean flag = false;

        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())) {
            // if its not empty, do something...
            for (String s : taskList) {
                Task task = Task.fromJsonToObject(s);
                if (task.getId() == id) {
                    // add it to new list
                    task.setStatus(status);
                    flag = true;
                }
                newList.add(task);
            }
        } else {
            System.out.println("The task list is empty");
        }

        TaskFileManager.writeToFile(newList);
        if (flag){
            System.out.println("Task with Id: " + id + " has been marked as " + status);
        } else {
            System.out.println("Task with Id: " + id + " COULD NOT be marked as " + status +
                    ". Please check your task id");
        }



    }

    public static void listAllTask(List<String> taskList) {

        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())) {
            // if it's not empty, do something...
            for (String s : taskList) {
                Task task = Task.fromJsonToObject(s);
                System.out.println(
                        "----------------------------- \n" +
                        "Id: " + task.getId() + "\n" +
                        "Task: " + task.getDescription() + "\n" +
                        "Status: " + task.getStatus() + "\n" +
                        "Created At: " + task.getCreatedAt() + "\n" +
                        "Updated At: " + task.getUpdatedAt() + "\n" +
                        "-----------------------------"

                );
            }
        }
    }

    public static void listAllTaskSecondary(List<String> taskList, String command2){
        System.out.println("LISTALLTASKDONE ACTIVATED");
        if (!(taskList.size() == 1 && taskList.getFirst().isEmpty())) {
            // if it's not empty, do something...
            for (String s : taskList) {
                Task task = Task.fromJsonToObject(s);
                if(command2.equalsIgnoreCase(task.getStatus())){
                    System.out.println(
                            "----------------------------- \n" +
                            "Id: " + task.getId() + "\n" +
                            "Task: " + task.getDescription() + "\n" +
                            "Status: " + task.getStatus() + "\n" +
                            "Created At: " + task.getCreatedAt() + "\n" +
                            "Updated At: " + task.getUpdatedAt() + "\n" +
                            "-----------------------------"

                    );
                }
            }
        }
    }


    public static boolean writeToFile(List<Task> taskList){
        // helper method to write to file , expects an array of task objects
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("[");


            for (int i = 0 ; i < taskList.size() ; i++){
                sb.append(Task.fromObjectToJson(taskList.get(i)));
                if (i < taskList.size() - 1){
                    sb.append(",\n");
                }
            }
            sb.append("]");

            Files.writeString(FILE_PATH, sb.toString());

            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
