import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
public class Tests {

	static int test_number=1;
	static double stretch_number[]=new double[9];
	static int counter[]=new int[9];
	static double max[]=new double[9];
	static double edgenum[]=new double[9];
	static double edgeavg[]=new double[9];
	static double weight_g[]=new double[9];
	static double weight_sp[]=new double[9];
	
	public static void init(){
		for(int i=0;i<9;i++){
			stretch_number[i]=0;
			counter[i]=0;
			max[i]=0;
			edgenum[i]=0;
			edgeavg[i]=0;
		}
	}
	
	public static void main(String[] args) {
		init();
		Scanner reader_for_graph_size = new Scanner(System.in);  // Reading from System.in
		System.out.println("Please choose the size of the graph(number of vertexs): ");
		int num_of_vertex = reader_for_graph_size.nextInt(); // Scans the next token of the input as an int.
		
		ArrayList<Vertex> vertexs_list=new ArrayList<Vertex>(num_of_vertex);
		int i=0;
		for(i=0;i<num_of_vertex;i++)
			vertexs_list.add(new Vertex(i));
		Scanner test_number_scanner = new Scanner(System.in);  // Reading from System.in

		System.out.println("For test_1 :'finding the avg number of edges in G and in G-spanner', please insert 1.\n"
							+ "For test_2 :'checking the avg and max stretch factor', please insert 2.\n"
							+ "For test_3 :'finding the avg weight in G and in G-spanner', please insert 3");

		int test_number = test_number_scanner.nextInt(); // Scans the next token of the input as an int.
		
		Scanner random_double = new Scanner(System.in);  // Reading from System.in
		System.out.println("Please insert a random number between 0.1 to 0.9 : ");
		double random_double_number = random_double.nextDouble(); // Scans the next token of the input as an int.
		//once finished
		run_spanner(vertexs_list,random_double_number);
		
		switch(test_number){
			case 1:
				/* TEST 1 - find the avg number of edges in G and in G-spanner*/
				 for(int j=2;j<9;j++){
					edgenum[j]/=counter[j];
					edgeavg[j]/=counter[j];
					System.out.println("Avrage Edge number of graph with factor"+j+" is:"+edgenum[j]);
					System.out.println("Avrage Edge number of spanner stretch with factor"+j+" is:"+edgeavg[j]);
				}
				break;
			case 2:
				/* TEST 2 - check the avg and max stretch factor*/
				for(int j=2;j<9;j++){
					stretch_number[j]/=counter[j];
					max[j]/=counter[j];
					System.out.println("Avrage stretch with factor"+j+" is:"+stretch_number[j]);
					System.out.println("Avrage maximal stretch with factor"+j+" is:"+max[j]);
				}
				break;
			case 3:
				/* TEST 3 - find the avg weight in G and in G-spanner*/
				for(int j=2;j<9;j++){
					weight_g[j]/=counter[j];
					weight_sp[j]/=counter[j];
					System.out.println("Avrage Edge number of graph with factor"+j+" is:"+edgenum[j]);
					System.out.println("Avrage Edge number of spanner stretch with factor"+j+" is:"+edgeavg[j]);
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
		Random rnd=new Random();
		for(int i=1;i<=5;++i){
				ArrayList<Edge> E=new ArrayList<Edge>();//(V.size()*V.size());
				for (int k=0; k<V.size(); ++k){
					for(int c=k+1; c<V.size(); ++c){
						if(rnd.nextDouble()<=p)
							E.add(new Edge(V.get(k),V.get(c)));
					}
				}
						
			Graph g=new Graph(V,E);
			for(int j=2;g.getVertexes().size()>Math.pow(2, j);++j){
				double avg=0, maxr=0;
				int count=0;
				Graph g1=g.getSpannerGraph(j);
				System.out.println("j is: "+j);
				System.out.println("g: "+g.toString());
				System.out.println("g1: "+g1.toString());

				for(int c=0; c<g.getVertexes().size(); ++c){
					Vertex source =g.getVertexes().get(c);
					g.executeDijkstra(source);
					g1.executeDijkstra(source);
					int pathg[]= g.getDijkstra().getDistanceArrayFromSource();
					int pathg1[]=g1.getDijkstra().getDistanceArrayFromSource();
					for(int k=0;k<pathg.length;k++){
						if(!(pathg[k]==0||pathg[k]==Integer.MAX_VALUE)){
							double tmp=pathg1[k]/pathg[k];
							avg+=tmp;
							count++;
							if(tmp>maxr)
								maxr=tmp;
							
						}
					}
				}
				//System.out.println(avg);
				avg/=count;
				max[j]+=maxr;
				stretch_number[j]+=avg;
				counter[j]++;
				edgenum[j]+=g.getNumOfEdges();
				edgeavg[j]+=g1.getNumOfEdges();
				weight_g[j]+=g.getGrapgWeight();
				weight_sp[j]+=g1.getGrapgWeight();
				System.out.println("Stretch number:"+j);	

			}
			test_number++;
		}
	}

}
























