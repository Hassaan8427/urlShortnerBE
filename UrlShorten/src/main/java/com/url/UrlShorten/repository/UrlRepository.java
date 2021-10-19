package com.url.UrlShorten.repository;

import com.url.UrlShorten.model.Dto.GetUrlDto;
import com.url.UrlShorten.model.Payload.UrlRequest;
import com.url.UrlShorten.model.Urls;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Urls, Long> {

    @Transactional
    @Modifying
    @Query("update Urls u set u.expirydate=:expiry WHERE u.shorturl=:sUrl")
    Integer updateUrl(@Param("expiry") String expiry, @Param("sUrl") String sUrl);

    Urls findByShorturl(String shortUrl);

    boolean existsByUrl(String url);

    boolean existsByShorturl(String shortUrl);

}