import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;

import java.util.Arrays;
import java.util.List;

public class InMemoryIO {
    public static void main(String[] args) {
        List<String> ls = Arrays.asList("To be, or not to be: that is the question: ",
                "Whether 'tis nobler in the mind to suffer ",
                "The slings and arrows of outrageous fortune, ",
                "Or to take arms against a sea of troubles, ");

        Pipeline pipeline = Pipeline.create();

        PCollection<String> output = pipeline.apply(Create.of(ls)).setCoder(StringUtf8Coder.of());

        output.apply(TextIO.write().to("C:\\Users\\nikhil.rao\\Learning\\My_Beam\\SampleFiles\\outputInMem").
                withSuffix(".txt").withNumShards(1));

        pipeline.run().waitUntilFinish();
    }
}
