package com.lizhengi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo Controller
 *
 * @author 栗筝i
 */
@RestController
@RequestMapping("/api/lizhengi/demo")
public class DemoController {

    @RequestMapping(path = {"/example"}, method = RequestMethod.GET)
    public String listEmployees() {
        return "This is an example of a SpringBoot Api";
    }

}