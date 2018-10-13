import com.mongodb.MongoClient
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class MongoStatusChecker extends DefaultTask {
    String host
    int port

    @TaskAction
    void checkStatus() {
        println "Checking status at ${host}:${port}"
        def client = new MongoClient(host, port)
        def names = client.listDatabaseNames();
        for(def name : names) {
            println("Found ${name}")
        }
    }
}