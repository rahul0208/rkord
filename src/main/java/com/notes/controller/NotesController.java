package com.notes.controller;

import com.notes.entity.Notes;
import com.notes.repository.NotesRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Controller
public class NotesController {
    private static Logger log = LoggerFactory.getLogger(NotesController.class);
    private Parser parser;
    private HtmlRenderer renderer;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    SendGrid sendGrid;
    @Autowired
    Email from;

    @PostConstruct
    void initialize(){
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
    }

    @GetMapping("/")
    public ModelAndView load() {
        ModelAndView modelAndView = new ModelAndView("notes");
        List<Notes> notes = notesRepository.findAll().collectList().block();
        modelAndView.addObject("userNotes", notes);
        return modelAndView;
    }

    @PostMapping("/note")
    public String save(@RequestParam String markdownText) {
        Node document = parser.parse(markdownText);
        String html= renderer.render(document);
        Notes note = new Notes();
        note.setMessage(html);
        notesRepository.save(note).block();
        return "redirect:/";
    }

    @PostMapping("/note/{id}/share")
    public String shareNote(@PathVariable String id, @RequestParam String emailAddress) {
        Notes note=notesRepository.findById(id).block();
        String subject = "A Note has been shared";
        Email to = new Email(emailAddress);
        Content content = new Content("text/html",note.getMessage());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info("Mail sending response status : {}, Body :{}, header :{}",response.getStatusCode(), response.getBody(), response.getHeaders());
        } catch (IOException ex) {
             throw new RuntimeException(ex);
        }
        return "redirect:/";
    }
}