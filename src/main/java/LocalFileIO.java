import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

public class LocalFileIO {
    public static void main(String[] args) {
        PipelineOptionsFactory.register(MyPipelineOptions.class);
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args)
                .withValidation().as(MyPipelineOptions.class);
        Pipeline pipeline = Pipeline.create(options);
        PCollection<String> Pcol = pipeline.apply(TextIO.read().from("C:\\Users\\nikhil.rao\\Learning\\My_Beam\\SampleFiles\\employee.csv"));
        Pcol.apply(TextIO.write().to("C:\\Users\\nikhil.rao\\Learning\\My_Beam\\SampleFiles\\employeeOut")
                .withSuffix(".csv").withNumShards(1));
        pipeline.run();
    }
}
