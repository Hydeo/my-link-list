package hello;

import hello.entity.Link;
import hello.repository.LinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


@SpringBootApplication(scanBasePackages={
        "hello.repository", "hello.entity","hello.controllers"})
public class app implements CommandLineRunner{
    @Autowired
    private LinkRepo repository;

    public static void main(String[] args) {
        System.out.println("Bla");
        SpringApplication.run(app.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //repository.deleteAll();

        // save a couple of customers

        //repository.save(new Link("Hideo","https://steamcommunity.com/app/555160","awd","zxc","https://steamcdn-a.akamaihd.net/steam/apps/555160/header.jpg?t=1524122957","dopfgk","awd"));
        //repository.save(new Link("Alice","https://steamcommunity.com/app/555161","gghnghn","zxnhgnghdfgvc","https://steamcdn-a.akamaihd.net/steam/apps/633060/header.jpg?t=1524827859","dcfbvcgnopfgk","asd"));

        // fetch all customers
        System.out.println("Link found with findAll():");
        System.out.println("-------------------------------");
        for (Link link: repository.findAll()) {
            System.out.println(link);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with Title('asd'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByTitle("asd"));

        System.out.println("Customers found with Owner('Hideo'):");
        System.out.println("--------------------------------");
        for (Link link: repository.findByOwner("Hideo")) {
            System.out.println(link);
        }
        System.out.println("App End");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MongoMappingContext context) {

        MappingMongoConverter converter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }
}
