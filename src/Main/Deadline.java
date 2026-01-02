package Main;

import java.time.LocalDateTime;
import java.time.Duration;


public class Deadline {

	private LocalDateTime dueDate;
	  // Bu constructor, deadline (bitiş zamanı) değerini set eder
	public Deadline(LocalDateTime dueDate) {
		this.dueDate = dueDate;
		
	}
	// Bu metot deadline'ın LocalDateTime değerini döndürür
	public LocalDateTime  getDueDate() {
		return dueDate;
	}
	 // Bu metot verilen "now" değerine göre deadline geçti mi kontrol eder
	public boolean isPast(LocalDateTime now) {
        return now.isAfter(dueDate);
		
	}
	// Bu metot deadline'a kaç gün kaldığını (negatif de olabilir) hesaplar
	public long daysleft(LocalDateTime now) {
		return Duration.between(now, dueDate).toDays();
	}

}
