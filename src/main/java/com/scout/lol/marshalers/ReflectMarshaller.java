/**
 * 
 */
package com.scout.lol.marshalers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class ReflectMarshaller<T> implements Marshaler<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> cls;

	private transient Schema schema;

	private transient ByteArrayOutputStream out;

	private transient EncoderFactory encoderFactory;

	private transient BinaryEncoder encoder;

	private transient DatumWriter<T> reflectDatumWriter;

	private transient DecoderFactory decoderFactory;

	private transient BinaryDecoder decoder;

	private transient DatumReader<T> datumReader;

	public ReflectMarshaller(Class<T> c) {
		cls = c;
	}

	public void init() {
		schema = ReflectData.get().getSchema(cls);
		out = new ByteArrayOutputStream();
		encoderFactory = EncoderFactory.get();
		encoder = encoderFactory.binaryEncoder(out, null);
		reflectDatumWriter = new ReflectDatumWriter<T>(schema);

		decoderFactory = DecoderFactory.get();
		decoder = DecoderFactory.get().binaryDecoder(new byte[10000], null);
		datumReader = new ReflectDatumReader<T>(schema);
	}

	@Override
	public synchronized byte[] marshal(T obj) throws IOException {

		if (obj == null) {
			return null;
		}
		if (out == null) {
			init();
		}
		out.reset();
		encoder = encoderFactory.binaryEncoder(out, encoder);
		reflectDatumWriter.write(obj, encoder);
		encoder.flush();
		return out.toByteArray();
	}

	@Override
	public synchronized T unmarshal(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		if (decoderFactory == null) {
			init();
		}

		decoder = decoderFactory.binaryDecoder(bytes, decoder);
		T activity = datumReader.read(null, decoder);
		return activity;
	}
	public static void main(String[] args) throws IOException{
//		ReflectMarshaller<CoseLog> coseLogReflectMarshaller = new  ReflectMarshaller<CoseLog>(CoseLog.class);
//		ArrayList<String> logValue = new ArrayList<String>();
//		logValue.add("value1");
//		logValue.add("value2");
//		CoseLog log = new CoseLog("logid",new Date(),logValue);		
//		byte[] bytes = coseLogReflectMarshaller.marshal(log );
//		System.out.println(bytes);
//		System.out.println(coseLogReflectMarshaller.unmarshal(bytes).getLogId());
//		System.out.println(coseLogReflectMarshaller.unmarshal(bytes).getDate());
//		System.out.println(coseLogReflectMarshaller.unmarshal(bytes).getValue());
	}
}
