/**
 * 
 */
package com.scout.lol.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public class SerializableUtils {
	 public static <T extends Serializable> String serialize(T obj, boolean compress) throws IOException{
	        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	        SerializationUtils.serialize(obj, compress?  new GzipCompressorOutputStream(bytes) : bytes);
	        return Base64.encodeBase64String(bytes.toByteArray());

	    }

	    public static <T extends Serializable> String serialize(T obj) throws IOException{
	        return serialize(obj, true);
	    }

	    @SuppressWarnings("unchecked")
        public static <T> T deserialize(String serializedObject, boolean compress, Class<T> cls) throws IOException{
	        if (StringUtils.isEmpty(serializedObject)){
	            return null;
	        }

	        ByteArrayInputStream bytes = new ByteArrayInputStream(Base64.decodeBase64(serializedObject));
	        Object deserializedObject = SerializationUtils.deserialize(compress?  new GzipCompressorInputStream(bytes) : bytes);
	        return (T) deserializedObject;
	    }

	    public static  <T> T deserialize(String  serializedObject, Class<T> cls) throws IOException{
	        return deserialize(serializedObject, true, cls);
	    }


}
