import java.util.Random;

public class YoungerKid extends Kid{
	private static final int MAX_TIME_FOR_RISUVANE = 3;
	private static final int MIN_TIME_FOR_RISUVANE = 1;
	private static final int SECS_TO_MILISECS_COEFF = 1000;

	YoungerKid(String name, GroupType group) throws KidException {
		super(name, group);
	}

	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			try {
				this.sing();
			} catch (InterruptedException e) {
				return;
			}
			System.out.println("[ Younger Kid ]" + this.getName() + " otivam da si vzema risuka za ocvetqvane ");
			Risunka risunka = null;
			try {
				risunka = Kindergarden.getInstance().giveMeRisunka();
			} catch (InterruptedException e) {
				return;
			}
			System.out.println("[ Younger Kid ]" + this.getName() + " vzeh si risunka, zapochvam da ocvetqvam " + risunka.getName());
			try {
				this.paint(risunka);
			} catch (InterruptedException e) {
				return;
			}
			System.out.println("[ Younger Kid ]" + this.getName() + " ocvetih q, slagam q na masata ");
			try {
				Kindergarden.getInstance().takePaintedRisunka(risunka);
			} catch (InterruptedException e) {
				return;
			}
			this.increaseNumberOfRisunki();
			this.addRisunka(risunka);
		}
		System.out.println(this.getName() + " trugvam si ");
		try {
			Kindergarden.getInstance().trugvamSi(this);
		} catch (InterruptedException e) {
			return;
		}
		
	}
	
	public void paint(Risunka r) throws InterruptedException {
		int randomTimeSecs = new Random().nextInt(MAX_TIME_FOR_RISUVANE - MIN_TIME_FOR_RISUVANE + 1) + MIN_TIME_FOR_RISUVANE;
		Thread.sleep(randomTimeSecs * SECS_TO_MILISECS_COEFF);
		r.paint();
	}
	
	@Override
	public void setGroup(GroupType group) throws KidException {
		if(group.equals( GroupType.PATETA ) || group.equals( GroupType.PINGVINCHETA ) ) {
			super.setGroup(group);
		}else {
			throw new KidException("INVALID KID GROUP !!! ");
		}
	}

	@Override
	String getType() {
		return "Younger Kid";
	}
}
