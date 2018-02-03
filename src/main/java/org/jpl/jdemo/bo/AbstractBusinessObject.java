/*
 * Copyright (c) 2018 JP-L, https://www.jp-l.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.jpl.jdemo.bo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Abstract class
 */
public abstract class AbstractBusinessObject implements BusinessObject {
	
	/**
	 * Default constructor.
	 */
	public AbstractBusinessObject() { // NOPMD by limpens on 23-3-08 11:52
		super();
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(org.jpl.patterns.jee.bo.BusinessObject)
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final BusinessObject bo) { // NOPMD by limpens on 23-3-08 11:51
		final CompareToBuilder builder = new CompareToBuilder(); // NOPMD by limpens on 23-3-08 11:52
        final Field[] fields = getClass().getDeclaredFields(); // NOPMD by limpens on 23-3-08 11:52
        try {
            for (int i = 0; i < fields.length; i++) {
                final Field field = fields[i]; // NOPMD by limpens on 23-3-08 11:52
                if (!field.isAccessible()) {
                    field.setAccessible(Boolean.TRUE.booleanValue());
                }
                builder.append(field.get(this), field.get(bo));
            }

        } catch (IllegalAccessException iae) {
        	throw new SecurityException(iae);
        }
        return builder.toComparison();
	}
	
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * {@inheritDoc}
     */
    @Override
	public boolean equals(final Object obj) { // NOPMD by limpens on 23-3-08 10:55
        boolean value = Boolean.FALSE;  // NOPMD by limpens on 29-10-07 12:00
    	if (obj instanceof AbstractBusinessObject) {
    		final AbstractBusinessObject object = (AbstractBusinessObject) obj; // NOPMD by limpens on 29-10-07 12:01
	        final EqualsBuilder builder = new EqualsBuilder(); // NOPMD by limpens on 29-10-07 12:01
	        final Field[] fields = getClass().getDeclaredFields(); // NOPMD by limpens on 23-3-08 10:57
	        try {
	        	for (int i = 0; i < fields.length; i++) {
	        		final Field field = fields[i]; // NOPMD by limpens on 23-3-08 10:57
	                field.setAccessible(Boolean.TRUE);
	                	                
	                final Object objectValue = field.get(this);
	                if (!(objectValue instanceof BusinessObject)) {
	                	builder.append(field.get(this), field.get(object));
	                }
	            }
	
	        } catch (IllegalAccessException e) {
	        	throw new SecurityException(e);
	        }
	        value = builder.isEquals();
    	}
    	return value;
    }
	
    /**
     * @see java.lang.Object#hashCode()
     * {@inheritDoc}
     */
    @Override
    public int hashCode() { // NOPMD by limpens on 23-3-08 10:55
        final HashCodeBuilder builder = new HashCodeBuilder(); // NOPMD by limpens on 23-3-08 10:57
        final Field[] fields = this.getClass().getDeclaredFields(); // NOPMD by limpens on 23-3-08 10:57
        try {
            for (int i = 0; i < fields.length; i++) {
                final Field field = fields[i]; // NOPMD by limpens on 23-3-08 10:57
                if (!field.isAccessible()) {
                    field.setAccessible(Boolean.TRUE);
                }
                
                final Object obj = field.get(this);
                if (!(obj instanceof BusinessObject)) {
                	builder.append(obj);
                }
            }
        } catch (IllegalAccessException e) {
        	throw new SecurityException(e);
        }
        return builder.toHashCode();
    }
    
    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    @Override
	public String toString() { // NOPMD by limpens on 23-3-08 10:55
        final ToStringBuilder builder = new ToStringBuilder(this); // NOPMD by limpens on 23-3-08 10:57
        final Field[] fields = getClass().getDeclaredFields(); // NOPMD by limpens on 23-3-08 10:57
        try {
            builder.appendSuper(super.toString());
            for (int i = 0; i < fields.length; i++) {
                final Field field = fields[i]; // NOPMD by limpens on 23-3-08 10:57
                if (!field.isAccessible()) {
                    field.setAccessible(Boolean.TRUE.booleanValue());
                }

                final Object obj = field.get(this);
                if (!(obj instanceof BusinessObject) && 
                	!("serialVersionUID".equalsIgnoreCase(field.getName()))) {
                	builder.append(field.getName())
                		   .append(":")
                		   .append(obj);
                }
            }

        } catch (IllegalAccessException e) {
        	throw new SecurityException(e);
        }
        return builder.toString();
    }
    
    /**
     * Clone a business object.
     * @param bo the business object to clone
     * @return a clone of the given business object
     */
    public BusinessObject cloneBusinessObject() {
    	try {
    		final BusinessObject clone = (BusinessObject) ClassLoader.getSystemClassLoader().loadClass(this.getClass().getName()).newInstance();
    		BeanUtils.copyProperties(clone, this);
    		return clone;
    	} catch (IllegalAccessException iae) {
    		throw new SecurityException(iae);
    	} catch (InvocationTargetException ite) {
    		throw new SecurityException(ite);
    	} catch (InstantiationException ie) {
    		throw new SecurityException(ie);
    	} catch (ClassNotFoundException cnfe) {
    		throw new SecurityException(cnfe);
    	}
    }
}
