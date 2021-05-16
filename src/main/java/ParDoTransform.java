/**
 * Pardo is a beam processing paradigm that is similar to the map phase of the map/shuffle/reduce
 * algorithm or general mapreduce program.
 * A ParDo transform considers each element in the input PCollection,
 * performs some processing function (your user code) on that element, and
 * emits zero, one, or multiple elements to an output PCollection.
 */

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.joda.time.Instant;

import java.util.HashMap;
import java.util.Map;

public class ParDoTransform {

    /**
     * The DoFn to perform on each element in the input PCollection.
     * ComputeWordLen computes the length of the input string and outputs KV pair
      */
    static class ComputerWordLen extends DoFn<String, KV<String, Integer>>{
        @ProcessElement
        public void processWordLen(@Element String word, OutputReceiver<KV<String, Integer>> out){
            out.outputWithTimestamp(KV.of(word, word.length()), new Instant(System.currentTimeMillis()));
        }

    }

    public static void main(String[] args) {

        Pipeline pipeline = Pipeline.create();

        PCollection<String> ipPortInput = pipeline.apply(TextIO.read().
                from("C:\\Users\\nikhil.rao\\Learning\\My_Beam\\SampleFiles\\ipaddress.txt"));

        // Apply a ParDo to the PCollection "words" to compute lengths for each word.
        PCollection<KV<String, Integer>> ipPortOutput = ipPortInput.apply(ParDo.of(new ComputerWordLen()));

        PCollection<String> strwordlen = ipPortOutput.apply(MapElements.into(TypeDescriptors.strings()).
                via((KV<String, Integer> map) -> map.getKey() +" - "+map.getValue() ));

        strwordlen.apply(TextIO.write().to("C:\\Users\\nikhil.rao\\Learning\\My_Beam\\SampleFiles\\ipaddressout")
                .withNumShards(1).withSuffix(".txt"));

        pipeline.run().waitUntilFinish();


    }
}
