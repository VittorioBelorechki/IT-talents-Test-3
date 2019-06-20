import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Kid implements Runnable{

	private static final int KIDS_SINGING_TIME = 2000;
	private String name;
	private GroupType group;
	private int id;
	private static int nextId = 0;
	private int numberOfRisunki = 0;
	private List<Risunka> risunki = new ArrayList<Risunka>();
	
	Kid(String name, GroupType group) throws KidException{
		if(name != null) {
			this.name = name;
		}
		this.setGroup(group);
		this.id = Kid.nextId++;
	}

	public void addRisunka(Risunka r) {
		this.risunki.add(r);
	}
	public void setGroup(GroupType group) throws KidException  {
		
		this.group = group;
		
	}

	public String getName() {
		return name;
	}

	public GroupType getGroup() {
		return group;
	}
	@Override
	public int hashCode() {
		return this.id;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Kid) {
			Kid kid = (Kid)obj;
			return this.id == kid.getId();
		}
		return false;
	}

	public int getId() {
		return id;
	}
	
	public void sing() throws InterruptedException {
		
		System.out.println("[ Kid ]" + this.name + " zapochvam da peq !!! ");
		Thread.sleep(KIDS_SINGING_TIME);
		System.out.println("[ Kid ]" + this.name + " izpqh si !!!");
	}
	
	public void increaseNumberOfRisunki() {
		this.numberOfRisunki++;
	}

	public int getNumberOfRisunki() {
		return numberOfRisunki;
	}
	abstract String getType();
	public List<Risunka> getRisunki(){
		return Collections.unmodifiableList(this.risunki);
	}
}
