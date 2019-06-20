import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Risunka {

	private LocalDateTime date;
	private boolean isPainted;
	private String name;
	private static List<String> names = Arrays.asList("Kuchence", "Kote", "Konche", "Slonche", "Jabka", "Kushtichka");
	
	Risunka(){
		this.isPainted = false;
		this.name = Risunka.names.get(new Random().nextInt(Risunka.names.size()));
		this.date = LocalDateTime.now();
	}

	public LocalDateTime getDate() {
		return date;
	}

	public boolean isPainted() {
		return isPainted;
	}

	public String getName() {
		return name;
	}
	
	public void paint() {
		this.isPainted = true;
	}
}
