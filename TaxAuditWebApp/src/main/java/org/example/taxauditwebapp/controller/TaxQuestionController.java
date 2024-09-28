package org.example.taxauditwebapp.controller;

import org.example.taxauditwebapp.service.GPTService;
import org.example.taxauditwebapp.model.TaxQuestion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax")
public class TaxQuestionController {

    private final GPTService gptService;

    public TaxQuestionController(GPTService gptService) {
        this.gptService = gptService;
    }

    @GetMapping("/ask")
    public ResponseEntity<TaxQuestion> askQuestion(@RequestParam(value = "question", required = true) String question) {
        try {
            // Generate a response using the GPT service
            String response = gptService.generateResponse(question);
            TaxQuestion taxQuestion = new TaxQuestion(question, response);

            // Set headers explicitly to ensure correct content type
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Return JSON response with correct headers
            return new ResponseEntity<>(taxQuestion, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            // Return error response in JSON format
            return new ResponseEntity<>(new TaxQuestion(question, "Error: Unable to get a response from GPT."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
