import java.util.Random;


public class Edge {
	private Vertex v1;
	private Vertex v2;
	private int weight;
	public static int max_random_weight  =  100;
	
	public Edge(Vertex v1,Vertex v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}
	public Edge(Vertex v1,Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = 10; 
//				new Random().nextInt(max_random_weight) + 1;
	}
	public Vertex getV1(){
		return this.v1;
	}
	public Vertex getV2(){
		return this.v2;
	}
	public int getWeight(){
		return this.weight;
	}
	public boolean equals(Edge e){
		return (this.v1.equals(e.getV1()) && this.v2.equals(e.getV2())) || (this.v2.equals(e.getV1()) && this.v1.equals(e.getV2()));
	}
	public String toString(){
		return "("+v1.toStringID()+","+v2.toStringID()+"): "+this.weight;
	}
	public boolean isVcontainsInThisEdge(Vertex v){
		return (this.v1.equals(v)||this.v2.equals(v));
	}
	
}
