package hello.repository;

import hello.entity.Link;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LinkRepo extends MongoRepository<Link,String>{
     Link findByTitle(String title);
     List<Link> findByOwner(String owner);
}
