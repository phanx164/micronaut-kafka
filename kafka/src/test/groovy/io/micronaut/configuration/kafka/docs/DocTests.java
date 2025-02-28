package io.micronaut.configuration.kafka.docs;

import io.micronaut.configuration.kafka.docs.quickstart.ProductClient;
import io.micronaut.context.ApplicationContext;
import io.micronaut.core.util.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class DocTests {

    static ApplicationContext applicationContext;
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.4"));

    @BeforeClass
    public static void setup() {
        kafkaContainer.start();
        applicationContext = ApplicationContext.run(
                CollectionUtils.mapOf(
                        "kafka.bootstrap.servers", kafkaContainer.getBootstrapServers()
            )
        );
    }

    @AfterClass
    public static void cleanup() {
        applicationContext.stop();
        kafkaContainer.stop();
    }

    @Test
    public void testSendProduct() {
        // tag::quickstart[]
        ProductClient client = applicationContext.getBean(ProductClient.class);
        client.sendProduct("Nike", "Blue Trainers");
        // end::quickstart[]
    }
}
