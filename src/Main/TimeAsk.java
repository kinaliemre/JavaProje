package Main;

import java.time.LocalDateTime;


public class TimeAsk extends Task {
  
	private LocalDateTime startTime;
    private int durationMinutes;
 // TimeAsk oluşturur; Task alanlarını super ile kurar, başlangıç ve süreyi set eder
    public TimeAsk(int id, String title, String description, LocalDateTime startTime, int durationMinutes) {
    
    	super(id, title, description);
    
    	this.startTime = startTime;
    
    	this.durationMinutes = durationMinutes;
    
    
    }
 // Başlangıç zamanını döndürür
    public LocalDateTime getStartTime() {
        return startTime;
    }
 // Süreyi dakika cinsinden döndürür
    public int getDurationMinutes() {
        return durationMinutes;
    }
 // Bitiş zamanını (start + duration) hesaplayıp döndürür
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }
 // Task bilgisini genişletip TimeAsk'e özel alanları da yazdırır
    @Override
    public String toString() {
        return super.toString() +
               " | Başlangıç: " + startTime +
               " | Süre(dk): " + durationMinutes;
    }
}
    
    
    
    
    
    
    

