package design_patterns;

@TemplateCode(1)
public class WelcomeMailGenerator implements MailGenerator {
    @Override
    public String generateMail() {
        //20 lines of code
        return "<HTML> Welcome new client</HTML>";
    }
}
