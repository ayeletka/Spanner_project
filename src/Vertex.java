
public class Vertex {
	private int x;
	private int y;
	private int id;


	public Vertex(int x, int y, int id) {
		this.x  =  x;
		this.y  =  y;
		this.id = id;
	}
	public Vertex(int id) {
		this.id = id;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getID(){
		return this.id;
	}
	public boolean equals(Vertex v1){
		return this.id == v1.getID();
	}
	public String toStringID(){
		return "V"+this.id;
	}
	public String toString(){
		return "V"+this.id+": ("+this.x+","+this.y+")";
	}

}
