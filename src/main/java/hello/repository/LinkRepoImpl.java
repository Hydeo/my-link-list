package hello.repository;

import com.mongodb.Block;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;

public class LinkRepoImpl implements LinkRepoCustom {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public ArrayList<String> getOwnerList(String owner) {
        Criteria criteria = new Criteria();
        criteria.where("owner").is(owner);
        Query query = new Query();
        query.addCriteria(criteria);
        MongoCollection mc = mongoTemplate.getCollection("link");
        DistinctIterable<String> c = mc.distinct("listName", String.class);
        ArrayList<String> listNames = new ArrayList<>();
        c.forEach((Block<? super String>) listNames::add);
        for (String doc : c) {
            System.out.println(doc);
        }
        return listNames;
    }
}
