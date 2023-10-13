package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm ) {
        //set up PostMapping to the results page which is consistent with the action of the form, using Model to pass
        // the variables back to the view, and @RequestParam to pass the values we get from the form submission into this method.
        ArrayList<Job> jobs; //stores the results from the if/else statements below so we can use them in the view later

        if (searchTerm.equalsIgnoreCase("all") || searchTerm.isEmpty()) { //checks to see if the search bar
            // is submitted with the word all in it regardless of case or if it's empty, if so, it calls the findAll()
            // method from the JobData model, which pulls in all Job values for each category; the data returned is stored in the array
           jobs = JobData.findAll();

        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm); //otherwise, we call this method and pass in
            // the searchType which is the category selected via the radio button and the searchTerm entered in the
            // search bar; That method in the model then matches the searchType and executes the logic within that if
            // statement to search for the searchTerm within that column of data; the data returned is stored in the array
        }
        model.addAttribute("title", "Jobs with " + searchType + ": " + searchTerm); //dynamically
        // adjusts the h1 title and passes it into the view to include the searchType and searchTerm when applicable
        model.addAttribute("columns", columnChoices); //passes the columnChoices into the view
        model.addAttribute("jobs", jobs); //passes the jobs array which contains the resuts of the users search into the view
        return "search";
    }

}

