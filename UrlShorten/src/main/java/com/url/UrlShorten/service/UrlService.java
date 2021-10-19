package com.url.UrlShorten.service;


import com.google.common.hash.Hashing;
import com.url.UrlShorten.model.Dto.GetUrlDto;
import com.url.UrlShorten.model.Payload.UrlRequest;
import com.url.UrlShorten.model.Urls;
import com.url.UrlShorten.repository.UrlRepository;
import javassist.NotFoundException;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Value("${server.url}")
    private String serverUrl;


    @Autowired
    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    public Optional<?> updateUrl(String sUrl, String date) {

        if (urlRepository.existsByShorturl(sUrl)) {
            Urls url = urlRepository.findByShorturl(sUrl);
            url.setExpirydate(date);
            urlRepository.save(url);
            return Optional.ofNullable(true);
        } else {
            return Optional.ofNullable(false);

        }
    }

    public Optional<?> addUrl(UrlRequest urlRequest) {
        if (urlRepository.existsByUrl(urlRequest.getUrl())) {
            return Optional.ofNullable("Url Already Exists");
        }

        String hashedUrl = Hashing.murmur3_32().hashString(urlRequest.getUrl(), StandardCharsets.UTF_8).toString();
        Urls urls = new Urls();
        urls.setShorturl(serverUrl + hashedUrl);
        urls.setHashUrl(hashedUrl);
        if (urlRequest.getUrl().contains("https://")) {
            urls.setUrl(urlRequest.getUrl());
        } else {
            urls.setUrl("https://" + urlRequest.getUrl());
        }
        urls.setExpirydate(urlRequest.getExpiryDate());
        urlRepository.save(urls);
        return Optional.ofNullable("Url added");
    }

    public Optional<?> getUrls() {
        return Optional.ofNullable(urlRepository.findAll());
    }

    public Optional<?> deleteUrl(String sUrl) throws NotFoundException {

        String sUrlWitReq = serverUrl + sUrl;
        if (urlRepository.existsByShorturl(sUrlWitReq)) {
            Urls url = urlRepository.findByShorturl(sUrlWitReq);
            urlRepository.delete(url);
            return Optional.ofNullable("Url Deleted");
        } else {
            return Optional.ofNullable("Url Not Found");
        }

    }

    public Urls findBysUrl(String sUrl) {
        return urlRepository.findByShorturl(sUrl);
    }

}
