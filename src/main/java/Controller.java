import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert.*;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Controller {
    private Gui gui;
    private MongoCollection<Document> collection;

    public Controller(Gui gui) {
        this.gui = gui;
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("crud_operations");
            collection = database.getCollection("data");
        }
        catch (Exception e) {
            gui.showAlert(AlertType.ERROR, "Database connection failed");
            System.exit(0);
        }
    }

    public void add(String id, String name, String age, String city) {
        if (name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            gui.showAlert(AlertType.ERROR, "All fields required");
            return;
        }

        if (!age.matches("[0-9]+")) {
            gui.showAlert(AlertType.ERROR, "Please enter number in Age");
            return;
        }

        Document document = new Document()
                .append("id", id)
                .append("name", name)
                .append("age", Integer.parseInt(age))
                .append("city", city);

        try {
            collection.insertOne(document);
            gui.showAlert(AlertType.INFORMATION, "Document added successfully");
        }
        catch (Exception e) {
            gui.showAlert(AlertType.ERROR, "Failed to add document");
        }
    }

    public void read(String id) {
        try {
            Document document = collection.find(new Document("_id", new ObjectId(id))).first();

            if (document != null) {
                gui.showAlert(AlertType.INFORMATION, "Found document: " + document.toJson());
            } else {
                gui.showAlert(AlertType.ERROR, "Document not found");
            }
        }
        catch (Exception e) {
            gui.showAlert(AlertType.ERROR, "Error fetching document");
        }
    }

    public void update(String id, String name, String age, String city) {
        if (id.isEmpty()) {
            gui.showAlert(AlertType.ERROR, "Please enter ID");
            return;
        }
        else if (name.isEmpty() && age.isEmpty() && city.isEmpty()) {
            gui.showAlert(AlertType.ERROR, "Please fill updated information");
            return;
        }
        else if (collection.find(new Document("_id", new ObjectId(id))).first() == null) {
            gui.showAlert(AlertType.ERROR, "Document not found");
            return;
        }

        if (!age.matches("[0-9]+")) {
            gui.showAlert(AlertType.ERROR, "Age must be a number");
            return;
        }

        ObjectId objectId = new ObjectId(id);

        Document updatedDoc = new Document();

        if (!name.isEmpty()) {
            updatedDoc.append("$set", new Document("name", name));
        }

        if (!age.isEmpty()) {
            updatedDoc.append("$set", new Document("age", age));
        }

        if (!city.isEmpty()) {
            updatedDoc.append("$set", new Document("city", city));
        }

        try {
            collection.updateOne(new Document("_id", objectId), updatedDoc);
            gui.showAlert(AlertType.INFORMATION, "Document updated successfully");
        }
        catch (Exception e) {
            gui.showAlert(AlertType.ERROR, "Error updating document");
        }
    }

    public void delete(String id) {
        Document document = collection.find(new Document("_id", new ObjectId(id))).first();

        if (document == null) {
            gui.showAlert(AlertType.ERROR, "Document not found");
            return;
        }

        try {
            collection.deleteOne(document);
            gui.showAlert(AlertType.INFORMATION, "Document deleted successfully");
        }
        catch (Exception e) {
            gui.showAlert(AlertType.ERROR, "Error deleting document");
        }
    }
}
