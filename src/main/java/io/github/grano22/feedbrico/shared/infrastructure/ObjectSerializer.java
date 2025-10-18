package io.github.grano22.feedbrico.shared.infrastructure;

import java.io.*;

public class ObjectSerializer {
    public static byte[] serializeToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream objectOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectInputStream = new ObjectOutputStream(objectOutputStream)) {
            objectInputStream.writeObject(object);
            return objectOutputStream.toByteArray();
        }
    }

    public static <T> T deserializeFromBytes(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream objectInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectOutputStream = new ObjectInputStream(objectInputStream)) {

            return clazz.cast(objectOutputStream.readObject());
        }
    }
}
