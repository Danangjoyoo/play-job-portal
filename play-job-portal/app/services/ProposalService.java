package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.inject.Inject;
import models.mongo.Proposal;
import org.bson.Document;
import org.bson.conversions.Bson;
import util.adapter.MongoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProposalService {
    private final MongoAdapter adapter;
    private final MongoCollection<Document> collection;

    @Inject
    public ProposalService(MongoAdapter adapter) {
        this.adapter = adapter;
        this.collection = adapter.getDatabase().getCollection("proposals");
    }

    public void insertOne(Document document) {
        collection.insertOne(document);
    }

    public List<Proposal> getProposalsOfUser(Long userId, Integer page, Integer row) {
        Bson filter = Filters.eq("user_id", userId);

        List<Document> findResult = collection
                .find(filter)
                .skip((page - 1) * row)
                .limit(row)
                .into(new ArrayList<>());

        List<Proposal> result = new ArrayList<>();
        for (Document doc : findResult) result.add(Proposal.load(doc));

        return result;
    }
}
