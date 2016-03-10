package ru.piom.notes.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.piom.notes.entities.Account;
import ru.piom.notes.entities.Note;
import ru.piom.notes.repository.AccountRepository;
import ru.piom.notes.repository.NoteRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by Alex on 3/6/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@EnableJpaRepositories(basePackageClasses = NoteRepository.class)
@EntityScan(basePackageClasses = Note.class)
public class NoteRestControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "testUser";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Note> noteList = new ArrayList<>();

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                item -> item instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        noteRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();

        account = accountRepository.save(new Account(userName, "password"));
        noteList.add(noteRepository.save(new Note(account, "Note body by " + userName, "Title one")));
        noteList.add(noteRepository.save(new Note(account, "Note body by " + userName, "Title second")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/notes/")
                .content(json(new Note()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleNote() throws Exception {
        mockMvc.perform(get("/" + userName + "/notes/"
                + noteList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(noteList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.body", is("Note body by testUser")));
    }

    @Test
    public void readNotes() throws Exception {
        mockMvc.perform(get("/" + userName + "/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(noteList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].body", is("Note body by testUser")));
    }

    @Test
    public void createNote() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "REST");
        String tagLocation = this.mockMvc.perform(post("/tags").contentType(contentType).content(
                                json(tag)))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getHeader("Location");
        Note note = new Note(account, "Note", "Title");
        String noteJson = json(note);
        mockMvc.perform(post("/" + userName + "/notes")
                .contentType(contentType)
                .content(noteJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateNote() throws Exception {
        String noteJson = json(new Note(
                account, "New Note", "New title"));
        mockMvc.perform(put("/" + userName + "/notes/" + noteList.get(0).getId())
                .contentType(contentType)
                .content(noteJson))
                .andExpect(status().isNoContent());

    }

    @Test
    public void removeNote() throws Exception {
        mockMvc.perform(delete("/" + userName + "/notes/" + noteList.get(0).getId()))
                .andExpect(status().isNoContent());

    }

    @Test
    public void readHistory() throws Exception {
        mockMvc.perform(get("/" + userName + "/notes/" + noteList.get(0).getId() + "/changes"))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}