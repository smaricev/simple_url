package com.marichely.simpleurl.dao;

import com.marichely.simpleurl.model.internal.InternalUser;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.marichely.simpleurl.db.public_.tables.Account.ACCOUNT;

@Repository
@Transactional
public class AccountRepository  extends JooqRepository{
    private final DSLContext dslContext;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccountRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void insertAccount(String username,String password){
        this.dslContext.insertInto(ACCOUNT).columns(ACCOUNT.USERNAME, ACCOUNT.PASSWORD).values(username,password).execute();
    }

    public String getPasswordByUsername(String username) throws Exception {
        Result<Record1<String>> fetch = dslContext.select(ACCOUNT.PASSWORD).from(ACCOUNT).where(ACCOUNT.USERNAME.eq(username)).fetch();
        return getPasswordFromResultList(fetch,username);
    }

    public InternalUser getUserByUsername(String username) throws Exception {
        List<InternalUser> result = dslContext.select().from(ACCOUNT).where(ACCOUNT.USERNAME.eq(username)).fetch().into(InternalUser.class);
        return fetchFromResultList(username,result);
    }

    private String getPasswordFromResultList(Result<Record1<String>> fetch,String username) throws Exception {
        if(fetch.size()>1){
            throw new Exception("Database returned more than 1 record for username "+username);
        }
        if(fetch.isEmpty()){
            throw new Exception("No records found for username "+ username);
        }
        return fetch.get(0).get(ACCOUNT.PASSWORD);
    }
}
