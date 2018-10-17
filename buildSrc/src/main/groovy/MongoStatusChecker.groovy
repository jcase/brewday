import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.ServerAddress
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class MongoStatusChecker extends DefaultTask {
    String host
    int port

    @TaskAction
    void checkStatus() {
        println "Checking status at ${host}:${port}"
        try {
            def client = buildMongoClient(host, port)
            def names = client.listDatabaseNames();
            if(names.size() < 1) {
                println "Mongo not running", System.out.newPrintWriter()
                throw new IllegalStateException("Mongo not running");
            }
        } catch(Exception e) {
            println("could not connect to MongoDB: " + e)
            throw new IllegalStateException("Could not connect to MongoDB", e)
        }
        println "Mongo is running"
    }

    MongoClient buildMongoClient(String host,int port) {
        def builder = new MongoClientOptions.Builder()
        builder.connectTimeout(5_000)
        builder.serverSelectionTimeout(5_000)
        def serverAddress = new ServerAddress(host, port)
        def client = new MongoClient(serverAddress, builder.build())
        return client
    }
}