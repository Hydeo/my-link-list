package hello.controllers;

import hello.entity.Link;
import hello.repository.LinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinkController {

    @Autowired
    private LinkRepo repository;

    @RequestMapping("/getlinklist")
    public List<Link> getListLinkByOwner(@RequestParam(value="owner") String owner){
        System.out.println("Controler getlinklist called");
        return repository.findByOwner(owner);
    }
}
