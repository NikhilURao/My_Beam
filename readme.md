# INTRODUCTION


Apache Beam is an OS, portable, unified programming model used to build batch and streaming data parallel processing pipelines. Beam supports Java, Python and Go SDKs. Beam pipeline can be executed on multiple compute engines or runners namely Direct Runner, Apache Flink Runner, Apache Nemo Runner, Apache Samza Runner, Apache Spark Runner, Google Cloud Dataflow Runner, Hazelcast Jet Runner and Twister2 Runner.(runner Ref: https://beam.apache.org/documentation/runners/)
![image alt text](https://github.com/NikhilURao/My_Beam/blob/master/img/image_1.png)
![image alt text](https://github.com/NikhilURao/My_Beam/blob/master/img/image_2.png)![image alt text](https://github.com/NikhilURao/My_Beam/blob/master/img/image_3.png)

# BEAM ABSTRACTIONS

1. Pipeline: A Pipeline encapsulates your entire data processing task, from start to finish. This includes reading input data, transforming that data, and writing output data. All Beam driver programs must create a Pipeline. When you create the Pipeline, you must also specify the execution options that tell the Pipeline where and how to run.

2. PCollection: A PCollection represents a distributed data set that your Beam pipeline operates on. The data set can be bounded, meaning it comes from a fixed source like a file, or unbounded, meaning it comes from a continuously updating source via a subscription or other mechanism. Your pipeline typically creates an initial PCollection by reading data from an external data source, but you can also create a PCollection from in-memory data within your driver program. From there, PCollections are the inputs and outputs for each step in your pipeline.

3. PTransform: A PTransform represents a data processing operation, or a step, in your pipeline. Every PTransform takes one or more PCollection objects as input, performs a processing function that you provide on the elements of that PCollection, and produces zero or more output PCollection objects.

4. I/O transforms: Beam comes with a number of "IOs" - library PTransforms that read or write data to various external storage systems.

# Built-in I/O Transforms

Ref: https://beam.apache.org/documentation/io/built-in/

# PCollection Characteristics

1. Each element of a PCollection should be of the same type. 

2. A PCollection is a distributed data set hence the data is stored across multiple nodes. A PCollection is a large, immutable "bag" of elements. There is no upper limit on how many elements a PCollection can contain; any given PCollection might fit in memory on a single machine, or it might represent a very large distributed data set backed by a persistent data store.

3. The element type in a PCollection has a structure that can be examined. Examples are JSON, Protocol Buffer, Avro, and database records. Schemas provide a way to express types as a set of named fields, allowing for more-expressive aggregations.

4. A PCollection is immutable. Once created, you cannot add, remove, or change individual elements. A Beam Transform might process each element of a PCollection and generate new pipeline data (as a new PCollection), but it does not consume or modify the original input collection.

5. Random access of individual elements of a PCollection is not allowed. A Beam transform considers every individual element of a PCollection.

6. A PCollection may be bounded or unbounded depending on the source of data. A bounded PCollection represents bounded, finite or fixed size of data sourcing/reading from a file or a database while an unbounded PCollection represents data set of infinite or unlimited size sourcing/reading from streaming or continuously updating data source such as Pub/Sub or Kafka.

7. The bounded (or unbounded) nature of your PCollection affects how Beam processes your data. A bounded PCollection can be processed using a batch job, which might read the entire data set once, and perform processing in a job of finite length. An unbounded PCollection must be processed using a streaming job that runs continuously, as the entire collection can never be available for processing at any one time.

8. Beam uses windowing to divide a continuously updating unbounded PCollection into logical windows of finite size. These logical windows are determined by some characteristic associated with a data element, such as a timestamp. Aggregation transforms (such as GroupByKey and Combine) work on a per-window basis — as the data set is generated, they process each PCollection as a succession of these finite windows.

9. Each element in a PCollection has an associated intrinsic timestamp. The timestamp for each element is initially assigned by the Source that creates the PCollection. Sources that create an unbounded PCollection often assign each new element a timestamp that corresponds to when the element was read or added.Sources that create a bounded PCollection for a fixed data set also automatically assign timestamps, but the most common behavior is to assign every element the same timestamp (Long.MIN_VALUE).Timestamps are useful for a PCollection that contains elements with an inherent notion of time. If your pipeline is reading a stream of events, like Tweets or other social media messages, each element might use the time the event was posted as the element timestamp.We can manually assign timestamps to the elements of a PCollection if the source doesn’t do it for you. You’ll want to do this if the elements have an inherent timestamp, but the timestamp is somewhere in the structure of the element itself (such as a "time" field in a server log entry). Beam has Transforms that take a PCollection as input and output an identical PCollection with timestamps attached

# Beam Transforms

Transforms are the processing framework in Apache beam. We can apply transforms (user codes, beam core transforms or pre-written composite transforms included in the SDk) on 1 or more PCollection/s and result is stored in 1 or more output PCollections.

Keyword: *apply*

**[**Output PCollection**]** **=** **[**Input PCollection**].**apply**([**Transform**])**

## Core Beam Transforms

Beam provides the following core transforms, each of which represents a different processing paradigm:

1. ParDo

2. GroupByKey

3. CoGroupByKey

4. Combine

5. Flatten

6. Partition

### Pardo

Pardo is a beam processing paradigm that is similar to the map phase of the map/shuffle/reduce algorithm or general mapreduce program.A ParDo transform considers each element in the input PCollection, performs some processing function (your user code) on that element, and emits zero, one, or multiple elements to an output PCollection.

ParDo is useful for a variety of common data processing operations, including:

1. **Filtering a data set.** You can use ParDo to consider each element in a PCollection and either output that element to a new collection or discard it.

2. **Formatting or type-converting each element in a data set.** If your input PCollection contains elements that are of a different type or format than you want, you can use ParDo to perform a conversion on each element and output the result to a new PCollection.

3. **Extracting parts of each element in a data set.** If you have a PCollection of records with multiple fields, for example, you can use a ParDo to parse out just the fields you want to consider into a new PCollection.

4. **Performing computations on each element in a data set.** You can use ParDo to perform simple or complex computations on every element, or certain elements, of a PCollection and output the results as a new PCollection.

