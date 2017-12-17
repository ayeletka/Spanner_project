
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class Graph {
	private ArrayList<Vertex> vertexs_list;
	private ArrayList<Edge> edges_list;
	private int num_of_vertexs;
	private int num_of_edges;
	private Dijkstra dijkstra;
	
	public Graph(ArrayList<Vertex> V,ArrayList<Edge> E) {
		this.num_of_vertexs=V.size();
		this.vertexs_list=V;
		this.num_of_edges=E.size();
		this.edges_list=E;
		this.dijkstra = new Dijkstra(this);

	}
	public Graph(ArrayList<Vertex> V){
		this.num_of_vertexs=V.size();
		this.vertexs_list=V;
		this.num_of_edges=0;
		this.edges_list=new ArrayList<Edge>(V.size()*V.size());
		this.dijkstra = new Dijkstra(this);

	}
	public ArrayList<Vertex> getVertexes(){
		return this.vertexs_list;
	}
	public ArrayList<Edge> getEdges(){
		return this.edges_list;
	}
	public int getNumOfVertex(){
		return vertexs_list.size();
	}
	public int getNumOfEdges(){
		return edges_list.size();
	}
	private void setNumOfEdges(int num){
		this.num_of_edges=num;
	}
	public void addEdge(Edge e){
		if(!this.edges_list.contains(e))
			this.setNumOfEdges(num_of_edges+1);
		edges_list.add(e);
	}

	public ArrayList<Edge> sortEdges(){
		ArrayList<Edge> sorted_edges=new ArrayList<Edge>(edges_list.size());
		ArrayList<Edge> edges_list_copy=(ArrayList<Edge>) edges_list.clone();
		Edge curr=null;
		for(int i=0;i<this.edges_list.size();i++){
			Edge min_edge=null;
			Iterator<Edge> it=edges_list_copy.iterator();
			while(it.hasNext()){
				if(min_edge==null)
					min_edge=it.next();
				else{
					curr=it.next();
					if(curr.getWeight()<min_edge.getWeight())
						min_edge=curr;
				}
			}
			sorted_edges.add(i, min_edge);
			edges_list_copy.remove(min_edge);
		}
		return sorted_edges;
	}
	
	public Dijkstra getDijkstra(){
		return dijkstra;
	}
	
	public void executeDijkstra(Vertex source) {
//		System.out.println("in executeDijkstra: "+ dijkstra.toString());
		dijkstra = new Dijkstra(this);
		dijkstra.execute(source);
	}
	

	public int shortPath(Vertex v1,Vertex v2){
		executeDijkstra(v1);
		LinkedList<Vertex> path = dijkstra.getPath(v2);
		int weigth = 0;
		if (path.size() > 0)
		{
			weigth = dijkstra.getShortestDistance(v2);
		}
		else
		{
			throw new RuntimeException("Should not happen2");
		}
		return weigth;
	}
	
	public Graph getSpannerGraph(double r){
		ArrayList<Edge> sort_edges_list=sortEdges();
//		Graph spanner_graph_in_progress=new Graph(this.vertexs_list, this.edges_list);
		Graph spanner_graph=new Graph(this.vertexs_list);
		for(int i=0;i<num_of_edges;i++){
			Vertex source = sort_edges_list.get(i).getV1() ;
			Vertex Destination = sort_edges_list.get(i).getV2() ;
			int edge_weigth = sort_edges_list.get(i).getWeight();
//			this.executeDijkstra(source);
			spanner_graph.executeDijkstra(source);
			int edge_short_path_weigth=spanner_graph.getDijkstra().getShortestDistance(Destination); 
			if(r*edge_weigth<edge_short_path_weigth)
				spanner_graph.addEdge(sort_edges_list.get(i));
		}
		return spanner_graph;
	}
	
	public int getGrapgWeight(){
		int weight=0;
		for(int i=0; i< edges_list.size(); ++i){
			weight=weight+edges_list.get(i).getWeight();			
		}	
		return weight;
	}
	
	public String toString(){
		String str="The graph has "+ num_of_vertexs+" vertexs and "+ num_of_edges+" num of edges\n";
		str=str + "edges = {";
		Iterator<Edge> itr=(Iterator<Edge>) edges_list.iterator();
		while(itr.hasNext())
		{
			str= str + itr.next().toString()+" , ";
		}
		str=str+"} \n";
		return str;
	}

}