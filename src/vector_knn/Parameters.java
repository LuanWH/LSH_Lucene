package vector_knn;

import jclient.JClient;

public class Parameters {
	public static boolean debug = true;
	public static int NUM_DIM = 128;
	public static int COMBINE_DIM = 1;
	public static int K = 1;

	public static final int MIN = 0;
	public static final int MAX = 1;
	
	
	public static String DATA_FILE_STRING = "LSHfile_1_100000.txt"; 
	
	public static int VEC_NUM = 100000;
	public static int NODES_NUM = 1;
	public static int HASHTABLE_NUM = 7;
	public static int HASH_NUM = 14 ;
	public static int NEIGHBOR_NUM =10;
	public static double RADIUS = 120;
	public static String VEC_INDEX_STRING = "Index_"+NODES_NUM+"_"+VEC_NUM;
	public static String VEC_SCANFILE_STRING = "Scanfile_"+NODES_NUM+"_"+VEC_NUM+".bin";
	public static String VEC_LSHINDEX_STRING = "LSHfile_"+NODES_NUM+"_"+VEC_NUM+"_"+HASHTABLE_NUM+"_"+HASH_NUM+"_"+NEIGHBOR_NUM+"_"+RADIUS;

	
}
