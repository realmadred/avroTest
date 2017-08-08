package com.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String args[]) {
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);
        User user2 = new User("Ben", 7, "red");
 
        User user3 = User.newBuilder()
             .setName("Charlie")
             .setFavoriteColor("blue")
             .setFavoriteNumber(null)
             .build();
        File file = new File("users.avro");
        DatumWriter<User> userDatumWriter =
                   new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter =
                   new DataFileWriter<>(userDatumWriter);
        try {
            dataFileWriter.create(user1.getSchema(), new File("users.avro"));
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
            dataFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatumReader<User> userDatumReader =
                             new SpecificDatumReader<>(User.class);
        DataFileReader<User> dataFileReader = null;
        try {
            dataFileReader = new DataFileReader<>(file, userDatumReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = null;
        try {
            while (dataFileReader.hasNext()) {
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}