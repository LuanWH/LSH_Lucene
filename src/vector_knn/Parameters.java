package vector_knn;

import jclient.JClient;

public class Parameters {
	public static boolean debug = true;
	public static int NUM_DIM = 128;
	public static int COMBINE_DIM = 1;
	public static int K = 1;

	public static final int MIN = 0;
	public static final int MAX = 1;
	
	
	public static String dataFile = "LSHfile_1_100000.txt"; 
	
	private static int vec_num;
	private static int nodes_num;
	private static int hashtable_num;
	private static int hash_num;
	private static int neighbor_num;
	private static double radius;
	private static String vec_index = "Index"+vec_num;
	private static String vec_scanfile = "Scanfile"+vec_num;
	private static String vec_lshindex = "LSHfile"+vec_num;
	
}
