
package Main;

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

  
    public static List<Task> loadTasks(String fileName) {
        
    		List<Task> tasks = new ArrayList<>();
        
    		File file = new File(fileName);
        
    		if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
           
        		String line;
            
        		while ((line = br.readLine()) != null) {
            
        			if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", -1);
                if (parts.length < 9) continue;

                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                String title = parts[2];
                String description = parts[3];
                boolean completed = Boolean.parseBoolean(parts[4]);
                int priority = Integer.parseInt(parts[5]);

                LocalDateTime dueDate = parts[6].isEmpty()
                        ? null
                        : LocalDateTime.parse(parts[6]);

                LocalDateTime startTime = parts[7].isEmpty()
                        ? null
                        : LocalDateTime.parse(parts[7]);

                int durationMinutes = parts[8].isEmpty()
                        ? 0
                        : Integer.parseInt(parts[8]);

                Task task;
                
                if ("TIMEASK".equals(type) && startTime != null) {
                    task = new TimeAsk(id, title, description, startTime, durationMinutes);
                } else {
                    task = new Task(id, title, description);
                }

                task.setPriority(priority);
                if (dueDate != null) {
                    task.setDeadline(new Deadline(dueDate));
                }
                if (completed) {
                    task.complete();
                }

                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }

        return tasks;
    }
}
