package com.marichely.simpleurl.dao;

import com.marichely.simpleurl.db.public_.tables.records.UrlRecord;
import com.marichely.simpleurl.model.internal.InternalUrl;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.marichely.simpleurl.db.public_.tables.Url.URL;

@Repository
@Transactional
public class UrlRepository extends JooqRepository {

    private DSLContext dslContext;

    public UrlRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void insertNewUrl(InternalUrl url) {
        UrlRecord urlRecord = dslContext.newRecord(URL, url);
        dslContext.executeInsert(urlRecord);
    }

    public InternalUrl getUrlByShortUrl(String url) throws Exception {
        List<InternalUrl> result = dslContext.select()
                                             .from(URL)
                                             .where(URL.SHORT_URL.eq(url))
                                             .fetch()
                                             .into(InternalUrl.class);
        return fetchFromResultList(url, result);
    }

    public InternalUrl getUrlByLongUrl(String url) throws Exception {
        List<InternalUrl> result = dslContext.select()
                                             .from(URL)
                                             .where(URL.URL_.eq(url))
                                             .fetch()
                                             .into(InternalUrl.class);
        return fetchFromResultList(url, result);
    }

    public void updateVisits(long number, String urlID) {
        dslContext.update(URL)
                  .set(URL.VISITS, number)
                  .where(URL.SHORT_URL.eq(urlID))
                  .execute();
    }

    public List<InternalUrl> getUrlsByOwner(Long id) {
        return dslContext.select()
                  .from(URL)
                  .where(URL.ACCOUNT_ID.eq(id))
                  .fetch()
                  .into(InternalUrl.class);
    }


}
