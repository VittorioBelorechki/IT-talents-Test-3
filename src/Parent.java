import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Parent implements Runnable{

	private static final int SECS_TO_MILISEC_COEFF = 1000;
	private static final int MIN_TIME_TO_GO_TO_KINDERGARDEN = 1;
	private static final int MAX_TIME_TO_GO_TO_KINDERGARDEN = 3;
	private static final int MIN_NUMBER_OF_KIDS = 1;
	private static final int MAX_KIDS_PER_PARENT = 3;
	private static List<String> names = Arrays.asList("Ivan", "Maria", "Petko", "Geprgi", "Alexandra", "Mihaela", "Dimitur", "Sasho", "Trendafil", "Mihail", "Cvetan", "Martin", "Lora");
	private String name;
	private Queue<Kid> kids;
	private int numberOfKids;
//	private Kindergarden kindergarden;
	
	Parent(String name) throws KidException{
		if(name != null) {
			this.name = name;
		}
		this.kids = new ArrayBlockingQueue<Kid>(MAX_KIDS_PER_PARENT);
		this.numberOfKids = new Random().nextInt(MAX_KIDS_PER_PARENT - MIN_NUMBER_OF_KIDS + 1) + MIN_NUMBER_OF_KIDS;
		for(int i = 0; i < numberOfKids; i++) {
			int typeOfKid = new Random().nextInt(2);
			if(typeOfKid == 0) {
				int randomGroup = new Random().nextInt(2);
				GroupType group = null;
				if(randomGroup == 0) {
					group = GroupType.JABKI;
				}else {
					group = GroupType.KALINKI;
				}
				this.kids.offer(new OlderKid(Parent.names.get(new Random().nextInt(Parent.names.size())), group));
			}else {
				int randomGroup = new Random().nextInt(2);
				GroupType group = null;
				if(randomGroup == 0) {
					group = GroupType.PATETA;
				}else {
					group = GroupType.PINGVINCHETA;
				}
				this.kids.offer(new YoungerKid(Parent.names.get(new Random().nextInt(Parent.names.size())), group));
			}
		}
	}

	@Override
	public void run() {
		
		for(int kid = 0; kid < this.numberOfKids; kid++) {
			
			if(Kindergarden.getInstance().isKindergardenFull()) {
				System.out.println("[ Parent ] Emi to gradinata se napulni, qvno zakusnqhme, baba mu shte go gleda ");
				return;
			}
			int randomSec = new Random().nextInt(MAX_TIME_TO_GO_TO_KINDERGARDEN - MIN_TIME_TO_GO_TO_KINDERGARDEN + 1) + MIN_TIME_TO_GO_TO_KINDERGARDEN;
			System.out.println("[ Parent ] Trugvame kum detskata gradina ");
			try {
				Thread.sleep(randomSec * SECS_TO_MILISEC_COEFF);
			} catch (InterruptedException e) {
				return;
			}
			
			Kid k = this.kids.poll();
			if(k != null) {
				try {
					System.out.println("[ Parent ] Stignahme i go ostavqme ");
					Kindergarden.getInstance().takeKid(k);
				} catch (InterruptedException e) {
					return;
				}
			}else {
				return;
			}
		}
		
		
	}
	
	
	
}
