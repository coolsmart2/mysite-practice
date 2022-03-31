package jyw.mysite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface UrlConst {
    List<String> ALL_PATH = new ArrayList<>(
            Arrays.asList(
                    "/",
                    "/back",
                    "/login",
                    "/logout",
                    "/sign-up",
                    "/post/write",
//                    "/post/**/edit",
//                    "/post/**/delete",
//                    "/post/**/comment",
//                    "/post/**/comment/**"
                    "/post/**"
            )
    );

    List<String> REDIRECT_URL = new ArrayList<>(
            Arrays.asList(
                    "/",
                    "/post/write",
                    "/post/*"
            )
    );
}
