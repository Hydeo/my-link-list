package hello.controllers;

import hello.entity.Link;
import hello.repository.LinkRepo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/{owner}")
public class LinkController {

    @Autowired
    private LinkRepo repository;

    @Value("${graph-api-url}")
    private String graph_api_url;

    @RequestMapping(method = RequestMethod.GET)
    public List<Link> getOwnerList(@PathVariable String owner){
        System.out.println("Controler getlinklist called "+owner);
        return repository.findByOwner(owner);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<Link> getall(@PathVariable String owner){
        return repository.findAll();
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "/lists")
    public List<String> getListOfOwner(@PathVariable String owner){
        return repository.findListOfOwner(owner);
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public Link add(@PathVariable String owner, @RequestBody String json){
        JSONParser parser = new JSONParser();
        try {
            JSONObject parsed_json = (JSONObject) parser.parse(json);
            return repository.save(fetchUrlInfo((String) parsed_json.get("url"),owner));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Link fetchUrlInfo(String url,String owner){

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(graph_api_url)
                .queryParam("url", url);
        RestTemplate restTemplate = new RestTemplate();
        Link new_link= restTemplate.getForObject(builder.toUriString(), Link.class);
        new_link.owner = owner;
        return new_link;
    }
}
