import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This is the entity class
public class Task {
    private int id;
    private String description;
    private String status;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    public Task(int id, String description){
        this.id = id;
        this.description = description;
        this.status = "ToDo";                // default status
        this.createdAt = LocalDateTime.now();    // default to now
        this.updatedAt = LocalDateTime.now();    // default to now
    }

    public static Task fromJsonToObject(String json){
        // method to parse the json string into Task objects
        try{
            json = json.trim();
            json = json.replace("{", "").replace("}", "").replace("\"" , "");
            String[] splittedJson = json.split(",");

            String id = splittedJson[0].split(":")[1].strip();
            String description = splittedJson[1].split(":")[1].strip();
            String statusString = splittedJson[2].split(":")[1].strip();
            String createdAtStr = splittedJson[3].split("[a-z]:")[1].strip();
            String updatedAtStr = splittedJson[4].split("[a-z]:")[1].strip();

            // create a new task to edit the default values with the existing task's values
            int idValue = Integer.parseInt(id);
            Task task = new Task(idValue, description);

            task.status = statusString;
            task.createdAt = LocalDateTime.parse(createdAtStr);
            task.updatedAt = LocalDateTime.parse(updatedAtStr);

            return task;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public int getId(){
        return id;
    }

    public void setDescription(String updateDescription){
        this.description = updateDescription;
    }

    public String getDescription(){
        return description;
    }

    public void setStatus(String updateStatus){
        this.status = updateStatus;
    }

    public String getStatus(){
        return status;
    }

    public String getCreatedAt(){
        return (createdAt).format(formatter);
    }

    public String getUpdatedAt(){
        return (updatedAt).format(formatter);
    }

    public static String fromObjectToJson(Task task) {
        // create the json

        return "{"
                + "\"id\":" + task.getId() + ","
                + "\"description\":\"" + task.getDescription() + "\","
                + "\"status\":\"" + task.getStatus() + "\","
                + "\"createdAt\":\"" + task.createdAt + "\","
                + "\"updatedAt\":\"" + task.updatedAt + "\""
                + "}";
    }
}
