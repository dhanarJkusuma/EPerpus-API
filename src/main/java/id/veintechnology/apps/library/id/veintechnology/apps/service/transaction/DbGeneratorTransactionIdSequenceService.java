package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DbGeneratorTransactionIdSequenceService implements GeneratorTransactionIdSequenceService{

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DbGeneratorTransactionIdSequenceService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public String nextIdSequence() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            Query q = em.createNativeQuery("SELECT NEXTVAL('service_transaction_public_id')");
            BigInteger value = (BigInteger) q.getSingleResult();
            return generateFromSequence(value.longValue());
        }finally {
            em.close();
        }
    }

    private String generateFromSequence(long sequenceId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        return String.format("T%s0%s", date, String.format("%5d", sequenceId));
    }
}
