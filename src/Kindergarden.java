import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Kindergarden {

	private static final int RANDOM_MAX_NUBER_OF_RISUNKI = 50;
	private static final int RANDOM_MAX_KINDERGARDEN_SIZE = 40;
	private Map<GroupType, HashSet<Kid>> kidsByGroups;
	private ArrayBlockingQueue<Kid> kids;
	private static Kindergarden instance = null;
	private volatile int kidsCounter;
	private ArrayBlockingQueue<Risunka> unpaintedRisunki = new ArrayBlockingQueue<Risunka>(RANDOM_MAX_NUBER_OF_RISUNKI);
	private ArrayBlockingQueue<Risunka> paintedRisunki = new ArrayBlockingQueue<Risunka>(RANDOM_MAX_NUBER_OF_RISUNKI);
	
	Kindergarden(){
		this.kidsByGroups = new ConcurrentHashMap<GroupType, HashSet<Kid>>();
		this.kids = new ArrayBlockingQueue<Kid>(RANDOM_MAX_KINDERGARDEN_SIZE);
		this.kidsCounter = 0;
		
		for(GroupType group : GroupType.values()) {
			this.kidsByGroups.put(group, new HashSet<Kid>());
		}
	}
	
	public void takeUnpaintedRisunka(Risunka risunka) throws InterruptedException {
		if(risunka != null) {
			System.out.println("[ Kindergarden ] Dobavi se risunka za ocvetqvane");
			this.unpaintedRisunki.put(risunka);
//			synchronized(this) {
//				this.notifyAll();
//			}
		}
	}
	
	public void trugvamSi(Kid kid) throws InterruptedException {
		if(this.kids.size() == 1) {
			System.out.println("[ Kindergarden ] Poslednoto dete e " + kid.getName());
		}
		this.kids.take();
	}
	
	public Risunka giveMeRisunka() throws InterruptedException {
		
		Risunka r = this.unpaintedRisunki.take();
		return r;
		
	}
	
	public void takePaintedRisunka(Risunka r) throws InterruptedException {
		if(r != null) {
			this.paintedRisunki.put(r);
		}
	}
	
	public Set<Kid> giveMeGroup(GroupType group) throws InterruptedException{
		while(this.kidsByGroups.get(group).isEmpty()) {
			System.out.println("[ Kindergarden ] There are ne kids in the group, WAIT");
			synchronized(this) {
				this.wait();
			}
		}
		System.out.println("[ Kindergarden ] Here is your group " + group);
		Set<Kid> kidsFromGroup = this.kidsByGroups.get(group);
		return kidsFromGroup;
	}
	
	public boolean isKindergardenFull(){
		if(this.kidsCounter < RANDOM_MAX_KINDERGARDEN_SIZE) {
			return false;
		}
		return true;
	}
	
	public synchronized void takeKid(Kid kid) throws InterruptedException {
		if(kid != null) {
			this.kids.put(kid);
			this.kidsCounter++;
		}
	}
	
	public Kid giveMeNextKid() throws InterruptedException {
		
		Kid kid = this.kids.take();
		return kid;
		
	}
	public synchronized void addKidToGroup(Kid kid) {
		if(kid != null) {
			this.kidsByGroups.get(kid.getGroup()).add(kid);
		}
	}
	public static Kindergarden getInstance() {
		if(Kindergarden.instance == null) {
			Kindergarden.instance = new Kindergarden();
		}
		return Kindergarden.instance;
	}
	
}
