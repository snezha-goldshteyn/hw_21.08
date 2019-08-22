package design_patterns;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DistributionService {
    private Map<Integer, MailGenerator> map = new HashMap<>();

    @SneakyThrows
    public DistributionService() {
        Reflections scanner = new Reflections("design_patterns");
        Set<Class<? extends MailGenerator>> set = scanner.getSubTypesOf(MailGenerator.class);

        set.stream().filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
                .filter(aClass -> aClass.isAnnotationPresent(TemplateCode.class))
                .collect(G)




        for (Class<? extends MailGenerator> aClass : set) {
            if (!Modifier.isAbstract(aClass.getModifiers())) {
                TemplateCode annotation = aClass.getAnnotation(TemplateCode.class);
                int mailCode = annotation.value();
                MailGenerator mailGenerator = aClass.getDeclaredConstructor().newInstance();
                if (map.containsKey(mailCode)) {
                    throw new IllegalStateException(mailCode + " already in use");
                }
                map.put(mailCode, mailGenerator);
            }
        }
    }

    public void sendMail() {
        int mailCode = DBUtils.getMailCode();
        MailGenerator mailGenerator = map.get(mailCode);
        if (mailGenerator == null) {
            throw new UnsupportedOperationException(mailCode + " not supported yet");
        }
        String html = mailGenerator.generateMail();
        send(html);
    }

    private void send(String html) {
        System.out.println("html was sent: " + html);
    }
}