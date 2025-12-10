import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TaskCsvRepository {

    public static void saveTasks(String fileName, List<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                String type = (task instanceof TimeAsk) ? "TIMEASK" : "TASK";

               
                String title = safe(task.getTitle());
                String description = safe(task.getDescription());
                String completed = Boolean.toString(task.isCompleted());
                String priority = Integer.toString(task.getPriority());

                
                String due = "";
                if (task.getDeadline() != null) {
                    due = task.getDeadline().getDueDate().toString();
                
                }

                
                String start = "";
                String duration = "";
                if (task instanceof TimeAsk) {
                    TimeAsk tt = (TimeAsk) task;
                    start = tt.getStartTime().toString();
                    duration = Integer.toString(tt.getDurationMinutes());
                
                }

                
                String line = task.getId() + ";" +
                              type + ";" +
                              title + ";" +
                              description + ";" +
                              completed + ";" +
                              priority + ";" +
                
                              due + ";" +
                              start + ";" +
                              duration;

               
                bw.write(line);
                
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    private static String safe(String s) {
        if (s == null) return "";
        return s.replace(";", ",");
    
    }
}