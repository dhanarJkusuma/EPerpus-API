package id.veintechnology.apps.library.id.veintechnology.apps.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DbGeneratorMemberIdSequence implements GeneratorMemberIdSequenceService {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DbGeneratorMemberIdSequence(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public String nextIdSequence() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Query q = em.createNativeQuery("SELECT NEXTVAL('service_member_public_id')");
            BigInteger value = (BigInteger) q.getSingleResult();
            return generateFromSequence(value.longValue());
        }
        finally {
            em.close();
        }
    }

    private String generateFromSequence(long sequenceId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        return String.format("U%s0%s", date, String.format("%04d", sequenceId));
    }


}
