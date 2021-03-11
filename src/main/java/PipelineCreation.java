/*
The Pipeline abstraction encapsulates all the data and steps in your data processing task.
Your Beam driver program typically starts by constructing a Pipeline object, and then using that object as
the basis for creating the pipeline’s data sets as PCollections and its operations as Transforms.
To use Beam, your driver program must first create an instance of the Beam SDK class Pipeline
(typically in the main() function).
When you create your Pipeline, you’ll also need to set some configuration options.
You can set your pipeline’s configuration options programmatically,
but it’s often easier to set the options ahead of time (or read them from the command line)
and pass them to the Pipeline object when you create the object.
 */

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.util.StreamUtils;
import org.apache.beam.sdk.values.PCollection;

public class PipelineCreation {
    public static void main(String[] args) {

        // Setting PipelineOptions programmatically. By default the runner is DirectRunner if no runner is provided.
        PipelineOptions options1 = PipelineOptionsFactory.create();
        // Creating pipeline using options set programmatically
        Pipeline p1 = Pipeline.create(options1);
        System.out.println("setting up pipeline options programmatically");
        p1.run();

        // Setting PipelineOptions from command-line arguments. program argument: --runner=TestFlinkRunner or --runner=DirectRunner or --runner=FlinkRunner
        PipelineOptions options2 = PipelineOptionsFactory.fromArgs(args).withValidation().create();
        // Creating pipeline using options from command line arguments
        Pipeline p2 = Pipeline.create(options2);
        System.out.println("setting up pipeline options from command line arguments");
        p2.run();

        // Creating custom options from interface MyPipelineOptions which extends PipelineOptions add our own custom options in addition to the standard PipelineOptions
        PipelineOptionsFactory.register(MyPipelineOptions.class);
        MyPipelineOptions options3 = PipelineOptionsFactory.fromArgs(args).withValidation().
                as(MyPipelineOptions.class);
        Pipeline p3 = Pipeline.create(options3);

        // Creating a PCollection by reading from custom option Input created in MyPipelineOptions. Input can now be assigned from cli or it takes default value if not provided in cli.
        PCollection<String> PColIp = p3.apply("Read Input",TextIO.read().from(options3.getInput()));

        PColIp.apply(TextIO.write().to(options3.getOutput()).withNumShards(1).withSuffix(".txt"));

        System.out.println("setting up custom pipeline options: ");

        p3.run();

    }
}
