 package vector_knn;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import jclient.JClient;
import tool.DataProcessor;
import lsh.LshManager;
import lucene.Index;
import lucene.ReturnValue;

public class Client {
	
	public static boolean debug = true;
	public static int NUM_DIM = 128;
	public static int COMBINE_DIM = 1;
	public static int K = 1;

	public static final int MIN = 0;
	public static final int MAX = 1;
	
	private JClient jclient;
	private Reader reader;
	
	private static int vec_num;
	private static int nodes_num;
	private static int hashtable_num;
	private static int hash_num;
	private static int neighbor_num;
	private static double radius;
	private static String vec_index ;
	private static String vec_scanfile ;
	private static String vec_lshindex ;
	
	Client(String ip) throws Throwable {
		
		jclient = new JClient(ip);
	}
	
//	public void buildIndex(String filename, int num_elements,String index_file) throws Throwable {
//		System.out.println("Building...");
//	
//		jclient.connectAllServers(index_file);
//	
//		// to read binary data
//		reader = new Reader(filename);
//		reader.openReader();
//		
//		//build index for bi-direction search
//		jclient.initAllServers(Index.VECTOR_BUILD, index_file);
//		//set the buffer size
//		jclient.setMaxVecNum(5000);
//		
//		//num_elements indicate the number of the test feature
//		for (int i = 0; i < num_elements; i++) {
//			int value_id[];
//			value_id = reader.getFeature(NUM_DIM);
//			
//			if(debug) {
//				System.out.println(value_id.length);
//				for(int j = 0; j < value_id.length; j++) {
//					System.out.print(value_id[j]+",");
//				}
//				System.out.println();
//			}
//			
//			long values[] = new long[NUM_DIM/COMBINE_DIM];
//			//combine the values
//			for(int j = 0; j < NUM_DIM / COMBINE_DIM; j++){
//				values[j] = value_id[j];
//			}	
//			jclient.addPairs(
//							value_id[NUM_DIM]
//			                , NUM_DIM
//			                , values
//			                , 8
//							, Index.VECTOR_BUILD
//							);
//
//			if(i % 10 == 0)
//				System.out.println(i);
//		}
//		jclient.flush();
//		jclient.closeAllIndexwriters();
////		handler.closeAllIndexwriters();
//		reader.closeReader();
//	
//	}
//	
//	public void testTopKsearch() throws Throwable{
//		
//		COMBINE_DIM = 1;
//		K = 5;
//		//first, connect servers.
//		jclient.connectAllServers(vec_index);
//		
//		//create the QueryConfig for each dimension
//		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("data/query.txt")));
//		String line = "";
//		int qid = 0;
//		long avergetime = 0;
//		while((line = buf.readLine()) != null) {
//			qid++;
//			String values[] = line.split(" ");
//			//maximum number of range and value
//			int dim_range= 128 / COMBINE_DIM;
//			long value_range = 255;
//			SIFTConfig config[] = new SIFTConfig[dim_range];
//			for(int i = 0; i < dim_range; i++) {
//				//initialize a query configuration, set query id
//				config[i] = new SIFTConfig(qid);
//				//set the domain
//				config[i].setDimValueRange(dim_range, value_range);
//				//combine the values and reduce the dimension if necessary
//				int combine_values[] = new int[COMBINE_DIM];
//				for(int j = 0; j < COMBINE_DIM; j++) {
//					combine_values[j] = Integer.valueOf(values[i * COMBINE_DIM + j]);
//				}
//				config[i].num_combination = COMBINE_DIM;
//				//set query 
//				config[i].setQuerylong(i, combine_values);
//				
//				//set bi-direction search range
//				config[i].setRange(10, 10);		
//				//set top K
//				config[i].setK(K);
//			}
//			
//			/*
//			 * this part need more modifications
//			 * **/ 
//			//randomly pick some data and calculate a bound
////			int bound = scan_topK_search();
//			//set bound for searching
////			jclient.setBound((float)bound);
//			
//			//init the servers before searching
//			jclient.initAllServers(Index.VECTOR_SEARCH, vec_index);
//			//searching
//			long starttime, endtime;
//			System.out.println("searching...");
//			starttime = System.currentTimeMillis();
//			long[] index = jclient.answerQuery(config);
//			endtime = System.currentTimeMillis();
//			avergetime += (endtime - starttime);
//			
//			//display the results
//			for(int i = 0; i < index.length; i++) {
//				System.out.println(index[i]);
//			}
//			System.out.println("seraching time: "+(endtime - starttime)+" ms");
//		}
//		System.out.println("average searching time: "+ avergetime/qid+" ms");
//	}
//	
//
//	public void testRangeQuery() throws Throwable {
//		
//		COMBINE_DIM = 1;
//		//first, connect servers.
//		jclient.connectAllServers(vec_index);
//		
//		//create the QueryConfig for each dimension
//		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("data/rangequery.txt")));
//		String line = "";
//		int qid = 0;
//		long avergetime = 0;
//		double theta = 50;
//		double pValue = 2;
//		while((line = buf.readLine()) != null) {
//			qid++;
//
//			String values[] = line.split(" ");
//			line = buf.readLine();
//			String ranges[] = line.split(" ");
//			line = buf.readLine();
//			String weights[] = line.split(" ");
//			
//			//maximum number of range and value
//			int dim_range= 128 / COMBINE_DIM;
//			long value_range = 255;
//			
//			double[] weights_double = new double[NUM_DIM];
//			for(int i = 0; i < NUM_DIM; i++){
//				weights_double[i] = Double.valueOf(weights[i]);
//			}
//			double[][] min_max_ranges = DataProcessor.getMinMaxLSH(theta, 128, 2);
//			double[] min_range = convertToDataSpace(min_max_ranges[MIN], weights_double, pValue);
//			double[] max_range = convertToDataSpace(min_max_ranges[MAX], weights_double, pValue);
//			
//			SIFTConfig config[] = new SIFTConfig[dim_range];
//			for(int i = 0; i < dim_range; i++) {
//				//initialize a query configuration, set query id
//				config[i] = new SIFTConfig(qid);
//				//set the domain
//				config[i].setDimValueRange(dim_range, value_range);
//				//combine the values and reduce the dimension if necessary
//				int combine_values[] = new int[COMBINE_DIM];
//				for(int j = 0; j < COMBINE_DIM; j++) {
//					combine_values[j] = Integer.valueOf(values[i * COMBINE_DIM + j]);
//				}
//				config[i].num_combination = COMBINE_DIM;
//				//set query 
//				config[i].setQuerylong(i, combine_values);
//				
//				//set bi-direction search range
//				//int range = Integer.valueOf(ranges[i]);
//				
//				config[i].setRange(Math.abs((int)Math.floor(min_range[i])), Math.abs((int)Math.ceil(max_range[i])));	
//				config[i].weight = weights_double[i];
//				
//				//set theta value in search
//				config[i].theta = theta;
//			}
//			
//			//init the servers before searching
//			jclient.initAllServers(Index.RANGE_QUERY, vec_index);
//			//searching
//			long starttime, endtime;
//			System.out.println("range querying...");
//			starttime = System.currentTimeMillis();
//			long index[] = jclient.rangeQuery(config);
//			endtime = System.currentTimeMillis();
//			avergetime += (endtime - starttime);
//			
//			//display the results
//			for(int i = 0; i < index.length; i++) {
//				System.out.println(index[i]);
//			}
//			System.out.println("totally found "+index.length+" features");
//			System.out.println("seraching time: "+(endtime - starttime)+" ms");
//		}
//		System.out.println("average searching time: "+ avergetime/qid+" ms");
//	}
//	
//	private double[] convertToDataSpace(double[] rangesInWeightedSpace, double[] weights, double p) {
//		double[] rangesInDataSpace = new double[rangesInWeightedSpace.length];
//		for(int i = 0; i < rangesInWeightedSpace.length; i++) {
//			double u = Math.pow(weights[i], (double)1/p);
//			rangesInDataSpace[i] = rangesInWeightedSpace[i]/u;
//		}
//		return rangesInDataSpace;
//	}
//	
//	int scan_topK_search() throws Throwable {
//		jclient.connectAllServers(vec_scanfile);
//
//		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("data/query.txt")));
//		String line = "";	
//		K = 5;
//		int qid = 0;
//		int distance = -1;
//		long averTime = 0;
//		while((line = buf.readLine()) != null) {
//			qid++;
//			String values[] = line.split(" ");
//			//maximum number of range and value
//			int dim_range= 128 / COMBINE_DIM;
//			long value_range = 255;
//			SIFTConfig config[] = new SIFTConfig[dim_range];
//			for(int i = 0; i < dim_range; i++) {
//				//initialize a query configuration, set query id
//				config[i] = new SIFTConfig(qid);
//				//set the domain
//				config[i].setDimValueRange(dim_range, value_range);
//				//Does not support dimension combination
//				int combine_values[] = new int[1];
//				combine_values[0] = Integer.valueOf(values[i]);
//				//set query
//				config[i].setQuerylong(i, combine_values);	
//				//set top K
//				config[i].setK(K);
//			}
//			long startTimeBeforeInit = System.currentTimeMillis();
//			jclient.initAllServers(Index.VECTOR_SCAN, vec_scanfile);
//			System.out.println("scanning...");
//			long startTime = System.currentTimeMillis();
//			ReturnValue revalue = jclient.scanQuery(config);
//			long endTime = System.currentTimeMillis();
//			averTime += (endTime - startTime);
//			for(int i = 0; i < K; i++) {
//				List<Map.Entry<Long, float[]>>list = revalue.sortedOndis();
//				if(i == 0){
//					distance = Math.round(list.get(0).getValue()[1]);
//				}
//				System.out.println(list.get(i).getKey()+"\t"+list.get(i).getValue()[1]);
//			}
//			System.out.println("init time:\t"+(startTime - startTimeBeforeInit)+" ms");
//			System.out.println("scanning time:\t"+(endTime-startTime)+" ms");
//			System.out.println("Total time (init+scan):\t"+(System.currentTimeMillis() - startTimeBeforeInit)+" ms");
//		}
//		
//		System.out.println("avg time:\t"+averTime/qid);
//		return distance;
//	}
//	
//	public void distributeDatafile(String filename, int num_elements, String scan_file) throws Throwable {
//		System.out.println("Partition...");
//		jclient.connectAllServers(scan_file);
//		reader = new Reader(filename);
//		reader.openReader();
//		
//		jclient.initAllServers(Index.SCAN_BUILD, scan_file);
//		jclient.setMaxVecNum(5000);
//				
//		for (int i = 0; i < num_elements; i++) {
//			int[] value_id = reader.getFeature(NUM_DIM);
//			
//			jclient.addPairs(value_id[NUM_DIM], NUM_DIM, value_id, Index.SCAN_BUILD);
//			
//			if(i%100 == 0)
//				System.out.println(i);
//		}
//		jclient.flush();
//		jclient.closeAllBinwriters();
//		reader.closeReader();
//	}
//	
//	int scan_topK_searchLocally() throws Throwable {
//		
//		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("data/query.txt")));
//		String line = "";
//		Reader reader = new Reader("data/datafile.bin");
//		reader.openReader();
//		
//		int topK = 5;
//		PriorityQueue<Combo> pq = new PriorityQueue<Combo>(topK, new scanComparator());
//		
//		long start = System.currentTimeMillis();
//		int nquery = 0;
//		int distance;
//		System.out.println("scanning...");
//		while((line = buf.readLine()) != null) {
//			nquery++;
//			String values[] = line.split(" ");
//			int[] query = new int[values.length];
//			for(int i = 0 ; i < values.length - 1; i++) {
//				query[i] = Integer.valueOf(values[i]);
//			}
//			int picked = 0, index = 0;
//			Random r = new Random();
//
//			while(picked < vec_num) {
//				int vec[] = reader.getFeature(128);
//				picked++;
//				distance = 0;					
//				for(int j = 0; j < 128; j++){
//					distance += ((query[j] - vec[j]) * (query[j] - vec[j]));
//				}
//				if(pq.size() < topK) 
//					pq.add(new Combo(distance, index));
//				else if(distance < pq.peek().distance) {
//					pq.poll();
//					pq.add(new Combo(distance, index));
//				}
//				index++;
//			}
//		}
//		System.out.println("avg time:\t"+(System.currentTimeMillis() - start));
//		int dis = pq.peek().distance;
//		for(int i = 0; i < topK; i++) {
//			Combo combo = pq.poll();
//			System.out.println(combo.index+"\t"+combo.distance);
//		}
//		return dis;
//	}
	
