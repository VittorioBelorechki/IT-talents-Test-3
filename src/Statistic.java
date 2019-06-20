import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Statistic {

	private String name;
	private String type;//golqmo, malko
	private int numberOfRisunki;
	private LocalDateTime time;
	private int id;
	private List<LocalDateTime> timeOfRisunki;
	
	public Statistic(String name, String type, int numberOfRisunki, int id, List<Risunka> risunki) {
		this.name = name;
		this.type = type;
		this.numberOfRisunki = numberOfRisunki;
		this.id = id;
		this.time = LocalDateTime.now();
		this.timeOfRisunki = new ArrayList<LocalDateTime>();
		risunki.stream().forEach(risunka -> this.timeOfRisunki.add(risunka.getDate()));
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getNumberOfRisunki() {
		return numberOfRisunki;
	}

	public LocalDateTime getTime() {
		return time;
	}
	public int getId() {
		return this.id;
	}
	
}
