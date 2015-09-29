import Models.Email;
import Utilities.EmailReceiver;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kasper on 25-09-2015.
 *
 * Class used to performance test application with JMeter
 */
public class javaRequest extends AbstractJavaSamplerClient
{
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext)
    {
        SampleResult result = new SampleResult();
        boolean success = true;
        result.sampleStart();

        String user = "keamailtest1@gmail.com";
        String pass = "mailtester1";
        EmailReceiver receiver = new EmailReceiver();
        ArrayList<Email> list = receiver.downloadEmails(user, pass);

        ArrayList<Email> newList = new ArrayList<>();
        addEmails(newList, 4);

        result.sampleEnd();

        if (list.size() == newList.size())
        {
            result.setSuccessful(success);
        }
        else
        {
            result.setSuccessful(false);
        }

        return result;
    }

    public void addEmails (ArrayList<Email> list, int num)
    {
        for (int i = 0; i < num; i++)
        {
            int id = i;
            String from = "fromString";
            String subject = "testSubject";
            String body = "testBody";
            Date date = new Date();
            boolean newEmail = true;
            Email email = new Email(id, from, subject, body, date,newEmail);
            list.add(email);
        }
    }
}
