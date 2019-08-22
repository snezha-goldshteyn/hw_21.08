package design_patterns;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class DistributionService {
    private Map<Integer, MailGenerator> map = new HashMap<>();

    @SneakyThrows
    public DistributionService() {
        Reflections scanner = new Reflections("design_patterns");
        Set<Class<? extends MailGenerator>> set = scanner.getSubTypesOf(MailGenerator.class);

        map = set.stream().filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
                .filter(aClass -> aClass.isAnnotationPresent(TemplateCode.class))
                .collect(toMap(DistributionService::getCode,
                        DistributionService::getGenerator,
                        DistributionService::resolve));

    }

    private static <U> U resolve(U u, U u1) {
        throw new IllegalStateException(u1 + " already in use");
    }

    @SneakyThrows
    private static int getCode(Class<? extends MailGenerator> aClass) {
        return aClass.getAnnotation(TemplateCode.class).value();
    }

    @SneakyThrows
    private static MailGenerator getGenerator(Class<? extends MailGenerator> aClass) {
        return aClass.getDeclaredConstructor().newInstance();
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