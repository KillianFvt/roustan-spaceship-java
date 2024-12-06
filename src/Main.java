import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Track track = new Track("Mars", 1000, 14);
        Spaceship[] spaceships = new Spaceship[3];
        spaceships[0] = new Spaceship("Apollo", "White", 100, 35);
        spaceships[1] = new Spaceship("Artemis", "Silver", 90, 20);
        spaceships[2] = new Spaceship("Orion", "Gold", 80, 30);

        Race race = new Race(track, spaceships);

        race.start();

        while (race.winner == null) {
            try {
            race.tick();
            sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.print("The winner is ");
        System.out.print("Spaceship: ");
        System.out.println(race.winner.name);
    }
}