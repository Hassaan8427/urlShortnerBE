package com.url.UrlShorten.controller;


import java.net.URI;

import com.url.UrlShorten.model.Payload.UpdateUrlRequest;
import com.url.UrlShorten.model.Urls;
import com.url.UrlShorten.utility.ResponseWrapper;
import com.url.UrlShorten.model.Payload.UrlRequest;
import com.url.UrlShorten.service.UrlService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/url/")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping(path = "{sUrl}")
    public ResponseEntity<?> goToUrl(@PathVariable String sUrl) throws URISyntaxException, ParseException {

        Urls urls = urlService.findBysUrl(sUrl);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        if (formatter2.format(formatter2.parse(urls.getExpirydate())).compareTo(formatter2.format(new Date())) > 0) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlService.findBysUrl(sUrl).getUrl())).build();
        } else
            return ResponseEntity.ok(new ResponseWrapper(false, "Url Expired", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @PostMapping(path = "add")
    public ResponseEntity<?> addUrl(@RequestBody UrlRequest urlRequest) {
        return ResponseEntity.ok(new ResponseWrapper(true, urlService.addUrl(urlRequest), HttpStatus.OK.value()));
    }

    @GetMapping(path = "get/all")
    public ResponseEntity<?> getUrls() {
        return ResponseEntity.ok(new ResponseWrapper(true, urlService.getUrls(), HttpStatus.OK.value()));

    }

    @PostMapping(path = "update")
    public ResponseEntity<?> updateUrl(@RequestBody UpdateUrlRequest updateUrlRequest) {
        if (Boolean.parseBoolean(urlService.updateUrl(updateUrlRequest.getShortUrl(), updateUrlRequest.getExpiryDate()).get().toString()))
            return ResponseEntity.ok(new ResponseWrapper(true, "Url updated Successfully", HttpStatus.OK.value()));
        else
            return ResponseEntity.ok(new ResponseWrapper(false, "Url Not updated", HttpStatus.INTERNAL_SERVER_ERROR.value()));

    }

    @DeleteMapping(path = "delete/{url}")
    public ResponseEntity<?> deleteUrl(@PathVariable String url) throws NotFoundException {
        return ResponseEntity.ok(new ResponseWrapper(true, urlService.deleteUrl(url), HttpStatus.OK.value()));

    }

}
