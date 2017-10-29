
public class Move {
	public Point oldSpot;
	public Point newSpot;
	
	public Move(Point newOldSpot, Point newNewSpot) {
		oldSpot = newOldSpot;
		newSpot = newNewSpot;
	}
	
	public String toString(){
		return (oldSpot.toString() + " -> " + newSpot.toString());
	}

}
