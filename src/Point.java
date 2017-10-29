
public class Point {
	public int x;
	public int y;
	public boolean valid;
	
	public Point(int newX, int newY) {
		x = newX;
		y = newY;
		
		if(x < 0 || x > 7 || y < 0 || y > 7) {
			valid = false;
		}
		else {
			valid = true;
		}
	}
	
	public String toString(){
		return ("(" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
	}
}
