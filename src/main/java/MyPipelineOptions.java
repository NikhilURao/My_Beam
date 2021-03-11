/*
 To add our own options, define an interface with getter and setter methods for each option,
 as in the following example for adding input and output custom options
 */
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public interface MyPipelineOptions extends PipelineOptions {
    @Description("Input for the pipeline") // appears when user types --help in command line
    @Default.String("SampleFiles/sample-input.txt") // default value if not passed from the cl
    String getInput();
    void setInput(String input);

    @Description("Output for the pipeline")
    @Default.String("SampleFiles/sample-output")
    String getOutput();
    void setOutput(String output);

}
