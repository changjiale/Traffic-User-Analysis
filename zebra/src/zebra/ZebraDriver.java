package zebra;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class ZebraDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(zebra.ZebraDriver.class);
		// TODO: specify a mapper
		job.setMapperClass(ZebraMapper.class);
		// TODO: specify a reducer
		job.setReducerClass(ZebraReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(HttpAppHost.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop01:9000/zebra"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/zebra/out"));
		
		if (!job.waitForCompletion(true))
			return;
	}

}
