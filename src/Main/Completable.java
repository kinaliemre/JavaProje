package Main;

//Bu arayüz, bir nesnenin "tamamlanabilir" olmasını standartlaştırır
	public interface Completable {
	
		// Bu metot nesneyi tamamlandı durumuna getirir
		void complete();
		// Bu metot nesnenin tamamlanıp tamamlanmadığını döndürür
		boolean isCompleted();
	
	}
	
	

