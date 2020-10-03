package yuan.gallery.gallery;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import yuan.gallery.gallery.user.ui.auth.AuthInterceptor;
import yuan.gallery.gallery.user.ui.auth.LoginUserResolver;

public abstract class MvcTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    protected AuthInterceptor authInterceptor;

    @MockBean
    protected LoginUserResolver loginUserResolver;

    @BeforeEach
    void setUpEncoding() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    protected ResultActions getAction(String url) throws Exception {
        return mockMvc.perform(get(url));
    }

    protected ResultActions postAction(String url, String inputJson) throws Exception {
        return this.mockMvc
            .perform(post(url)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
