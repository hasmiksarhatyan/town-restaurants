package am.itspace.townrestaurantsweb.utilWeb;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    public static List<Integer> getTotalPages(Page<?> object) {
        int totalPages = object.getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return null;
    }
}