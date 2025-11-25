package itmo.labs.cybersec1.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class SanitizationService {

    public String sanitize(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.none());
    }
}
