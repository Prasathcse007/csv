package com.csv.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;
import com.csv.entity.ServiceRelationship;
import com.csv.entity.ServiceRelationshipPK;

public class CSVReader {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<ServiceRelationship> csvToObject(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<CSVRecord> csvRecords = csvParser.getRecords();
      List<ServiceRelationship> ServiceRelationship = csvRecords.stream().map(
    		  c ->  new ServiceRelationship(new ServiceRelationshipPK(c.get("parent"), c.get("child")), new BigDecimal(c.get("impact")), c.get("label")))
    		  .collect(Collectors.toList());

      return ServiceRelationship;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream objectToCSV(List<ServiceRelationship> serviceRelationships) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
    	
    	List<List<String>> data = serviceRelationships.stream().map(
      		  c ->  Arrays.asList(
      	              c.getId().getParent(),
      	              c.getId().getChild(),
      	              String.valueOf(c.getLabel()),
      	              c.getLabel()))
      		  .collect(Collectors.toList());
      csvPrinter.printRecord(data);
      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

}
