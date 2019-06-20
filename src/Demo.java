import java.util.ArrayList;

public class Demo {

	private static final int NUMBER_OF_PARENTS = 30;

	public static void main(String[] args) throws KidException, InterruptedException {
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(int i = 0 ; i < NUMBER_OF_PARENTS; i++) {
			Thread thread = new Thread(new Parent("Random Name"));
			thread.start();
			threads.add(thread);
		}
		for(GroupType group : GroupType.values()) {
			Thread thread = new Thread(new Teacher("Random Name", group));
			thread.start();
			threads.add(thread);
		}
		
		Thread.sleep(20000);
		threads.stream().forEach(thread -> thread.interrupt());
	}
	
}
