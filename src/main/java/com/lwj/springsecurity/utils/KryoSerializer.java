package com.lwj.springsecurity.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements RedisSerializer {
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null){
            return new byte[0];
        }
        try {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output =new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output,o);
            output.close();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            throw new SerializationException("Cannot serialize", e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0){
            return null;
        }
        try {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            input.close();
            return kryo.readClassAndObject(input);
        }catch (Exception e){
            throw new SerializationException("Cannot deserialize", e);
        }
    }

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            kryo.setRegistrationRequired(false);
            return kryo;
        }
    };

}
