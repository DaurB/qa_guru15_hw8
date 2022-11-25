package quru.qa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import quru.qa.model.Student;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonFileTest {

    File json = new File("src/test/resources/Student.json");
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonTestModel() throws Exception {
        Student student = objectMapper.readValue(json, Student.class);
        assertThat(student.name).isEqualTo("Dauren");
        assertThat(student.secondName).isEqualTo("Bibol");
        assertThat(student.age).isEqualTo(24);
        assertThat(student.isGoodStudent).isTrue();
        assertThat(student.lessons.get(1)).isEqualTo("Git");
        assertThat(student.passport.number).isEqualTo(123456);
        assertThat(student.passport.issueDate).isEqualTo("12.12.2022");
    }
}
