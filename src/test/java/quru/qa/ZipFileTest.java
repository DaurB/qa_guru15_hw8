package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileTest {

    ClassLoader cl = ZipFileTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/test.zip"));
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("test.zip"))) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                switch (entry.getName()) {
                    case "test_csv.csv":
                        try (InputStream inputStream = zf.getInputStream(entry);
                             CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                            List<String[]> content = reader.readAll();
                            String[] row = content.get(0);
                            String searchWords = row[0];
                            assertThat(searchWords).isEqualTo("Teacher");
                        }
                        break;
                    case "test_xlsk.xlsx":
                        try (InputStream inputStream = zf.getInputStream(entry)) {
                            XLS xls = new XLS(inputStream);
                            assertThat(
                                    xls.excel.getSheetAt(0)
                                            .getRow(1)
                                            .getCell(3)
                                            .getStringCellValue()
                            ).isEqualTo("KIMEP");
                        }
                        break;
                    case "test_pdf.pdf":
                        try (InputStream inputStream = zf.getInputStream(entry)) {
                            PDF pdf = new PDF(inputStream);
                            assertThat(pdf.text).contains("Lorem ipsum");
                        }
                        break;
                }
            }
        }
    }


}
