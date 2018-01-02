import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
public class Experiments {

	static double stretch_number_avrage[] = new double[9];
	static int counter_for_each_r[] = new int[9];
	static double max[] = new double[9];
	static double normal_graph_num_of_edges[] = new double[9];
	static double spanner_graph_num_of_edges[] = new double[9];
	static double weight_of_normal_graph[] = new double[9];
	static double weight_of_mst_graph[] = new double[9];
	static double weight_of_spanner_graph[] = new double[9];
	
	public static void init(){
		for(int i = 0;i<9;i++){
			stretch_number_avrage[i] = 0;
			counter_for_each_r[i] = 0;
			max[i] = 0;
			normal_graph_num_of_edges[i] = 0;
			spanner_graph_num_of_edges[i] = 0;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("*********** welcome to R-Spanner experiment ***********\n\n");
		init();
		Scanner reader_for_graph_size  =  new Scanner(System.in);  // Reading from System.in
		System.out.println("Please choose the size of the graph(number of vertexs): ");
		int num_of_vertex  =  reader_for_graph_size.nextInt(); // Scans the next token of the input as an int.
		
		ArrayList<Vertex> vertexs_list = new ArrayList<Vertex>(num_of_vertex);
		int i = 0;
		for(i = 0;i<num_of_vertex;i++)
			vertexs_list.add(new Vertex(i));
		Scanner test_number_scanner  =  new Scanner(System.in);  // Reading from System.in

		System.out.println("For test_1 :'finding the avg number of edges in G and in G-spanner', please insert 1.\n"
							+ "For test_2 :'checking the avg and max stretch factor', please insert 2.\n"
							+ "For test_3 :'finding the avg weight in G and in G-spanner', please insert 3");

		int test_number  =  test_number_scanner.nextInt(); // Scans the next token of the input as an int.
		
		Scanner random_double  =  new Scanner(System.in);  // Reading from System.in
		System.out.println("Please insert a random number between 0.1 to 0.9 : ");
		double random_double_number  =  random_double.nextDouble(); // Scans the next token of the input as an int.
		//once finished
		run_spanner(vertexs_list,random_double_number);
		int max_r_num  =  (int) (Math.log10(num_of_vertex)/Math.log10(2));
//		System.out.println("max_r_num: "+ max_r_num);
		switch(test_number){
			case 1:
				/* TEST 1 - find the avg number of edges in G and in G-spanner*/
				 for(int j = 2;j<max_r_num;j++){
					normal_graph_num_of_edges[j] /= counter_for_each_r[j];
					spanner_graph_num_of_edges[j] /= counter_for_each_r[j];
					System.out.println("Avrage Edge number of graph with factor "+j+" is:"+normal_graph_num_of_edges[j]);
					System.out.println("Avrage Edge number of spanner stretch with factor "+j+" is:"+spanner_graph_num_of_edges[j]);
				}
				break;
			case 2:
				/* TEST 2 - check the avg and max stretch factor*/
				for(int j = 2;j<max_r_num;j++){
					stretch_number_avrage[j] /= counter_for_each_r[j];
					max[j] /= counter_for_each_r[j];
					System.out.println("Avrage stretch with factor "+j+" is:"+stretch_number_avrage[j]);
					System.out.println("Avrage maximal stretch with factor "+j+" is:"+max[j]);
				}
				break;
			case 3:
				/* TEST 3 - find the avg weight in G and in G-spanner*/
				for(int j = 2;j<max_r_num;j++){
					weight_of_normal_graph[j] /= counter_for_each_r[j];
					weight_of_spanner_graph[j] /= counter_for_each_r[j];
					weight_of_mst_graph[j] /= counter_for_each_r[j];

					System.out.println("Weight of normal graph with factor "+j+" is:"+weight_of_normal_graph[j]);
					System.out.println("Weight of spanner graph with factor "+j+" is:"+weight_of_spanner_graph[j]);
					System.out.println("Weight of mst graph with factor "+j+" is:"+weight_of_mst_graph[j]);

				}
				break;
			default:
				System.out.println("Didn't run any test");
				break;
		}
		//once finished
		test_number_scanner.close();
		reader_for_graph_size.close(); 
		random_double.close();


		
	}

	public static void run_spanner(ArrayList<Vertex> V,double p){
		Random rnd = new Random();
		for(int i = 1; i <= 100; ++i){
			ArrayList<Edge> E = new ArrayList<Edge>();//(V.size()*V.size());
			for (int k = 0; k<V.size(); ++k){
				for(int c = k+1; c<V.size(); ++c){
					if(rnd.nextDouble() <= p)
						E.add(new Edge(V.get(k),V.get(c)));
				}
			}
						
			Graph g = new Graph(V,E);
			ArrayList<Edge> g_mst_edges = g.prim();
			Graph g_mst = new Graph(V,g_mst_edges);
//			System.out.println("graph: "+ g.toString());
//			System.out.println("mst: "+ g_mst.toString());
			for(int r = 2; g.getVertexes().size()>Math.pow(2, r); ++r){
				double stretch_sum = 0, stretch_num_max = 0;
				int count = 0;
				Graph spanner_graph = g.getSpannerGraph(r);
//				System.out.println("j is: "+r);
//				System.out.println("normal graph: "+g.toString());
//				System.out.println("spanner graph: "+spanner_graph.toString());

				for(int c = 0; c<g.getVertexes().size(); ++c){
					Vertex source  = g.getVertexes().get(c);
					g.executeDijkstra(source);
					spanner_graph.executeDijkstra(source);
					int distance_Vi_from_source[] =  g.getDijkstra().getDistanceArrayFromSource();
					int distance_Vi_from_source_spanner_graph[] = spanner_graph.getDijkstra().getDistanceArrayFromSource();
					for(int k = 0;k<distance_Vi_from_source.length;k++){
						if(!(distance_Vi_from_source[k]  == 0||distance_Vi_from_source[k] == Integer.MAX_VALUE || distance_Vi_from_source_spanner_graph[k]== Integer.MAX_VALUE)){
//							System.out.println("pathg1[k]/pathg[k]: "+distance_Vi_from_source_spanner_graph[k]+ "/"+ distance_Vi_from_source[k]);
							double stretch_num = distance_Vi_from_source_spanner_graph[k]/distance_Vi_from_source[k];
							stretch_sum += stretch_num;
							count++;
							if(stretch_num>stretch_num_max){
								stretch_num_max = stretch_num;
							}
							
						}
					}
				}
				stretch_sum /= count;
				max[r] += stretch_num_max;
				stretch_number_avrage[r] += stretch_sum;
				(counter_for_each_r[r])++;
				normal_graph_num_of_edges[r] += g.getNumOfEdges();
				spanner_graph_num_of_edges[r] += spanner_graph.getNumOfEdges();
				weight_of_normal_graph[r] += g.getGrapgWeight();
				weight_of_mst_graph[r] += g_mst.getGrapgWeight();
				weight_of_spanner_graph[r] += spanner_graph.getGrapgWeight();
			}
		}
	}

}
























