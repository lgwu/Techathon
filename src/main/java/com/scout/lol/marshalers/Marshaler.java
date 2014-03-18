/**
 * 
 */
package com.scout.lol.marshalers;

import java.io.IOException;

/**
 * @author pingwang@telenav.cn
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * $LastChangedBy$
 */

public interface Marshaler<T> {

    public abstract byte[] marshal(T obj) throws IOException;

    public abstract T unmarshal(byte[] bytes) throws IOException;

}
