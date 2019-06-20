import java.util.Random;

public class OlderKid extends Kid{
	

	private static final int SECS_TO_MILISECS_COEFF = 1000;
	private static final int MAX_TIME_FOR_RISUVANE = 3;
	private static final int MIN_TIME_FOR_RISUVANE = 1;

	OlderKid(String name, GroupType group) throws KidException {
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
			System.out.println("[ Kid ] zapochvam da risuvam ");
			Risunka risunka = null;
			try {
				risunka = this.paintRisunka();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(risunka != null) {
				System.out.println("[ Kid ] " + this.getName() + " Narisuvah " + risunka.getName());
				try {
					Kindergarden.getInstance().takeUnpaintedRisunka(risunka);
				} catch (InterruptedException e) {
					return;
				}
				this.increaseNumberOfRisunki();
				this.addRisunka(risunka);
			}
		}
		System.out.println(this.getName() + " trugvam si ");
		try {
			Kindergarden.getInstance().trugvamSi(this);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	public Risunka paintRisunka() throws InterruptedException {
		int randomTimeSecs = new Random().nextInt(MAX_TIME_FOR_RISUVANE - MIN_TIME_FOR_RISUVANE + 1) + MIN_TIME_FOR_RISUVANE;
		Thread.sleep(randomTimeSecs * SECS_TO_MILISECS_COEFF);
		Risunka risunka = new Risunka();
		return risunka;
	}
	
	@Override
	public void setGroup(GroupType group) throws KidException {
		if(group.equals( GroupType.JABKI ) || group.equals( GroupType.KALINKI ) ) {
			super.setGroup(group);
		}else {
			throw new KidException("INVALID KID GROUP !!! ");
		}
	}

	@Override
	String getType() {
		return "Older Kid";
	}

}
