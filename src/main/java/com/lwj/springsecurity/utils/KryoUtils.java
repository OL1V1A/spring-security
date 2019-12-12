package com.lwj.springsecurity.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class KryoUtils {

    /**
     * Kryo线程不安全，需要ThreadLocal去维护
     * 减少每次实例化Kryo开销同时保证线程安全
     */
    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            kryo.setRegistrationRequired(false);
            return kryo;
        }
    };

    public static byte[] serialize(Object obj){
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output =new Output(byteArrayOutputStream);
        kryo.writeClassAndObject(output,obj);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static  <T> T deserilize(byte[] bytes){
        Kryo kryo = KRYO_THREAD_LOCAL.get();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        input.close();
        return (T) kryo.readClassAndObject(input);
    }


}