	public void lshIndex(String dataFile, String index_file) throws Throwable{
		long start = System.currentTimeMillis();
		LshManager lshManager = new LshManager(jclient);
		lshManager.configure(hashtable_num, hash_num, neighbor_num, radius);
//		lshManager.configure(7, 12, 10, 120);
		lshManager.setDataset(dataFile);
		lshManager.setIndexFile(index_file);
		lshManager.lshDistributeIndex();
		long end = System.currentTimeMillis();
		System.out.println("LSH index total time: "+(end-start));
	}
	
	public void lshQuery(String queryFile, String index_file) throws Throwable {
		
		long start = System.currentTimeMillis();
		LshManager lshManager = new LshManager(jclient);
		lshManager.configure(hashtable_num, hash_num, neighbor_num, radius);
//		lshManager.configure(7, 12, 10, 120);
		lshManager.setQuerys(queryFile);
		lshManager.setIndexFile(index_file);
		lshManager.startDistributeLSH();
		long end = System.currentTimeMillis();
		System.out.println("LSH query total time: "+(end-start)+ "ms");
	}
	
		
	public static void main(String args[]) throws Throwable {
		
		String datafile = "data/datafile.bin";
		Client c = new Client("socket://127.0.0.1:8888");
//		Client c = new Client("socket://137.132.145.132:8888");
		vec_num = Parameters.VEC_NUM;
		nodes_num = Parameters.NODES_NUM;
		hashtable_num = Parameters.HASHTABLE_NUM;
		hash_num = Parameters.HASH_NUM;
		neighbor_num = Parameters.NEIGHBOR_NUM;
		radius = Parameters.RADIUS;
		vec_index = "Index_"+nodes_num+"_"+vec_num;
		vec_scanfile = "Scanfile_"+nodes_num+"_"+vec_num+".bin";
		vec_lshindex = "LSHfile_"+nodes_num+"_"+vec_num+"_"+hashtable_num+"_"+hash_num+"_"+neighbor_num+"_"+radius;

		debug = false;
		long start = System.currentTimeMillis();

		c.lshIndex("LSHfile_1_100000.txt", vec_lshindex);
	
		c.lshQuery("data/query_lsh.txt", vec_lshindex);

	}
}



class scanComparator implements Comparator<Combo>{

	@Override
	public int compare(Combo arg0, Combo arg1) {
		// TODO Auto-generated method stub
		return arg1.distance - arg0.distance;
	}
	
}
class Combo {
	
	int distance;
	int index;
	
	Combo(int dis, int index) {
		this.distance = dis;
		this.index = index;
	}
}
