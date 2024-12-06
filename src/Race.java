import java.util.List;
import java.util.Map;

public class Race {

    final public Track track;
    final public int raceLength;
    final public Spaceship[] spaceships;
    public int ticks = 0;

    public Spaceship winner = null;

    public Map<Spaceship, Integer> positions = new java.util.HashMap<Spaceship, Integer>();

    public Race(Track track, Spaceship[] spaceships) {
        this.track = track;
        this.spaceships = spaceships;

        raceLength = track.length * track.turns;
    }

    public void start() {
        for (Spaceship spaceship : spaceships) {
            positions.put(spaceship, 0);
        }
        tick();
    }

    public void tick() {
        ticks += 1;
        for (Spaceship spaceship : spaceships) {

            if (spaceship.latency > ticks) continue;

            int speed = spaceship.speed;
            int distance = positions.get(spaceship) + speed;

            positions.put(spaceship, distance);

            if (distance >= raceLength) {
                winner = spaceship;
            }
        }

        // Move the cursor to the beginning of the line
        System.out.print("\r");

        System.out.println(displayRace());
    }

    public String displayPositions() {
        final List<Spaceship> sortedSpaceships = new java.util.ArrayList<Spaceship>();

        for (var entry : positions.entrySet()) {
            sortedSpaceships.add(entry.getKey());
        }

        sortedSpaceships.sort((a, b) -> positions.get(b) - positions.get(a));

        StringBuilder sb = new StringBuilder();
        for (Spaceship spaceship : sortedSpaceships) {
            sb.append(spaceship.name);
            sb.append(" ");
            sb.append(positions.get(spaceship));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String displayRace() {
        StringBuilder sb = new StringBuilder();

        for (Spaceship spaceship : spaceships) {
            final StringBuilder line = new StringBuilder();

            line.append("---------------------------------------------------------------------------------------------------------");
            final int lineLength = line.length();

            final int spaceshipPosPercent = (positions.get(spaceship) * 100) / raceLength;
            final int displayPosPercent = (spaceshipPosPercent * lineLength) / 100;

            if (displayPosPercent >= lineLength) {
                line.setCharAt(lineLength - 1, '>');
            } else if (displayPosPercent < 0) {
                line.setCharAt(0, '>');
            } else {
                line.setCharAt(displayPosPercent, '>');
            }

            line.append(" [");
            line.append(spaceship.name);
            line.append("]");

            sb.append(line);
            sb.append("\n");
        }

        sb.append(displayPositions());

        return sb.toString();
    }
}
