import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;

public class Teacher implements Runnable{

	private static final int TEACHER_PERIOD_OF_WRITING_STATS = 3000;
	private static final int TEACHER_RANDOM_PERIOD_OF_TAKING_KID = 2000;
	private GroupType group;
	private String name;
	
	Teacher(String name, GroupType group){
		if(name != null) {
			this.name = name;
		}
		this.group = group;
	}

	@Override
	public void run() {
		
		while(!Kindergarden.getInstance().isKindergardenFull()) {
			
			try {
				Thread.sleep(TEACHER_RANDOM_PERIOD_OF_TAKING_KID);
			} catch (InterruptedException e1) {
				return;
			}
			System.out.println("[ Teacher ] Vzimam sledvashtoto dete, za da go razpredelq");
			Kid kid = null;
			try {
				kid = Kindergarden.getInstance().giveMeNextKid();
			} catch (InterruptedException e) {
				return;
			}
			if(kid != null) {
			 System.out.println("[ Teacher ] Vzeh " + kid.getName() + " i shte go slozha v grupa " + kid.getGroup());
			 Kindergarden.getInstance().addKidToGroup(kid);
			}
			
		}
		
		System.out.println("[ Teacher ] Vsichki deca sa tuk, otivam za moqta grupa !!! ");
		Set<Kid> kidsFromGroup = null;
		try {
			kidsFromGroup = Kindergarden.getInstance().giveMeGroup(group);
		} catch (InterruptedException e) {
			return;
		}
		
		System.out.println("[ Teacher ] Imam grupa, zapochvame zanqtiq ");
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(Kid kid : kidsFromGroup) {
			Thread thread = new Thread(kid);
			thread.start();
			threads.add(thread);
		}
		
		// SAVE stats !!!
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(TEACHER_PERIOD_OF_WRITING_STATS);
			} catch (InterruptedException e) {
				return;
			}
			//write stats
			System.out.println("[ Teacher ] I am writing stats ");
			TreeSet<Statistic> stats = this.takeStats(kidsFromGroup);
			try {
				this.writeStatsInFileAsJSONs(stats);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[Teacher] Kraj na rabotniq den, deca pribirajte se vkushti ");
		threads.stream().forEach(thread -> thread.interrupt());
		
	}
	
	public void writeStatsInFileAsJSONs(TreeSet<Statistic> stats) throws IOException {
		
		String fileName = "statistics".concat(this.group.toString()).concat(".txt");
		File file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
		for(Statistic stat : stats) {
			Gson gson = new Gson();
		    String statJson = gson.toJson(stat);
		    pw.write(statJson);
		}
		pw.close();
	}
	public TreeSet<Statistic> takeStats(Set<Kid> kidsFromGroup) {
		TreeSet<Statistic> stats = new TreeSet<Statistic>((s1, s2) -> {
			if(s1.getNumberOfRisunki() == s2.getNumberOfRisunki()) {
				return s2.getId() - s1.getId();
			}else {
				return s2.getNumberOfRisunki() - s1.getNumberOfRisunki();
			}
		});
		for(Kid kid : kidsFromGroup) {
			stats.add(new Statistic (kid.getName(), kid.getType(), kid.getNumberOfRisunki(), kid.getId(), kid.getRisunki()));
		}
		return stats;
	}
	
	
}
